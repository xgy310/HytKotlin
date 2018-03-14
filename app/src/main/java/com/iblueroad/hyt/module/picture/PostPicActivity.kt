package com.iblueroad.hyt.module.picture

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.provider.MediaStore.Images.ImageColumns
import android.text.TextUtils
import android.view.View
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadFileListener
import com.bumptech.glide.Glide
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseTBActivity
import com.iblueroad.hyt.data.bmob.model.BMUser
import com.iblueroad.hyt.data.bmob.model.ImgFeed
import com.iblueroad.hyt.data.bmob.model.MBmobFile
import com.iblueroad.hyt.data.view_model.UserVM
import com.iblueroad.hyt.util.extensions.onClick
import com.iblueroad.hyt.util.extensions.toast
import kotlinx.android.synthetic.main.activity_post_pic_album.*
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
class PostPicActivity : BaseTBActivity() {
    override val layoutResId = R.layout.activity_post_pic_album

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
//        btn_album.onClick { startActivityForResult(pick(CONTENT_TYPE_IMAGE), REQUEST_CODE_PICK_PICTURE) }
//        btn_camera.onClick { toast("btn_camera") }
        fbtn_upload_pic.onClick { postPic() }

        startActivityForResult(pick(CONTENT_TYPE_IMAGE), REQUEST_CODE_PICK_PICTURE)
    }

    private fun postPic() {
        if (null != mImgPath) {
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
                        val imgFeed = ImgFeed()
                        imgFeed.imgId = "img_" + UserVM.getMobile() + "_" + nowTime
                        imgFeed.picUrl = bmobFile.fileUrl//返回的上传文件的完整地址
                        val imgUser = BMUser()
                        imgUser.objectId = UserVM.getObjId()
                        imgFeed.own = imgUser

                        imgFeed.save(object : SaveListener<String>() {
                            override fun done(objectId: String?, e: BmobException?) {
                                if (e == null) {
                                    toast("提交成功, 小编将在5个工作日内筛选后将在首页展示")
                                    finish()
                                } else {
                                    toast("提交失败：" + e.message)
                                }
                            }
                        })
                    } else {
                        toast("上传文件失败：\n" + e.message)
                    }
                }

            })
        } else {
            toast("你未选中图片")
        }

    }

    fun pick(type: String): Intent {
        val intent = Intent("android.intent.action.PICK")
        intent.type = type
//        if (multiple && Build.VERSION.SDK_INT >= 18) {
//            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true)
//        }

        return intent
    }


    fun getPictureFile(file: String?): File? {
        return if (TextUtils.isEmpty(file)) {
            null
        } else {
            val dir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            if (dir == null) null else File(dir, file!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return
        when (requestCode) {
            REQUEST_CODE_PICK_PICTURE -> {
                if (data == null) return
                var tUri = data.data
                mImgPath = parseUri2File(tUri)
                var tempImgPath: String = tUri.path

                var exifInterface = ExifInterface(getRealFilePath(this, data.data))
                println(exifInterface.getAttribute(ExifInterface.TAG_DATETIME))//拍摄时间，取决于设备设置的时间
                println(exifInterface.getAttribute(ExifInterface.TAG_APERTURE_VALUE))//光圈
                println(exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH))//焦距
                println(exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME))//曝光时间
                println(exifInterface.getAttribute(ExifInterface.TAG_MODEL))//设备型号
                ll_pick_pic.visibility= View.GONE
                Glide.with(this).load(tUri).into(iv_picture)
//                startActivityForResult(IntentUtils.cropImage(data.data, UriUtils.fromFile(mImgPath), false, dp(100), dp(100)), REQUEST_CODE_CROP_IMAGE)
            }
            REQUEST_CODE_TAKE_PICTURE -> {
                mImgPath = getPictureFile(".jpg")
//                mImgPath = getPictureFile(UserUtils.getLatestId() + "_" + DateUtils.getCurDateFormat() + ".jpg")
//                startActivityForResult(
//                    IntentUtils.cropImage(UriUtils.fromFile(EnvUtils.getPictureFile("capture.jpg")), UriUtils.fromFile(mImgPath), false, dp(100), dp(100)),
//                    REQUEST_CODE_CROP_IMAGE)
            }
//            REQUEST_CODE_CROP_IMAGE -> userVM.uploadAvatar(mImgPath)
        }
    }

    private fun parseUri2File(uri: Uri?): File? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val actualimagecursor = managedQuery(uri, proj, null, null, null)
        val actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        actualimagecursor.moveToFirst()

        val img_path = actualimagecursor.getString(actual_image_column_index)
        return File(img_path)
    }

    fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri, arrayOf(ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

}