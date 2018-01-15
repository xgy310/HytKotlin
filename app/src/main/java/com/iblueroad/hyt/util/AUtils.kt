package com.iblueroad.hyt.util

import cn.bmob.v3.Bmob
import com.iblueroad.hyt.AppConstants
import com.iblueroad.hyt.BuildConfig
import com.iblueroad.hyt.HytApp
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.CsvFormatStrategy
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.raizlabs.android.dbflow.config.FlowManager
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : xxx@xx
 *     time   : 2017/12/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object AUtils {
    lateinit var sApp: WeakReference<HytApp>

    fun init(context: HytApp) {
        sApp = WeakReference(context)

        //初始化数据库DBFlow
        FlowManager.init(context)
//        FlowManager.init(FlowConfig.Builder(context).openDatabasesOnInit(true).build())
        Logger.addLogAdapter(AndroidLogAdapter())
        val formatStrategy = CsvFormatStrategy.newBuilder().tag("hyt").build()
        Logger.addLogAdapter(DiskLogAdapter(formatStrategy))

        //第一：默认初始化
        Bmob.initialize(context, AppConstants.APP_ID_BMOB)
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //        BmobConfig config = new BmobConfig.Builder(context)
        //                ////设置appkey
        //                .setApplicationId(Constant.APP_ID_BMOB)
        //                ////请求超时时间（单位为秒）：默认15s
        //                //.setConnectTimeout(15)
        //                ////文件分片上传时每片的大小（单位字节），默认512*1024
        //                .setUploadBlockSize(512 * 1024)
        //                ////文件的过期时间(单位为秒)：默认1800s
        //                .setFileExpiration(2500).build();
        //        Bmob.initialize(config);
        CrashReport.initCrashReport(context, AppConstants.APP_ID_BUGLY, BuildConfig.DEBUG)

        if (!LeakCanary.isInAnalyzerProcess(context)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            LeakCanary.install(context)
        }

        AMemory.init()
    }

    val appContext: HytApp? get() = sApp.get()

}