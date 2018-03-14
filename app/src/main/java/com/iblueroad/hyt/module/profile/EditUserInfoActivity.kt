package com.iblueroad.hyt.module.profile

import android.content.Intent
import android.text.Selection
import android.text.Spannable
import android.view.View
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
import com.bumptech.glide.Glide
import com.fire.zhihudaily.utils.FileUtils
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseTBActivity
import com.iblueroad.hyt.data.bmob.model.MBmobFile
import com.iblueroad.hyt.data.view_model.UserVM
import com.iblueroad.hyt.util.extensions.onClick
import com.iblueroad.hyt.util.extensions.toast
import kotlinx.android.synthetic.main.activity_edit_userinfo.*
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
class EditUserInfoActivity : BaseTBActivity() {
    override val layoutResId = R.layout.activity_edit_userinfo

    val CONTENT_TYPE_IMAGE = "image/*"

    private val REQUEST_CODE_PICK_PICTURE = 1
    private val REQUEST_CODE_TAKE_PICTURE = 2
    private val REQUEST_CODE_CROP_IMAGE = 3
    private val REQUEST_CODE_CAMERA = 999
    private val REQUEST_CODE_WRITE_READ = 666

    var mImgPath: File? = null


    override fun initView() {
        setBackBar(true)
        initToolbar("上传图片")
        ll_pick_pic.onClick { startActivityForResult(pick(CONTENT_TYPE_IMAGE), REQUEST_CODE_PICK_PICTURE) }
        iv_user_face.onClick { startActivityForResult(pick(CONTENT_TYPE_IMAGE), REQUEST_CODE_PICK_PICTURE) }
//        btn_album.onClick { startActivityForResult(pick(CONTENT_TYPE_IMAGE), REQUEST_CODE_PICK_PICTURE) }
//        btn_camera.onClick { toast("btn_camera") }
        btn_confirm.onClick { postPic() }

        val curUser = UserVM.getCurUser()
        if (null != curUser) {
            val tFaceUrl = curUser.faceUrl
            if (null != tFaceUrl && !tFaceUrl.isEmpty()) {
                ll_pick_pic.visibility = View.GONE
                Glide.with(this).load(curUser.faceUrl).into(iv_user_face)
            }
            val tUsername: String? = curUser.username
            if (null != tUsername && !tUsername.isEmpty()) {
                et_username.setText(tUsername)
                val tString = et_username.text
                if (null != tString) {
                    val spanText = tString as Spannable
                    Selection.setSelection(spanText, tString.length)// 将光标移动到最后
                }
            }
        }
    }

    private fun postPic() {

        val tUsername = et_username.text
        if (null == tUsername || tUsername.isEmpty()) {
            toast("昵称不能为空")
            return
        }
        if (null == mImgPath) {
            toast("你未选中图片")
        } else {

            val picPath: String = mImgPath!!.absolutePath
            val bmobFile = MBmobFile(File(picPath))

            val nowTime = System.currentTimeMillis()
            if (null != picPath) {
                val preFix = picPath.substring(picPath.lastIndexOf(".") + 1)
                //TODO 不知道为什么这里没有起作用
                bmobFile.filename = "an_" + UserVM.getMobile() + "_" + nowTime + "." + preFix
            }
            bmobFile.uploadblock(object : UploadFileListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        val curUser = UserVM.getCurUser()
                        if (null != curUser) {
                            curUser.faceUrl = bmobFile.fileUrl
                            curUser.username = tUsername.toString()
                            curUser.update(object : UpdateListener() {
                                override fun done(e: BmobException?) {
                                    if (e == null) {
                                        UserVM.saveCurUser(curUser)
                                        finish()
                                        toast("更新用户信息成功")
                                    } else {
                                        toast("更新用户信息失败:" + e.message)
                                    }
                                }
                            })
                        }
                    } else {
                        toast("上传文件失败：\n" + e.message)
                    }
                }
            })
        }
    }

    fun pick(type: String): Intent {
        val intent = Intent("android.intent.action.PICK")
        intent.type = type
        return intent
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
                Glide.with(this).load(tUri).into(iv_user_face)
            }
            REQUEST_CODE_TAKE_PICTURE -> {
                mImgPath = FileUtils.getPictureFile(".jpg")
            }
        }
    }


}