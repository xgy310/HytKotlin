package com.iblueroad.hyt.data.bmob.model

import cn.bmob.v3.BmobObject

/**
 * Created by SkyXiao on 2017/6/16.
 */

data class ImgFeed(var imgId: String? = null, var picUrl: String? = null, var state: Int = 0, var own: BMUser?=null) : BmobObject()
