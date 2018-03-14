package com.iblueroad.hyt.module

import android.app.Activity
import android.os.Bundle
import com.iblueroad.hyt.R
import com.iblueroad.hyt.util.extensions.start
import com.iblueroad.hyt.z_other.WeakHandler
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by SkyXiao on 2017/9/15.
 */
class SplashActivity : Activity() {

//    var runnable:Runnable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mHandler = WeakHandler()
        //TODO 后期需要请求广告页面，再替换当前页面显示
        mHandler.postDelayed({ startMain() }, 2000L)

//        runnable= Runnable { startMain() }
//        btn_skip.postDelayed(runnable,2000L)

        btn_skip.setOnClickListener {
            mHandler.removeCallbacksAndMessages(null)
//            btn_skip.removeCallbacks(runnable)
            startMain()
        }

    }

    private fun startMain() {
        start<MainActivity>()
        finish()
        //        //TODO 判断是否是第一次启动，或者更新启动
        //        if (AppUtils.isNewVersion()) GuideActivity.start(this);
        //        else MainActivity.start(this);
        //        finish();
    }
}
