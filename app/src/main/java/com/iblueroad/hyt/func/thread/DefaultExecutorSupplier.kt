package com.iblueroad.hyt.func.thread

import android.os.Process
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * <pre>
 * author : SkyXiao
 * e-mail : 532370487@qq.com
 * time   : 2018/03/01
 * desc   : Singleton class for default executor supplier
 * version: 1.0
 * 参考 https://blog.mindorks.com/threadpoolexecutor-in-android-8e9d22330ee3
</pre> *
 */
class DefaultExecutorSupplier private constructor() {

    /*
    * thread pool executor for background tasks
    */
    private val mForBackgroundTasks: ThreadPoolExecutor
    /*
    * thread pool executor for light weight background tasks
    */
    private val mForLightWeightBackgroundTasks: ThreadPoolExecutor
    /*
    * thread pool executor for main thread tasks
    */
    private val mMainThreadExecutor: Executor

    init {

        // setting the thread factory
        val backgroundPriorityThreadFactory = PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND)

        // setting the thread pool executor for mForBackgroundTasks;
        mForBackgroundTasks = ThreadPoolExecutor(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 60L, TimeUnit.SECONDS, LinkedBlockingQueue(),
                                                 backgroundPriorityThreadFactory)

        // setting the thread pool executor for mForLightWeightBackgroundTasks;
        mForLightWeightBackgroundTasks = ThreadPoolExecutor(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 60L, TimeUnit.SECONDS,
                                                            LinkedBlockingQueue(), backgroundPriorityThreadFactory)

        // setting the thread pool executor for mMainThreadExecutor;
        mMainThreadExecutor = MainThreadExecutor()
    }

    /*
    * returns the thread pool executor for background task
    */
    fun forBackgroundTasks(): ThreadPoolExecutor {
        return mForBackgroundTasks
    }

    /*
    * returns the thread pool executor for light weight background task
    */
    fun forLightWeightBackgroundTasks(): ThreadPoolExecutor {
        return mForLightWeightBackgroundTasks
    }

    /*
    * returns the thread pool executor for main thread task
    */
    fun forMainThreadTasks(): Executor {
        return mMainThreadExecutor
    }

    companion object {
        /*
    * Number of cores to decide the number of threads
    */
        val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
        /*
    * an instance of DefaultExecutorSupplier
    */
        private var sInstance: DefaultExecutorSupplier? = null

        /*
    * returns the instance of DefaultExecutorSupplier
    */
        val instance: DefaultExecutorSupplier
            get() {
                if (sInstance == null) {
                    synchronized(DefaultExecutorSupplier::class.java) {
                        sInstance = DefaultExecutorSupplier()
                    }
                }
                return sInstance!!
            }
    }
}

///*
//* Using it for Background Tasks
//*/
//fun doSomeBackgroundWork() {
//    DefaultExecutorSupplier.getInstance().forBackgroundTasks()
//        .execute(Runnable {
//            // do some background work here.
//        })
//}
//
///*
//* Using it for Light-Weight Background Tasks
//*/
//fun doSomeLightWeightBackgroundWork() {
//    DefaultExecutorSupplier.getInstance().forLightWeightBackgroundTasks()
//        .execute(Runnable {
//            // do some light-weight background work here.
//        })
//}
//
///*
//* Using it for MainThread Tasks
//*/
//fun doSomeMainThreadWork() {
//    DefaultExecutorSupplier.getInstance().forMainThreadTasks()
//        .execute(Runnable {
//            // do some Main Thread work here.
//        })
//}

//取消
//Future future = DefaultExecutorSupplier.getInstance().forBackgroundTasks()
//.submit(new Runnable() {
//    @Override
//    public void run() {
//        // do some background work here.
//    }
//});
//
///*
//* cancelling the task
//*/
//future.cancel(true);


//class DefaultExecutorSupplier private constructor() {
//
//
//    ...如使用优先级，替换如下
//
//    private val mForBackgroundTasks: PriorityThreadPoolExecutor
//
//    init {
//
//        val backgroundPriorityThreadFactory = PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND)
//
//        mForBackgroundTasks = PriorityThreadPoolExecutor(
//            NUMBER_OF_CORES * 2,
//            NUMBER_OF_CORES * 2,
//            60L,
//            TimeUnit.SECONDS,
//            backgroundPriorityThreadFactory
//        )
//
//    }
//  ...
//}

////Use Example
//fun doSomeTaskAtHighPriority() {
//    DefaultExecutorSupplier.getInstance().forBackgroundTasks()
//        .submit(object : PriorityRunnable(PriorityRunnable.HIGH) {
//            override fun run() {
//                // do some background work here at high priority.
//            }
//        })
//}
