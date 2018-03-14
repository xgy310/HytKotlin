package com.iblueroad.hyt.module.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.bumptech.glide.Glide
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseFragment
import com.iblueroad.hyt.data.view_model.UserVM
import com.iblueroad.hyt.module.auth.LoginActivity
import com.iblueroad.hyt.util.extensions.onClick
import com.iblueroad.hyt.util.extensions.start
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
        iv_user_face.onClick {
            if (UserVM.hasLogin()) {
                activity?.start<EditUserInfoActivity>()
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val curUser = UserVM.getCurUser()
        if (null != curUser) {
            val tFaceUrl = curUser.faceUrl
            if (null != tFaceUrl && !tFaceUrl.isEmpty()) {
                Glide.with(this).load(curUser.faceUrl).into(iv_user_face)
            }
            tv_username.text = curUser.username
        }
    }
}
