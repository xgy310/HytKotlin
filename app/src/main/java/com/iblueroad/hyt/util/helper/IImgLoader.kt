package com.iblueroad.hyt.util.helper

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/01/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface IImgLoader {

    fun load(imageView: ImageView, url: String)

    fun load(imageView: ImageView, url: String, listener: RequestListener<Drawable>)

    fun load(imageView: ImageView, resId: Int, listener: RequestListener<Drawable>)

    fun loadThumb(imageView: ImageView, url: String)

    fun loadCropCircle(imageView: ImageView, url: String)

    fun loadCropCircle(imageView: ImageView, resId: Int)
}
