package com.iblueroad.hyt.module.picture

import android.annotation.TargetApi
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore.Images.ImageColumns
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseTBActivity
import com.iblueroad.hyt.util.extensions.onClick
import com.iblueroad.hyt.util.extensions.toast
import kotlinx.android.synthetic.main.activity_post_pic.*
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
    override val layoutResId = R.layout.activity_post_pic

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
        btn_album.onClick { startActivityForResult(pick(CONTENT_TYPE_IMAGE), REQUEST_CODE_PICK_PICTURE) }
        btn_camera.onClick { toast("btn_camera") }
    }

    fun pick(type: String): Intent {
        val intent = Intent("android.intent.action.PICK")
        intent.type = type
//        if (multiple && Build.VERSION.SDK_INT >= 18) {
//            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true)
//        }

        return intent
    }


    @TargetApi(8)
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
                var tempImgPath: String = data.data.path

               var  exifInterface:ExifInterface =  ExifInterface(getRealFilePath(this,data.data))
                println(exifInterface.getAttribute(ExifInterface.TAG_DATETIME))
                println(exifInterface.getAttribute(ExifInterface.TAG_MODEL))
                Glide.with(this).load(data.data).into(iv_picture)
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

    fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri!!.getScheme()
        var data: String? = null
        if (scheme == null)
            data = uri!!.getPath()
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri!!.getPath()
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.getContentResolver().query(uri, arrayOf(ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor!!.moveToFirst()) {
                    val index = cursor!!.getColumnIndex(ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor!!.getString(index)
                    }
                }
                cursor!!.close()
            }
        }
        return data
    }

}