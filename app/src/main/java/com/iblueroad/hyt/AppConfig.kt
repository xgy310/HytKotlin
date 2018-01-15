package com.iblueroad.hyt

import android.os.Environment
import com.iblueroad.hyt.util.AUtils

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/01/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object AppConfig {

    val FILE_SEP = System.getProperty("file.separator")
    val LINE_SEP = System.getProperty("line.separator")
    val PKG = "com.blankj.androidutilcode"
    val TEST_PKG = "com.blankj.testinstall"
    val GITHUB = "https://github.com/Blankj/AndroidUtilCode"
    val BLOG = "http://www.jianshu.com/u/46702d5c6978"
    val CACHE_PATH: String
    val TEST_APK_PATH: String

    init {
        val cacheDir = AUtils.appContext?.externalCacheDir
        CACHE_PATH = if (cacheDir != null) {
            cacheDir.absolutePath
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        TEST_APK_PATH = CACHE_PATH + FILE_SEP + "test_install.apk"

    }
}