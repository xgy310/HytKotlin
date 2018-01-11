package com.iblueroad.hyt.module.picture

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseFragment
import com.iblueroad.hyt.data.view_model.GirlsViewModel
import com.iblueroad.hyt.module.auth.LoginActivity
import com.iblueroad.hyt.util.AUtils
import com.iblueroad.hyt.util.extensions.onClick
import com.orhanobut.logger.Logger
import io.armcha.ribble.presentation.adapter.PictureAdapter
import kotlinx.android.synthetic.main.frag_picture.*

/**
 * Created by SkyXiao on 2017/9/15.
 */
class PictureFragment : BaseFragment() {
    private var isNewType: Boolean = false

    private var mAdapter: PictureAdapter? = null
    private var mGirlsViewModel: GirlsViewModel? = null

    override val layoutResId = R.layout.frag_picture

    companion object {
        private val PICTURE_TYPE_IS_NEW = "picture_type_is_new"

        fun newInstance(isNew: Boolean): Fragment {
            val args = Bundle()
            args.putBoolean(PICTURE_TYPE_IS_NEW, isNew)
            val fragment = com.iblueroad.hyt.module.picture.PictureFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        isNewType = arguments?.getBoolean(PICTURE_TYPE_IS_NEW, false) ?: false
        Logger.d("isNewType" + isNewType)

//
        with(recycler_view) {
            val tLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            layoutManager = tLayoutManager
            mAdapter = PictureAdapter(mActivity)
            adapter = mAdapter
//            scheduleLayoutAnimation()
            addOnScrollListener(getOnBottomListener(tLayoutManager))
        }

        refresh_layout.apply {
            isEnableLoadmore = false
            isEnableAutoLoadmore = false
            setOnLoadmoreListener({ mGirlsViewModel?.loadNextPageGirls() })

            setOnRefreshListener({
                mAdapter?.clearPictureList()
                mGirlsViewModel!!.refreshGrilsData()
            })
        }

//        with(refresh_layout) {
//            isEnableLoadmore = false
//            isEnableAutoLoadmore = false
//            setOnLoadmoreListener({ mGirlsViewModel?.loadNextPageGirls() })
//
//            setOnRefreshListener({
//                mAdapter?.clearPictureList()
//                mGirlsViewModel!!.refreshGrilsData()
//            })
//        }
        refresh_layout.autoRefresh()
        var isLogin = true
        fbtn_upload_pic.onClick {
            if (isLogin) {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        subscribeUI()
    }

    private val PRELOAD_SIZE = 6
    private var mIsFirstTimeTouchBottom = true

    private fun getOnBottomListener(layoutManager: StaggeredGridLayoutManager): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {
                val isBottom = layoutManager.findLastCompletelyVisibleItemPositions(IntArray(2))[1] >= (mAdapter?.itemCount ?: 0) - PRELOAD_SIZE
                if (!refresh_layout.isRefreshing && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        mGirlsViewModel?.loadNextPageGirls()
                    } else {
                        mIsFirstTimeTouchBottom = false
                    }
                }
            }
        }
    }

    private fun subscribeUI() {

        val factory = GirlsViewModel.Factory(AUtils.appContext!!)
        mGirlsViewModel = ViewModelProviders.of(mActivity, factory).get(GirlsViewModel::class.java)

        mGirlsViewModel!!.gilrsLiveData.observe(this, Observer { girls ->
            if (null == girls) return@Observer
//            girls.forEach { mAdapter?.add(it) }
            mAdapter?.addList(girls)
            if (refresh_layout.isRefreshing) refresh_layout.finishRefresh()
            if (refresh_layout.isLoading) refresh_layout.finishLoadmore()
        })

    }
}
