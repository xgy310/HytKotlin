package com.iblueroad.hyt.data.view_model

import android.app.Application
import android.arch.lifecycle.*
import android.text.TextUtils
import com.iblueroad.hyt.data.DataCenter
import com.iblueroad.hyt.data.bmob.model.BMUser
import com.iblueroad.hyt.data.local.dbflow.entity.Girl
import com.iblueroad.hyt.util.AMemory
import com.iblueroad.hyt.util.NetUtils

/**
 * Created by SkyXiao on 2017/9/8.
 */
class UserVM private constructor(application: Application) : AndroidViewModel(application) {
    private val mGirlPageIndex = MutableLiveData<Int>()
    val gilrsLiveData: LiveData<List<Girl>>
    private val mGirlsDataCenter = DataCenter.instance

    init {
        gilrsLiveData = Transformations.switchMap(mGirlPageIndex) { input -> mGirlsDataCenter.getGirlList(input!!) }
    }

    companion object {
        fun hasLogin(): Boolean {
            return !TextUtils.isEmpty(AMemory.get(CUR_USER_OBJID) as CharSequence?)
        }

        val CUR_USER = "cur_user"
        val CUR_USER_MOBILE = "cur_user_mobile"
        val CUR_USER_TOKEN = "cur_user_token"
        val CUR_USER_OBJID = "cur_user_token"

        fun login(user: BMUser) {
            AMemory.save(CUR_USER, user)
            AMemory.save(CUR_USER_TOKEN, user.sessionToken)
            AMemory.save(CUR_USER_MOBILE, user.mobilePhoneNumber)
            AMemory.save(CUR_USER_OBJID, user.objectId)
        }

        fun logout() {
            AMemory.delete(CUR_USER)
            AMemory.delete(CUR_USER_TOKEN)
            AMemory.delete(CUR_USER_MOBILE)
            AMemory.delete(CUR_USER_OBJID)
        }
    }

    fun refreshGrilsData() {
        mGirlPageIndex.value = 1
    }

    fun loadNextPageGirls() {
        if (!NetUtils.isConnected()) return
        mGirlPageIndex.value = if (mGirlPageIndex.value == null) 1 else mGirlPageIndex.value!! + 1
    }

    class Factory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserVM(mApplication) as T
        }
    }
}