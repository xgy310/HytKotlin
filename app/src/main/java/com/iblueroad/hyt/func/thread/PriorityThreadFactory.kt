package com.iblueroad.hyt.func.thread

import java.util.concurrent.ThreadFactory


/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/03/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PriorityThreadFactory (threadPriority: Int) : ThreadFactory {

    private var mThreadPriority: Int = threadPriority


    override fun newThread(runnable: Runnable?): Thread {
        val wrapperRunnable = Runnable {
            try {
                android.os.Process.setThreadPriority(mThreadPriority)
            } catch (t: Throwable) {

            }

            runnable?.run()
        }
        return Thread(wrapperRunnable)
    }
}