package com.iblueroad.hyt.module.trip

import android.content.Intent
import android.view.View
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadFileListener
import com.bumptech.glide.Glide
import com.fire.zhihudaily.utils.FileUtils
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseTBActivity
import com.iblueroad.hyt.data.bmob.model.Journey
import com.iblueroad.hyt.data.bmob.model.MBmobFile
import com.iblueroad.hyt.data.view_model.UserVM
import com.iblueroad.hyt.util.DateUtils
import com.iblueroad.hyt.util.extensions.onClick
import com.iblueroad.hyt.util.extensions.toast
import kotlinx.android.synthetic.main.activity_post_journey.*
import java.io.File


/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/01/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PostJourneyActivity : BaseTBActivity() {
    override val layoutResId = R.layout.activity_post_journey

    val CONTENT_TYPE_IMAGE = "image/*"

    private val REQUEST_CODE_PICK_PICTURE = 1
    private val REQUEST_CODE_TAKE_PICTURE = 2
    private val REQUEST_CODE_CROP_IMAGE = 3
    private val REQUEST_CODE_CAMERA = 999
    private val REQUEST_CODE_WRITE_READ = 666

    var mImgPath: File? = null


    override fun initView() {
        setBackBar(true)
        initToolbar("发布文章")
        ll_pick_pic.onClick { startActivityForResult(pick(CONTENT_TYPE_IMAGE), REQUEST_CODE_PICK_PICTURE) }
        fbtn_upload_pic.onClick { postPic() }
    }

    fun pick(type: String): Intent {
        val intent = Intent("android.intent.action.PICK")
        intent.type = type
        return intent
    }

    private fun postPic() {
        val title = ed_title.text
        if (null == title || title.isEmpty()) {
            toast("文章标题不能为空")
            return
        }
        val dateStr = ed_date.text
        if (null == dateStr || dateStr.isEmpty()) {
            toast("时间不能为空")
            return
        }
        val date = DateUtils.parse2Long(DateUtils.dateFormat1, dateStr.toString())
        val daysStr = ed_days.text
        if (null == daysStr || daysStr.isEmpty()) {
            toast("时间不能为空")
            return
        }
        val days = Integer.parseInt(daysStr.toString())
        val loc = ed_loc.text
        if (null == loc || loc.isEmpty()) {
            toast("地址不能为空")
            return
        }
        val content = ed_content.text
        if (null == content || content.isEmpty()) {
            toast("内容不能为空")
            return
        }


        if (null == mImgPath) {
            toast("你未选中展示图片")
        } else {
            var picPath: String = mImgPath!!.absolutePath
//            toast(picPath)

            var bmobFile = MBmobFile(File(picPath))

            val nowTime = System.currentTimeMillis()
            if (null != picPath) {
                val preFix = picPath.substring(picPath.lastIndexOf(".") + 1)
                //TODO 不知道为什么这里没有起作用
                bmobFile.filename = "an_" + UserVM.getMobile() + "_" + nowTime + "." + preFix
            }
            bmobFile.uploadblock(object : UploadFileListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        val journey = Journey()

                        journey.coverUrl = bmobFile.fileUrl

                        val curUser = UserVM.getCurUser()
                        if (null != curUser) {

                            journey.own = curUser

                            val tFaceUrl = curUser.faceUrl
                            journey.userFaceUrl = tFaceUrl ?: ""

                            val tUsername: String? = curUser.username
                            journey.userName = tUsername ?: "佚名"
                        }
                        journey.title = title.toString()
                        journey.date = date
//                        journey.date = System.currentTimeMillis()
                        journey.days = days
                        journey.loc = loc.toString()
                        journey.content = content.toString()

                        journey.save(object : SaveListener<String>() {
                            override fun done(objectId: String?, e: BmobException?) {
                                if (e == null) {
                                    toast("发布成功, 小编将在5个工作日内筛选后将在相关页面展示")
                                } else {
                                    toast("发布失败：${e.message}")
                                }
                            }
                        })
                    } else {
                        toast("""
                            |上传文件失败
                            |${e.message}
                            """.trimMargin())
                    }
                }

            })
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return
        when (requestCode) {
            REQUEST_CODE_PICK_PICTURE -> {
                if (data == null) return
                var tUri = data.data
                mImgPath = FileUtils.parseUri2File(this, tUri)
                ll_pick_pic.visibility = View.GONE
                Glide.with(this).load(tUri).into(iv_picture)
            }
            REQUEST_CODE_TAKE_PICTURE -> {
                mImgPath = FileUtils.getPictureFile(".jpg")
            }
        }
    }

}