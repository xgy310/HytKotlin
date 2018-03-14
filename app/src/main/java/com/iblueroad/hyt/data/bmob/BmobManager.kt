package com.iblueroad.hyt.data.bmob

import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.iblueroad.hyt.data.bmob.model.BMUser
import com.iblueroad.hyt.data.bmob.model.ImgFeed
import com.iblueroad.hyt.util.EncryptUtils

/**
 * Created by SkyXiao on 2017/7/6.
 */
class BmobManager {
    private val TAG = BmobManager::class.java.simpleName
    private var mListener: IBmobListener<BmobObject>? = null

    /**
     * 用户注册:需要验证码
     */
    fun signUp(phoneNum: String, password: String, code: String, listener: BmobCallBack<BmobObject>) {
        mListener = listener
        val user = BMUser()
        user.mobilePhoneNumber = phoneNum
        user.username = phoneNum
        user.setPassword(EncryptUtils.SHA256ToStr(password))
        user.sex = 0
        user.signOrLogin(code, object : SaveListener<BMUser>() {
            override fun done(user: BMUser, e: BmobException?) {
                if (e == null) {
                    mListener!!.onSuccess(user)
                } else {
                    mListener!!.onFailure(e)
                }
            }
        })
    }

    /**
     * 用户注册
     */
    fun signUp(phoneNum: String, password: String, listener: BmobCallBack<BMUser>) {
        mListener = listener as BmobCallBack<BmobObject>
        val user = BMUser()
        user.mobilePhoneNumber = phoneNum
        user.username = phoneNum
        user.setPassword(EncryptUtils.SHA256ToStr(password))
        user.sex = 0
        user.signUp(object : SaveListener<BMUser>() {
            override fun done(user: BMUser?, e: BmobException?) {
                if (e == null) {
                    mListener!!.onSuccess(user!!)
                } else {
                    mListener!!.onFailure(e)
                }
            }
        })
    }

    /**
     * 使用用户名密码登录
     */
    fun login(username: String, password: String, listener: BmobCallBack<BMUser>) {
        mListener = listener as BmobCallBack<BmobObject>
        //        BMUser user = new BMUser();
        //        user.setUsername(username);
        //        user.setPassword(password);
        //        user.login(new SaveListener<BMUser>() {
        //            @Override
        //            public void done(BMUser user, BmobException e) {
        //                if (e == null) {
        //                    mListener.onSuccess();
        //                } else {
        //                    mListener.onFailure(e);
        //                }
        //            }
        //        });

        BmobUser.loginByAccount(username, EncryptUtils.SHA256ToStr(password), object : LogInListener<BMUser>() {
            override fun done(user: BMUser?, e: BmobException?) {
                if (e == null) {
                    if (user != null) mListener!!.onSuccess(user)
                } else {
                    mListener!!.onFailure(e)
                }
            }
        })
    }

    /**
     * 用户注册
     */
    fun queryImgFeed(page: Int = 0, listener: BmobCallBack<ImgFeed>) {
        mListener = listener as BmobCallBack<BmobObject>

//        val sql = "select * from GameScore order by playScore,signScore desc"
//        val query = BmobQuery<GameScore>()
//        设置sql语句
//        query.setSQL(sql)
        var query = BmobQuery<ImgFeed>()
        query.setLimit(20)
        query.order("-createdAt")//按时间排序，最新发布的在最前面
        query.setSkip(20 * (page - 1))
        query.findObjects(object : FindListener<ImgFeed>() {
            override fun done(list: MutableList<ImgFeed>?, e: BmobException?) {
                if (e == null) {
                    mListener?.onSuccess(list!!)
                } else {
                    mListener!!.onFailure(e)
                }
            }

        })
    }


    companion object {
        var instance = BmobManager()
    }
}
