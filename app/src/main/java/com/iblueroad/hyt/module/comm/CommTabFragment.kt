package com.iblueroad.hyt.module.comm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseFragment
import com.iblueroad.hyt.module.picture.PictureFragment
import com.iblueroad.hyt.module.trip.TripFragment
import com.iblueroad.hyt.module.word.WordFragment
import kotlinx.android.synthetic.main.frag_tab.*
import kotlinx.android.synthetic.main.include_tab_layout.*

/**
 * Created by SkyXiao on 2017/9/18.
 */
class CommTabFragment : BaseFragment() {
    lateinit var mFragmentNew: Fragment
    lateinit var mFragmentHot: Fragment

    override val layoutResId = R.layout.frag_tab

    companion object {

        val FRAG_TYPE_PIC = 0
        val FRAG_TYPE_WORD = 1
        val FRAG_TYPE_TRIP = 2

        private val FRAG_TYPE = "frag_type"

        fun newInstance(fragType: Int): Fragment {
            val args = Bundle()
            args.putInt(FRAG_TYPE, fragType)
            val fragment = CommTabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        when (arguments?.getInt(FRAG_TYPE, 0)) {
            1 -> {
                mFragmentNew = WordFragment.newInstance(true)
                mFragmentHot = WordFragment.newInstance(false)
            }
            2 -> {
                mFragmentNew = TripFragment.newInstance(true)
                mFragmentHot = TripFragment.newInstance(false)
            }
            else -> {
                mFragmentNew = PictureFragment.newInstance(true)
                mFragmentHot = PictureFragment.newInstance(false)
            }
        }

        view_pager!!.adapter = FragmentAdapter()
        tab_layout!!.setupWithViewPager(view_pager)
    }

    private inner class FragmentAdapter : FragmentPagerAdapter(childFragmentManager) {
        private val PAGE_SIZE = 2

        override fun getItem(position: Int): Fragment {
            return if (1 == position) mFragmentHot else mFragmentNew
        }

        override fun getCount(): Int {
            return PAGE_SIZE
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (0 == position) "最新" else "最热"
        }
    }
}
