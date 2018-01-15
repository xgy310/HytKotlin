package com.iblueroad.hyt.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import java.io.*

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/01/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object ImgUtils {

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return `true`: 成功<br></br>`false`: 失败
     */
    fun save(src: Bitmap,
        filePath: String,
        format: Bitmap.CompressFormat): Boolean {
        return save(src, getFileByPath(filePath), format, false)
    }

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @param format 格式
     * @return `true`: 成功<br></br>`false`: 失败
     */
    fun save(src: Bitmap, file: File, format: Bitmap.CompressFormat): Boolean {
        return save(src, file, format, false)
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @param recycle  是否回收
     * @return `true`: 成功<br></br>`false`: 失败
     */
    fun save(src: Bitmap,
        filePath: String,
        format: Bitmap.CompressFormat,
        recycle: Boolean): Boolean {
        return save(src, getFileByPath(filePath), format, recycle)
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return `true`: 成功<br></br>`false`: 失败
     */
    fun save(src: Bitmap,
        file: File?,
        format: Bitmap.CompressFormat,
        recycle: Boolean): Boolean {
        if (isEmptyBitmap(src) || !createFileByDeleteOldFile(file)) return false
        var os: OutputStream? = null
        var ret = false
        try {
            os = BufferedOutputStream(FileOutputStream(file!!))
            ret = src.compress(format, 100, os)
            if (recycle && !src.isRecycled) src.recycle()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            closeIO(os!!)
        }
        return ret
    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable drawable 对象
     * @return bitmap
     */
    fun drawable2Bitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        val bitmap: Bitmap
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565)
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
    /**
     * 关闭 IO
     *
     * @param closeables closeables
     */
    fun closeIO(vararg closeables: Closeable) {
        if (closeables == null) return
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 判断 bitmap 对象是否为空
     *
     * @param src 源图片
     * @return `true`: 是<br></br>`false`: 否
     */
    private fun isEmptyBitmap(src: Bitmap?): Boolean {
        return src == null || src.width == 0 || src.height == 0
    }

    private fun getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    private fun createFileByDeleteOldFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists() && !file.delete()) return false
        if (!createOrExistsDir(file.parentFile)) return false
        try {
            return file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    private fun createOrExistsDir(file: File?): Boolean {
        // 如果存在，是目录则返回 true，是文件则返回 false，不存在则返回是否创建成功
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

    /**
     * view 转 bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    fun view2Bitmap(view: View?): Bitmap? {
        if (view == null) return null
        val ret = Bitmap.createBitmap(view.width,
            view.height,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(ret)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return ret
    }
    /**
     * bitmap 转 byteArr
     *
     * @param bitmap bitmap 对象
     * @param format 格式
     * @return 字节数组
     */
    fun bitmap2Bytes(bitmap: Bitmap?, format: Bitmap.CompressFormat): ByteArray? {
        if (bitmap == null) return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, 100, baos)
        return baos.toByteArray()
    }

}