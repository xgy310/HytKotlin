package com.iblueroad.hyt.func.thread

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/03/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open class PriorityRunnable(val priority: Int) : Runnable {

    override fun run() {
        // nothing to do here.
    }

    companion object {
        val IMMEDIATE=999
        val HIGH=888
        val MEDIUM=555
        val LOW=111
    }

}