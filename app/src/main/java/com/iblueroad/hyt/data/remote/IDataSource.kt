package com.iblueroad.hyt.data.remote

import android.arch.lifecycle.LiveData
import com.iblueroad.hyt.data.local.dbflow.entity.Girl

/**
 * Created by SkyXiao on 2017/9/7.
 */
interface IDataSource {

    val isLoadingGirls: LiveData<Boolean>
    fun getGirlList(index: Int): LiveData<List<Girl>>


}
