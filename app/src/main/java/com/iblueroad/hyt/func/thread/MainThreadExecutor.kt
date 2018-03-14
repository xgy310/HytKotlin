package com.iblueroad.hyt.func.thread

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor



/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/03/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MainThreadExecutor:Executor {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(runner: Runnable?) {
        handler.post(runner)
    }
}