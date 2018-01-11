package com.iblueroad.hyt.util.extensions

import android.content.res.Configuration
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

/**
 * Created by Chatikyan on 19.09.2017.
 */

//fun bundled(value: Any) {
//    val args = Bundle()
//
//    when (value) {
//        is Int -> args.putInt(key, value)
//        is Long -> args.putLong(key, value)
//        is String -> args.putString(key, value)
//        is Parcelable -> args.putParcelable(key, value)
//        is Serializable -> args.putSerializable(key, value)
//        else -> throw UnsupportedOperationException("${value.javaClass.simpleName} type not supported yet!!!")
//    }
//    arguments = args
//}

inline infix fun <reified T> Fragment.extraWithKey(key: String): T {
    val value: Any = arguments!![key]
    return value as T
}

fun Fragment.isPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

infix fun Fragment.getColor(colorId: Int) = context?.let { ContextCompat.getColor(it, colorId) }
infix fun Fragment.getStr(strId: Int) = context?.getString(strId)
