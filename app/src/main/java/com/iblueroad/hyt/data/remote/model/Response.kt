package com.iblueroad.hyt.data.remote.model

import com.iblueroad.hyt.data.local.dbflow.entity.Girl

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2017/12/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class GirlResp(var error: Boolean = false, var results: List<Girl>? = null)