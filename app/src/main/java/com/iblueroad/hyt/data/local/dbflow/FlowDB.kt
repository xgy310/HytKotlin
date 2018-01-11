package com.iblueroad.hyt.data.local.dbflow

import com.raizlabs.android.dbflow.annotation.Database

/**
 * Created by SkyXiao on 2017/9/13.
 */
@Database(name = FlowDB.NAME, version = FlowDB.VERSION, generatedClassSeparator = "$")
object FlowDB {
    const val NAME: String = "messages" // we will add the .db extension
    const val VERSION: Int = 1//TODO  修改于 1017-12-04
}

