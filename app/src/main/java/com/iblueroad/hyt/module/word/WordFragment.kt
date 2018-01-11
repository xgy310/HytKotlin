package com.iblueroad.hyt.module.word

import android.os.Bundle
import android.support.v4.app.Fragment
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseFragment
import com.orhanobut.logger.Logger

/**
 * Created by SkyXiao on 2017/9/15.
 */
class WordFragment : BaseFragment() {

    override val layoutResId = R.layout.frag_word

    override fun initView() {
        var isNewType = arguments?.getBoolean(WordFragment.WORD_TYPE_IS_NEW, false) ?: false
        Logger.d("isNewType" + isNewType)
    }

    companion object {
        private val WORD_TYPE_IS_NEW = "word_type_is_new"

        fun newInstance(isNew: Boolean): Fragment {
            val args = Bundle()
            args.putBoolean(WORD_TYPE_IS_NEW, isNew)
            val fragment = WordFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
