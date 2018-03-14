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
import com.iblueroad.hyt.data.view_model.PicFeedVM
import com.iblueroad.hyt.data.view_model.UserVM
import com.iblueroad.hyt.module.auth.LoginActivity
import com.iblueroad.hyt.util.AUtils
import com.iblueroad.hyt.util.extensions.onClick
import com.iblueroad.hyt.util.extensions.start
import com.orhanobut.logger.Logger
import io.armcha.ribble.presentation.adapter.PictureAdapter
import kotlinx.android.synthetic.main.frag_picture.*

/**
 * Created by SkyXiao on 2017/9/15.
 */
class PictureFragment : BaseFragment() {
    private var isNewType: Boolean = false

    private var mAdapter: PictureAdapter? = null
    //    private var mGirlsViewModel: GirlsViewModel? = null
    private var mPicFeedVM: PicFeedVM? = null

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

        initRecyclerView()

        refresh_layout.autoRefresh()
        fbtn_upload_pic.onClick {
            if (UserVM.hasLogin()) {
                activity?.start<PostPicActivity>()

            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        subscribeUI()
    }

    private fun initRecyclerView() {

        with(recycler_view) {
            val tLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            layoutManager = tLayoutManager
            mAdapter = PictureAdapter(mActivity)
            adapter = mAdapter
//            scheduleLayoutAnimation()
            addOnScrollListener(getOnBottomListener(tLayoutManager))
        }

        with(refresh_layout) {
            isEnableLoadmore = false
            isEnableAutoLoadmore = false
            setOnLoadmoreListener({ mPicFeedVM?.loadNextPagePics() })

            setOnRefreshListener({
                                     mAdapter?.clearPictureList()
                                     mPicFeedVM!!.refreshPicData()
                                 })
        }

//        refresh_layout.apply {
//            isEnableLoadmore = false
//            isEnableAutoLoadmore = false
////            setOnLoadmoreListener({ mPicFeedVM?.loadNextPageGirls() })
//            setOnLoadmoreListener({ mPicFeedVM?.loadNextPagePics() })
//
//            setOnRefreshListener({
//                mAdapter?.clearPictureList()
////                mPicFeedVM!!.refreshGrilsData()
//                mPicFeedVM!!.refreshPicData()
//            })
//        }


    }

    private val PRELOAD_SIZE = 6
    private var mIsFirstTimeTouchBottom = true

    private fun getOnBottomListener(layoutManager: StaggeredGridLayoutManager): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {
                val isBottom = layoutManager.findLastCompletelyVisibleItemPositions(IntArray(2))[1] >= (mAdapter?.itemCount ?: 0) - PRELOAD_SIZE
                if (!refresh_layout.isRefreshing && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
//                        mPicFeedVM?.loadNextPageGirls()
                        mPicFeedVM?.loadNextPagePics()
                    } else {
                        mIsFirstTimeTouchBottom = false
                    }
                }
            }
        }
    }

    private fun subscribeUI() {

        val factory = PicFeedVM.Factory(AUtils.appContext!!)
        mPicFeedVM = ViewModelProviders.of(mActivity, factory).get(PicFeedVM::class.java)

//        mPicFeedVM!!.gilrsLiveData.observe(this, Observer { girls ->
        mPicFeedVM!!.picsLiveData.observe(this, Observer { pics ->
            if (null == pics) return@Observer
//            girls.forEach { mAdapter?.add(it) }
            pics.forEach {
                if (it.state == 1) mAdapter?.add(it)
            }

//            mAdapter?.addList(pics)
            if (refresh_layout.isRefreshing) refresh_layout.finishRefresh()
            if (refresh_layout.isLoading) refresh_layout.finishLoadmore()
        })

    }
}
