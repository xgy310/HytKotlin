package com.iblueroad.hyt.data.bmob.model

import cn.bmob.v3.BmobUser

/**
 * Created by SkyXiao on 2017/5/26.
 */

data class BMUser(
    var uid: Long = 0L,
    var sex: Int = 0,//0:男 1：女
    var labels: List<String>? = null,
    var carrer: String? = null,
    var province: String? = null,
    var city: String? = null,
    var faceUrl: String? = null,
    var signature: String? = null,
    var wechatId: String? = null,
    var birthday: Long = 0L,
    var career: String? = null,
    var hometown: String? = null,
    var longitude: Double = 0.toDouble(),
    var latitude: Double = 0.toDouble()
) : BmobUser()
