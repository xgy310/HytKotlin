package com.iblueroad.hyt.data.bmob

import cn.bmob.v3.BmobObject
import cn.bmob.v3.exception.BmobException
import com.iblueroad.hyt.util.ToastUtils

/**
 * Created by SkyXiao on 2017/7/6.
 */
abstract class BmobCallBack<T> : IBmobListener<T> {
    override fun onSuccess() {}

    override fun onSuccess(obj: T) {}

    override fun onSuccess(dataList: List<BmobObject>) {}

    override fun onFailure(e: BmobException) {
        ToastUtils.show(e.message)
        //        EventBus.getDefault().post(new HideLoadingEvent());
    }

}
