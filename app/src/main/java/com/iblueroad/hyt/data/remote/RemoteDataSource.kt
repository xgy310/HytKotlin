package com.iblueroad.hyt.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import cn.bmob.v3.BmobObject
import cn.bmob.v3.exception.BmobException
import com.iblueroad.hyt.data.bmob.BmobCallBack
import com.iblueroad.hyt.data.bmob.BmobManager
import com.iblueroad.hyt.data.bmob.model.ImgFeed
import com.iblueroad.hyt.data.local.dbflow.DBManager
import com.iblueroad.hyt.data.local.dbflow.entity.Girl
import com.iblueroad.hyt.data.remote.model.GirlResp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by SkyXiao on 2017/9/7.
 */
class RemoteDataSource private constructor() : IDataSource {
    private val mApiStore: ApiStore = ApiManager.instance.getApiStore()
    private val mGirlList = MutableLiveData<List<Girl>>()
    private val mPicList = MutableLiveData<List<ImgFeed>>()
    override val isLoadingGirls = MutableLiveData<Boolean>()

    override fun getGirlList(index: Int): LiveData<List<Girl>> {
        isLoadingGirls.value = true
        mApiStore.getGirlsData(index).enqueue(object : Callback<GirlResp> {
            override fun onResponse(call: Call<GirlResp>, response: Response<GirlResp>) {
                isLoadingGirls.value = false
                val girlResp = response.body()
                if (response.isSuccessful || !response.body()!!.error) {
                    val newGirls = girlResp!!.results
                    if (newGirls != null) {
                        if (newGirls.isNotEmpty()) {
//                            if (1 == index) refreshLocalGirlList(newGirls)
                            mGirlList.value = newGirls
                        }
                    }
                }
            }

            override fun onFailure(call: Call<GirlResp>, t: Throwable) {
                isLoadingGirls.value = false
            }
        })
        return mGirlList
    }

    private fun refreshLocalGirlList(results: List<Girl>) {
        DBManager.instance.insertGirlList(results)
    }

    private fun refreshLocalPicList(results: List<Girl>) {
//        DBManager.instance.insertGirlList(results)
    }

    companion object {
        val instance = RemoteDataSource()
    }

    fun getPicList(index: Int): LiveData<List<ImgFeed>>? {
        isLoadingGirls.value = true
        BmobManager.instance.queryImgFeed(index, object : BmobCallBack<ImgFeed>() {

            override fun onSuccess(dataList: List<BmobObject>) {
                isLoadingGirls.value = false
                if (dataList.isNotEmpty()) {
                    mPicList.value = dataList as List<ImgFeed>
                }
            }

            override fun onFailure(e: BmobException) {
                isLoadingGirls.value = false
            }

        })
        return mPicList
    }
}
