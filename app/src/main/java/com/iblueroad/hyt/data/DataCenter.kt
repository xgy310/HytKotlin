package com.iblueroad.hyt.data

import android.arch.lifecycle.LiveData
import com.iblueroad.hyt.data.local.LocalDataSource
import com.iblueroad.hyt.data.local.dbflow.entity.Girl
import com.iblueroad.hyt.data.remote.RemoteDataSource
import com.iblueroad.hyt.util.NetUtils

/**
 * Created by SkyXiao on 2017/9/8.
 */
class DataCenter private constructor() {
    private val mRemoteDataSource = RemoteDataSource.instance
    private val mLocalDataSource = LocalDataSource.instance

    fun getGirlList(index: Int): LiveData<List<Girl>> {
        return if (NetUtils.isConnected()) {
            mRemoteDataSource.getGirlList(index)
        } else {
            mLocalDataSource.getGirlList(index)
        }
    }

    companion object {
        val instance = DataCenter()
    }
}
