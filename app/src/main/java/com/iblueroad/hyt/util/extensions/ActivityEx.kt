package com.iblueroad.hyt.util.extensions

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.support.v4.content.ContextCompat

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : xxx@xx
 *     time   : 2017/12/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
inline fun <reified T> Activity.start() {
  this.startActivity(Intent(this, T::class.java))
}

fun Activity.isPortrait() = this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

infix fun Activity.getColor(colorId: Int) = ContextCompat.getColor(this, colorId)