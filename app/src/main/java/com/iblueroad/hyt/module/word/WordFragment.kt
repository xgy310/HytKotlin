package com.iblueroad.hyt.module.word

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseFragment
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.frag_picture.*

/**
 * Created by SkyXiao on 2017/9/15.
 */
class WordFragment : BaseFragment() {

    override val layoutResId = R.layout.frag_word

    override fun initView() {
        var isNewType = arguments?.getBoolean(WordFragment.WORD_TYPE_IS_NEW, false) ?: false
        Logger.d("isNewType" + isNewType)
        initRecyclerView()
    }
    private fun initRecyclerView() {

        with(recycler_view) {
            val tLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            layoutManager = tLayoutManager
//            mAdapter = PictureAdapter(mActivity)
//            adapter = mAdapter
//            scheduleLayoutAnimation()
            addOnScrollListener(getOnBottomListener(tLayoutManager))
        }

        with(refresh_layout) {
            isEnableLoadmore = false
            isEnableAutoLoadmore = false
//            setOnLoadmoreListener({ mPicFeedVM?.loadNextPagePics() })

            setOnRefreshListener({
//                mAdapter?.clearPictureList()
//                mPicFeedVM!!.refreshPicData()
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

    private fun getOnBottomListener(layoutManager: StaggeredGridLayoutManager): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {
//                val isBottom = layoutManager.findLastCompletelyVisibleItemPositions(IntArray(2))[1] >= (mAdapter?.itemCount ?: 0) - PRELOAD_SIZE
//                if (!refresh_layout.isRefreshing && isBottom) {
//                    if (!mIsFirstTimeTouchBottom) {
////                        mPicFeedVM?.loadNextPageGirls()
//                        mPicFeedVM?.loadNextPagePics()
//                    } else {
//                        mIsFirstTimeTouchBottom = false
//                    }
//                }
            }
        }
    }
    companion object {
        private val WORD_TYPE_IS_NEW = "word_type_is_new"

        fun newInstance(isNew: Boolean): Fragment {
            val args = Bundle()
            args.putBoolean(WORD_TYPE_IS_NEW, isNew)
            val fragment = WordFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
