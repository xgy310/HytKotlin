package com.iblueroad.hyt.base

import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.iblueroad.hyt.R
import kotlinx.android.synthetic.main.include_tool_bar.*

/**
 * Created by SkyXiao on 2017/9/7.
 */
abstract class BaseTBActivity : BaseActivity() {


    /**
     * 当前 Activity 渲染的视图 View
     */
    protected lateinit var mContentView: View

    override fun setRootView(@LayoutRes layoutResId: Int) {
//    Slidr.attach(this)
        mContentView = LayoutInflater.from(this).inflate(R.layout.include_tool_bar, null)
        setContentView(mContentView)
        fl_container.addView(LayoutInflater.from(this).inflate(layoutResId, fl_container, false))

        initToolbar(R.string.app_name)
//        setSupportActionBar(tool_bar)
//        getToolBar()?.setDisplayHomeAsUpEnabled(isShowBackBar)

//        BarUtils.setStatusBarColor(this, ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary), 0)
//        BarUtils.addMarginTopEqualStatusBarHeight(root_layout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private var isShowBackBar = true

    /**
     * 初始化 Toolbar
     *
     * @param resId 标题 Title 的 resId
     */
    protected fun initToolbar(resId: Int): Toolbar? {
        initToolbar(getString(resId))
        return tool_bar
    }

    /**
     * 初始化 Toolbar
     *
     * @param title 标题 Title
     */
    protected fun initToolbar(title: String): Toolbar? {
        if (null != tool_bar) {
            setSupportActionBar(tool_bar)
            val actionBar = supportActionBar
            tool_bar!!.title = title
            actionBar?.setDisplayHomeAsUpEnabled(isShowBackBar)
        }
        return tool_bar
    }

    /**
     * @param isShow 是否显示返回键
     */
    fun setBackBar(isShow: Boolean) {
        isShowBackBar = isShow
    }


    protected fun getToolBar(): ActionBar? {
        return supportActionBar
    }


}
