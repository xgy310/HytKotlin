package com.iblueroad.hyt.module.comm

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/02/11
 *     desc   : 简单的WebView使用，优化内存溢出
 *     version: 1.0
 * </pre>
 */


class WebActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_web

    private var bundle: Bundle? = null
    private var webView: WebView? = null

    private fun setTransParent() {
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.systemUiVisibility = option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        //getWindow().setNavigationBarColor(Color.TRANSPARENT);
        supportActionBar!!.hide()
    }

    override fun initView() {
        bundle = intent.extras
        webView = WebView(this)
        val settings = webView!!.settings
        settings.domStorageEnabled = true
        //解决图片加载问题
        settings.javaScriptEnabled = true
        settings.blockNetworkImage = false

        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.d("webview", "url: " + url)
                view.loadUrl(url)
                return true
            }
        }
        web_frame!!.addView(webView)
        val url = bundle!!.getString("URL")
        Log.d("web", "url: " + url!!)
        webView!!.loadUrl(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView!!.canGoBack()) {
                webView!!.goBack()
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        if (webView != null) {
            webView!!.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView!!.tag = null
            webView!!.clearHistory()

            (webView!!.parent as ViewGroup).removeView(webView)
            webView!!.destroy()
            webView = null
        }
        super.onDestroy()
    }
}
