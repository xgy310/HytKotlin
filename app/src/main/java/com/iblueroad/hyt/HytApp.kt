package com.iblueroad.hyt

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.iblueroad.hyt.util.AUtils

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : xxx@xx
 *     time   : 2017/12/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HytApp : Application() {

    companion object {
        lateinit var instance: HytApp
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        FlowManager.init(FlowConfig.Builder(this).openDatabasesOnInit(true).build())
        AUtils.init(this)
    }

}