package com.fire.zhihudaily.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by fire on 2017/12/9.
 * Date：2017/12/9
 * Author: fire
 * Description:
 */
object FileUtils {

    fun readAssetsTxt(context: Context, fileName: String): String {
        try {
            val `is` = context.assets.open(fileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            return String(buffer, Charset.forName("utf-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getPictureFile(file: String): File? {
        return if (!TextUtils.isEmpty(file)) {
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            if (dir == null) null else File(dir, file)
            null
        } else null
    }

     fun parseUri2File(activity:Activity,uri: Uri?): File? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val actualimagecursor = activity.managedQuery(uri, proj, null, null, null)
        val actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        actualimagecursor.moveToFirst()

        val img_path = actualimagecursor.getString(actual_image_column_index)
        return File(img_path)
    }
}