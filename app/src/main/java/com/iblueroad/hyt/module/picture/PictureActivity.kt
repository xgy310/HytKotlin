package com.iblueroad.hyt.module.picture

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseBackActivity
import com.iblueroad.hyt.util.extensions.get
import com.iblueroad.hyt.util.extensions.toast
import kotlinx.android.synthetic.main.activity_picture.*
import kotlinx.android.synthetic.main.activity_top_bar.*

class PictureActivity : BaseBackActivity() {
    private var mPhotoViewAttacher: PhotoViewAttacher? = null
    private var mIsToolbarHidden: Boolean = false
    private var mGirlImgUrl: String? = null

    override val layoutResId = R.layout.activity_picture

    private fun readIntent() {
        val intent = this.intent
        if (intent.hasExtra(GIRL_IMG_URL)) {
            mGirlImgUrl = intent.getStringExtra(GIRL_IMG_URL)
        }
    }

    override fun initView() {
        readIntent()

        mPhotoViewAttacher = PhotoViewAttacher(photo_view)
        mPhotoViewAttacher!!.setOnClickListener { toggleToolbar() }
        mPhotoViewAttacher!!.setOnLongClickListener {
            showSaveGirlDialog()
            true
        }
        Glide.with(this)
            .load(mGirlImgUrl)
//            .fitCenter()
//            .crossFade()
//            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(photo_view)

//        tool_bar = initToolbar("大美女")
    }

    private fun showSaveGirlDialog() {
        AlertDialog.Builder(this).setMessage("保存图片")
            .setNegativeButton(android.R.string.cancel) { anInterface, i -> anInterface.dismiss() }
            .setPositiveButton(android.R.string.ok) { anInterface, i ->
                anInterface.dismiss()
                toast("点击了保存")
            }
            .show()
    }

    private fun toggleToolbar() {
        tool_bar.animate()
            .translationY(if (mIsToolbarHidden) 0.0F else (-(tool_bar.height )).toFloat())
            .setInterpolator(DecelerateInterpolator(2f))
            .start()
        mIsToolbarHidden = (!mIsToolbarHidden)!!
    }
//
//    private fun toggleToolbar() {
//        tool_bar.animate()
//            .translationY(if (mIsToolbarHidden) 0.0F else (-(tool_bar.height + v_fake_status_bar.height)).toFloat())
//            .setInterpolator(DecelerateInterpolator(2f))
//            .start()
//        mIsToolbarHidden = (!mIsToolbarHidden)!!
//    }

    companion object {
        private val GIRL_IMG_URL = "girl_img_url"

        fun start(activity: Activity?, girlUrl: String) {
            if (activity == null || TextUtils.isEmpty(girlUrl)) return

            val intent = Intent(activity, PictureActivity::class.java)
            intent.putExtra(GIRL_IMG_URL, girlUrl)
            activity.startActivity(intent)

        }

        fun start(activity: Activity?, imageView: ImageView, girlUrl: String) {
            if (activity == null || TextUtils.isEmpty(girlUrl)) return

            val intent = Intent(activity, PictureActivity::class.java)
            intent.putExtra(GIRL_IMG_URL, girlUrl)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions.makeSceneTransitionAnimation(activity, imageView, activity.get(R.string.girl_photo_describe))
                } else null
                activity.startActivity(intent, options!!.toBundle())
            } else {
                //            让新的Activity从一个小的范围扩大到全屏
                val options = ActivityOptionsCompat.makeScaleUpAnimation(imageView, imageView
                    .width / 2, imageView.height / 2, 0, 0)
                ActivityCompat.startActivity(activity, intent, options.toBundle())
            }
        }
    }
}
