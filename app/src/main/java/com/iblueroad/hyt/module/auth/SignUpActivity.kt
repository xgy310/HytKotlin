package com.iblueroad.hyt.module.auth

import android.app.Activity
import android.util.Patterns
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseActivity
import com.iblueroad.hyt.data.bmob.BmobCallBack
import com.iblueroad.hyt.data.bmob.BmobManager
import com.iblueroad.hyt.data.bmob.model.BMUser
import com.iblueroad.hyt.util.extensions.onClick
import kotlinx.android.synthetic.main.activity_signup.*

/**
 * Created by SkyXiao on 2017/7/6.
 */
class SignUpActivity : BaseActivity() {
    override val layoutResId: Int = R.layout.activity_signup

    override fun initView() {
        btn_login_sign_up.setText(R.string.sign_up)
        tv_sing_up_login.setText(R.string.has_account_2_login)

        btn_login_sign_up.onClick { singUp() }
        tv_sing_up_login.onClick { finish() }
    }


    private fun singUp() {
        if (!validate()) return

        val phone = ed_phone.text.toString()
        val password = ed_pwd.text.toString()

        //        EventBus.getDefault().post(new ShowLoadingEvent());
        BmobManager.instance.signUp(phone, password, object : BmobCallBack<BMUser>() {
            override fun onSuccess(obj: BMUser) {
                //                EventBus.getDefault().post(new HideLoadingEvent());
                //todo save user
                setResult(Activity.RESULT_OK)
                finish()
            }
        })
    }

    fun validate(): Boolean {
        var valid = true

        val phone = ed_phone.text.toString()
        val password = ed_pwd.text.toString()

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            ed_phone.error = "请输入有效的手机号码"
            valid = false
        } else {
            ed_phone.error = null
        }

        if (password.isEmpty() || password.length < 6 || password.length > 12) {
            ed_pwd.error = "密码长度在6-12位之间"
            valid = false
        } else {
            ed_pwd.error = null
        }

        return valid
    }
}
