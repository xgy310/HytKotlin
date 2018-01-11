package com.fire.zhihudaily.utils

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.iblueroad.hyt.R
import com.iblueroad.hyt.util.AUtils


/**
 * Created by fire on 2017/8/24.
 */

object ImgLoader {


    fun <T> loadImage(model: T): RequestBuilder<*> {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .dontAnimate()

        return Glide.with(AUtils.appContext).load(model).apply(options)
    }

    fun loadImage(model: String): RequestBuilder<*> {
        return Glide.with(AUtils.appContext).asBitmap().load(model)
    }

    fun <T> loadImage(model: T, resourceId: Int): RequestBuilder<*> {
        val options = RequestOptions()
            .placeholder(resourceId)
            .error(resourceId)
            .dontAnimate()
        return Glide.with(AUtils.appContext).load(model).apply(options)
    }


    fun <T> loadImagePic(model: T): RequestBuilder<*> {
        val options = RequestOptions()
            .dontAnimate()
        return Glide.with(AUtils.appContext).load(model).apply(options)
    }
}



