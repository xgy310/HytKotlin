package com.iblueroad.hyt.data.view_model

import android.app.Application
import android.arch.lifecycle.*
import com.iblueroad.hyt.data.DataCenter
import com.iblueroad.hyt.data.bmob.model.ImgFeed
import com.iblueroad.hyt.util.NetUtils

/**
 * Created by SkyXiao on 2017/9/8.
 */
class PicFeedVM private constructor(application: Application) : AndroidViewModel(application) {
    private val mPageIndex = MutableLiveData<Int>()
    val picsLiveData: LiveData<List<ImgFeed>>
    private val mCurDataCenter = DataCenter.instance

    init {
        picsLiveData = Transformations.switchMap(mPageIndex) { input -> mCurDataCenter.getPicList(input!!) }
    }

    fun refreshPicData() {
        mPageIndex.value = 1
    }

    fun loadNextPagePics() {
        if (!NetUtils.isConnected()) return
        mPageIndex.value = if (mPageIndex.value == null) 1 else mPageIndex.value!! + 1
    }

    class Factory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PicFeedVM(mApplication) as T
        }
    }
}