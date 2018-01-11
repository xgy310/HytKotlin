package com.iblueroad.hyt.data.bmob

import cn.bmob.v3.BmobObject
import cn.bmob.v3.exception.BmobException

/**
 * Created by SkyXiao on 2017/7/6.
 */
interface IBmobListener<T> {
    fun onSuccess()

    fun onSuccess(obj: T)

    fun onSuccess(dataList: List<BmobObject>)

    fun onFailure(e: BmobException)
}
