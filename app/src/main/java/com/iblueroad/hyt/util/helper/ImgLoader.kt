package com.iblueroad.hyt.util.helper

import com.iblueroad.hyt.util.glide.GlideImpl

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/01/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object ImgLoader {

    private var imageLoader: IImgLoader? = null

    fun get(): IImgLoader {
        if (imageLoader == null) {
            synchronized(ImgLoader::class.java) {
                if (imageLoader == null) {
                    imageLoader = GlideImpl()
                }
            }
        }
        return imageLoader!!
    }
}