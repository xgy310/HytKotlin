package com.iblueroad.hyt.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.iblueroad.hyt.R
import kotlinx.android.synthetic.main.activity_top_bar.*

/**
 * Created by SkyXiao on 2017/9/7.
 */
abstract class BaseBackActivity : AppCompatActivity(), LifecycleOwner {

    private var mBackPressedListener: BackPressedListener? = null
    private val mRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return mRegistry
    }

    /**
     * 当前 Activity 渲染的视图 View
     */
    protected lateinit var mContentView: View

    protected abstract val layoutResId: Int

    protected lateinit var mActivity: BaseBackActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        setBaseView(layoutResId)
        initView()
    }

    abstract fun initView()

    protected fun setBaseView(@LayoutRes layoutId: Int) {
//    Slidr.attach(this)
        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_top_bar, null)
        setContentView(mContentView)
        fl_container.addView(LayoutInflater.from(this).inflate(layoutId, fl_container, false))
        setSupportActionBar(tool_bar)
        getToolBar()?.setDisplayHomeAsUpEnabled(true)

//        BarUtils.setStatusBarColor(this, ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary), 0)
//        BarUtils.addMarginTopEqualStatusBarHeight(root_layout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun getToolBar(): ActionBar? {
        return supportActionBar
    }


    override fun onBackPressed() {
        if (null != mBackPressedListener)
            mBackPressedListener!!.onBackPress()
        else
            super.onBackPressed()
    }

    fun setBackPressedListener(backPressedListener: BackPressedListener?) {
        this.mBackPressedListener = backPressedListener
    }

    interface BackPressedListener {
        fun onBackPress(): Boolean
    }
}
