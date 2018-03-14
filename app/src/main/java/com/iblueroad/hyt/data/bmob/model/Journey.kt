package com.iblueroad.hyt.data.bmob.model

import cn.bmob.v3.BmobObject

/**
 * Created by SkyXiao on 2017/6/16.
 */

data class Journey(var jId: String? = null,
                   var title: String? = null,
                   var coverUrl: String? = null,
                   var state: Int = 0,
                   var own: BMUser? = null,
                   var date: Long = 9L,
                   var days: Int = 1,
                   var loc: String? = null,
                   var content: String? = null,
                   var userFaceUrl: String? = null,
                   var userName: String? = null) : BmobObject()
