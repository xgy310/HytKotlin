package com.iblueroad.hyt.module.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseFragment
import com.iblueroad.hyt.data.view_model.UserVM
import com.iblueroad.hyt.util.extensions.onClick
import kotlinx.android.synthetic.main.frag_profile.*

/**
 * Created by SkyXiao on 2017/9/15.
 */
class ProfileFragment : BaseFragment() {

    override val layoutResId = R.layout.frag_profile

    companion object {
        fun newInstance(): Fragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        btn_logout.onClick { UserVM.logout() }
    }
}
