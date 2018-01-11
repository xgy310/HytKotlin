package com.iblueroad.hyt.data.local

import android.arch.lifecycle.LiveData
import com.iblueroad.hyt.data.local.dbflow.DBManager
import com.iblueroad.hyt.data.local.dbflow.entity.Girl

/**
 * Created by SkyXiao on 2017/9/7.
 */
class LocalDataSource private constructor() {

    fun getGirlList(index: Int): LiveData<List<Girl>> {
        return DBManager.instance.loadGirlList()
    }

    companion object {
        val instance = LocalDataSource()
    }

}
