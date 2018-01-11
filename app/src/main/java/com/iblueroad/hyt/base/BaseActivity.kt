package com.iblueroad.hyt.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : xxx@xx
 *     time   : 2017/12/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseActivity : AppCompatActivity(), LifecycleOwner {
    private var mBackPressedListener: BackPressedListener? = null
    private val mRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return mRegistry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//    setContentView(getLayoutId())
        setContentView(layoutResId)
        initView()
    }


    abstract fun initView()


    protected abstract val layoutResId: Int

    override fun onBackPressed() {
        if (null != mBackPressedListener)
            mBackPressedListener!!.onBackPress()
        else
            super.onBackPressed()
    }

//  @LayoutRes
//  abstract fun getLayoutId(): Int

    fun setBackPressedListener(backPressedListener: BackPressedListener) {
        this.mBackPressedListener = backPressedListener
    }

    interface BackPressedListener {
        fun onBackPress(): Boolean
    }
}
