package com.iblueroad.hyt.data.view_model

import android.app.Application
import android.arch.lifecycle.*
import com.iblueroad.hyt.data.DataCenter
import com.iblueroad.hyt.data.local.dbflow.entity.Girl
import com.iblueroad.hyt.util.NetUtils

/**
 * Created by SkyXiao on 2017/9/8.
 */
class GirlsViewModel private constructor(application: Application) : AndroidViewModel(application) {
    private val mGirlPageIndex = MutableLiveData<Int>()
    val gilrsLiveData: LiveData<List<Girl>>
    private val mGirlsDataCenter = DataCenter.instance

    init {
        gilrsLiveData = Transformations.switchMap(mGirlPageIndex) { input -> mGirlsDataCenter.getGirlList(input!!) }
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
            return GirlsViewModel(mApplication) as T
        }
    }
}