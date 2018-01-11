package com.iblueroad.hyt.module.auth

import android.Manifest
import android.content.Intent
import android.util.Patterns
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseActivity
import com.iblueroad.hyt.data.bmob.BmobCallBack
import com.iblueroad.hyt.data.bmob.BmobManager
import com.iblueroad.hyt.data.bmob.model.BMUser
import com.iblueroad.hyt.util.PermissionsUtils
import com.iblueroad.hyt.util.extensions.onClick
import kotlinx.android.synthetic.main.activity_signup.*

/**
 * Created by SkyXiao on 2017/7/6.
 */
class LoginActivity : BaseActivity() {
    override val layoutResId: Int = R.layout.activity_signup

    override fun initView() {
        tv_sing_up_login!!.setText(R.string.no_account_2_sing_up)
        PermissionsUtils.with(this).addPermission(Manifest.permission.ACCESS_FINE_LOCATION).addPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION).addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).addPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE).addPermission(Manifest.permission.CAMERA).addPermission(
            Manifest.permission.READ_PHONE_STATE).initPermission()
        btn_login_sign_up.onClick { login() }
        tv_sing_up_login.onClick { startActivityForResult(Intent(this, SignUpActivity::class.java), 0) }
    }


    private fun login() {
        if (!validate()) return

        val phone = ed_phone!!.text.toString()
        val password = ed_pwd!!.text.toString()

        //        EventBus.getDefault().post(new ShowLoadingEvent());
        BmobManager.instance.login(phone, password, object : BmobCallBack<BMUser>() {
            override fun onSuccess(user: BMUser) {
                //                EventBus.getDefault).post(new HideLoadingEvent());
//                start<MainActivity>()
                //TODO  save user
                finish()
                //                gotoActivity(MainActivity.class, true);
                //                UserUtils.login(user);
            }
        })
    }

    private fun validate(): Boolean {
        var valid = true

        val phone = ed_phone!!.text.toString()
        val password = ed_pwd!!.text.toString()

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            ed_phone!!.error = "请输入有效的手机号码"
            valid = false
        } else {
            ed_phone!!.error = null
        }

        if (password.isEmpty() || password.length < 6 || password.length > 12) {
            ed_pwd!!.error = "密码长度在6-12位之间"
            valid = false
        } else {
            ed_pwd!!.error = null
        }

        return valid
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            finish()
        }
    }
}




