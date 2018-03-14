package io.armcha.ribble.presentation.adapter

import android.app.Activity
import android.view.View
import com.iblueroad.hyt.R
import com.iblueroad.hyt.data.bmob.model.ImgFeed
import com.iblueroad.hyt.module.picture.PictureActivity
import com.iblueroad.hyt.util.NetUtils
import com.iblueroad.hyt.util.ToastUtils
import com.iblueroad.hyt.util.extensions.onClick
import com.iblueroad.hyt.util.glide.clear
import com.iblueroad.hyt.util.glide.load
import kotlinx.android.synthetic.main.list_item_pic.view.*
import org.jetbrains.annotations.NonNls

/**
 * Created by Chatikyan on 26.08.2017.
 */
class PictureAdapter constructor(activity: Activity)
    : AbstractAdapter<ImgFeed>(mutableListOf(), R.layout.list_item_pic) {
//    : AbstractAdapter<Girl>(mutableListOf(), R.layout.list_item_pic) {

    private val mActivity = activity

    override fun onViewRecycled(itemView: View) {
        itemView.iv_picture.clear()
    }

    override fun View.bind(item: ImgFeed) {
        iv_picture.load(item.picUrl)
//        with(item) {
//            commentAuthor.text = user?.username
//            userImage.load(user?.avatarUrl, TransformationType.CIRCLE)
//            userCommentLikeCount.text = item.likeCount.toString()
//        }
        iv_picture.onClick {
            if (NetUtils.isConnected()) {
                PictureActivity.start(mActivity, iv_picture, item.picUrl!!)
            } else {
                ToastUtils.show("网络异常")
            }
        }
    }

    fun clearPictureList() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun setPictureList(pics: List<ImgFeed>?) {


        pics?.forEach { add(it) }
//        var list = itemList.toMutableList()
//        list.addAll(girls!!)
//        itemList = list
//        itemList.toMutableList().addAll(girls!!)
//        itemList = itemList
//        if (itemList.size<girls!!.size){
//            itemList=girls
//        }else{
//         var list =   itemList.toMutableList().addAll(girls)
//            itemList=list as List<Girl>
////            girls.forEach { add(it) }
//        }
//        notifyDataSetChanged()
    }

    fun addList(@NonNls pics: List<ImgFeed>) {
        val curSize = itemCount
        itemList.addAll(pics)
        notifyItemRangeInserted(if (0 == curSize) 0 else curSize - 1, pics.size)
    }
}