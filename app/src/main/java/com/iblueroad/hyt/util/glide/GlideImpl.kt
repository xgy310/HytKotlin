package com.iblueroad.hyt.util.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Option
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.iblueroad.hyt.R
import com.iblueroad.hyt.util.helper.IImgLoader

/**
 * Copyright ©2017 by ruzhan
 */

class GlideImpl : IImgLoader {

    private val normalTransitionOptions = DrawableTransitionOptions()
        .crossFade()

    override fun load(imageView: ImageView, url: String) {
        val options = RequestOptions()
            .set(TIMEOUT_OPTION, TIMEOUT_MS)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)

        Glide.with(imageView.context).load(url).apply(options).transition(normalTransitionOptions).into(imageView)

    }

    override fun load(imageView: ImageView, url: String, listener: RequestListener<Drawable>) {
        val options = RequestOptions().set(TIMEOUT_OPTION, TIMEOUT_MS)

        Glide.with(imageView.context)
            .load(url).apply(options)
            .transition(normalTransitionOptions)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    listener.onLoadFailed(e, model, target, isFirstResource)
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource,
                    isFirstResource: Boolean): Boolean {
                    listener.onResourceReady(resource, model, target, dataSource, isFirstResource)
                    return false
                }
            })
            .into(imageView)
    }

    override fun load(imageView: ImageView, resId: Int, listener: RequestListener<Drawable>) {

        val options = RequestOptions().set(TIMEOUT_OPTION, TIMEOUT_MS)
        Glide.with(imageView.context)
            .load(resId).apply(options)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any,
                    target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    listener.onLoadFailed(e, model, target, isFirstResource)
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any,
                    target: Target<Drawable>, dataSource: DataSource,
                    isFirstResource: Boolean): Boolean {
                    listener.onResourceReady(resource, model, target, dataSource,
                        isFirstResource)
                    return false
                }
            })
            .transition(normalTransitionOptions)
            .into(imageView)
    }

    override fun loadThumb(imageView: ImageView, url: String) {
        val options = RequestOptions().set(TIMEOUT_OPTION, TIMEOUT_MS)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)

        Glide.with(imageView.context)
            .load(url)
            .apply(options)
            .thumbnail(SIZE_MULTIPLIER)
            .transition(normalTransitionOptions)
            .into(imageView)
    }

    override fun loadCropCircle(imageView: ImageView, url: String) {
        val options = RequestOptions().set(TIMEOUT_OPTION, TIMEOUT_MS)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher).transform(MultiTransformation<Bitmap>(CircleCrop()))

        Glide.with(imageView.context)
            .load(url)
            .apply(options)
            .transition(normalTransitionOptions)
            .into(imageView)
    }

    override fun loadCropCircle(imageView: ImageView, resId: Int) {
        val options = RequestOptions().set(TIMEOUT_OPTION, TIMEOUT_MS)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .transform(MultiTransformation<Bitmap>(CircleCrop()))

        Glide.with(imageView.context)
            .load(resId).apply(options)
            .transition(normalTransitionOptions)
            .into(imageView)
    }

    companion object {

        private val KEY_MEMORY = "com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout"

        private val SIZE_MULTIPLIER = 0.3f
        private val TIMEOUT_MS = 16000

        private val TIMEOUT_OPTION = Option.memory(KEY_MEMORY, TIMEOUT_MS)
    }
}
