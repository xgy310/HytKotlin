package com.iblueroad.hyt.data.local.dbflow.entity

import com.iblueroad.hyt.data.local.dbflow.FlowDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2017/12/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
//@Table(database = FlowDB::class)
//data class Girl(
//    @PrimaryKey var _id: String? = null,
//    @Column var createdAt: String? = null,
//    @Column var desc: String? = null,
//    @Column var publishedAt: String? = null,
//    @Column var source: String? = null,
//    @Column var type: String? = null,
//    @Column var url: String? = null,
//    @Column var used: Boolean = false,
//    @Column var who: String? = null,
//    @Column var mAge: Int = 0)


@Table(database = FlowDB::class)
class Girl : BaseModel() {
    @PrimaryKey
    var _id: String? = null
    @Column
    var createdAt: String? = null
    @Column
    var desc: String? = null
    @Column
    var publishedAt: String? = null
    @Column
    var source: String? = null
    @Column
    var type: String? = null
    @Column
    var url: String? = null
    @Column
    var used: Boolean = false
    @Column
    var who: String? = null
    @Column
    var mAge: Int = 0
}