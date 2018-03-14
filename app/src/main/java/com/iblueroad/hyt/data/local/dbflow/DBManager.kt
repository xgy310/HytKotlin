package com.iblueroad.hyt.data.local.dbflow

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.iblueroad.hyt.HytApp
import com.iblueroad.hyt.data.bmob.model.ImgFeed
import com.iblueroad.hyt.data.local.dbflow.entity.Girl
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.SQLite

/**
 * Created by SkyXiao on 2017/9/7.
 */
class DBManager private constructor() {

    private val mGirlList = MutableLiveData<List<Girl>>()
    private val mPicList = MutableLiveData<List<ImgFeed>>()
    val isLoadGirls = MutableLiveData<Boolean>()

    fun insertGirlList(girls: List<Girl>) {
        FlowManager.init(FlowConfig.Builder(HytApp.instance).openDatabasesOnInit(true).build())
        val database = FlowManager.getDatabase(Girl::class.java)
        val transaction = database.beginTransactionAsync {
            girls.forEach { it.save() }
        }.build()
        transaction.execute()

        //         FlowQueryList<Girl> mChatRecords;
        //        //实时保存，马上保存
        //        SQLite.insert(Girl.class).executeInsert(new DatabaseWrapper() {})
        //        new SaveModelTransaction<>(ProcessModelInfo.withModels(girls)).onExecute();
        //异步保存，使用异步，如果立刻查询可能无法查到结果
        //TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(peoples)));
    }

    fun loadGirlList(): LiveData<List<Girl>> {
        mGirlList.value = SQLite.select().from(Girl::class.java).queryList();
        return mGirlList
    }

    fun loadPicList(): LiveData<List<ImgFeed>> {
        mPicList.value = SQLite.select().from(ImgFeed::class.java).queryList();
        return mPicList
    }

    companion object {
        val instance = DBManager()
    }
}
