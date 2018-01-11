package com.iblueroad.hyt.module.trip

import android.os.Bundle
import android.support.v4.app.Fragment
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseFragment
import com.orhanobut.logger.Logger

/**
 * Created by SkyXiao on 2017/9/15.
 */
class TripFragment : BaseFragment() {

    override val layoutResId = R.layout.frag_trip

    override fun initView() {
        var isNewType = arguments?.getBoolean(TripFragment.TRIP_TYPE_IS_NEW, false) ?: false
        Logger.d("isNewType" + isNewType)
    }

    companion object {
        private val TRIP_TYPE_IS_NEW = "trip_type_is_new"

        fun newInstance(isNew: Boolean): Fragment {
            val args = Bundle()
            args.putBoolean(TRIP_TYPE_IS_NEW, isNew)
            val fragment = TripFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
