package com.iblueroad.hyt.base

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iblueroad.hyt.util.extensions.emptyString

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : xxx@xx
 *     time   : 2017/12/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseFragment : Fragment(), LifecycleOwner {
    protected lateinit var mRootView: View

    protected lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(layoutResId, container, false)
        return mRootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
        initView()
    }

    abstract fun initView()

    fun getRootView(): View = mRootView

    protected abstract val layoutResId: Int

    open fun getTitle(): String = emptyString

}