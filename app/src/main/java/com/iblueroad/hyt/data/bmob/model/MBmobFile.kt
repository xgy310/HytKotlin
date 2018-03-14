package com.iblueroad.hyt.data.bmob.model

import cn.bmob.v3.datatype.BmobFile
import java.io.File

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/03/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MBmobFile(file: File?) : BmobFile(file) {

     public override fun setFilename(filename: String?) {
        super.setFilename(filename)
    }
}