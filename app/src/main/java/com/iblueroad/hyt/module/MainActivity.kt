package com.iblueroad.hyt.module

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import com.iblueroad.hyt.R
import com.iblueroad.hyt.base.BaseActivity
import com.iblueroad.hyt.base.BaseFragment
import com.iblueroad.hyt.module.comm.CommTabFragment
import com.iblueroad.hyt.module.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_main

    private var mLastFragment: Fragment? = null

    override fun initView() {
//        CrashReport.testJavaCrash()
        navigation_view.setOnNavigationItemSelectedListener({ item ->

            val transaction = supportFragmentManager.beginTransaction()
            if (null != mLastFragment) transaction.hide(mLastFragment)
            val fragment = TabFragment.from(item.itemId).fragment()
            if (!fragment!!.isAdded) transaction.add(R.id.fl_content, fragment)
            transaction.show(fragment).commit()
            mLastFragment = fragment
//            getToolbar().setVisibility(if (R.id.menu_tab_picture == item.itemId) View.GONE else View.VISIBLE)
            true

        })

        navigation_view.selectedItemId = R.id.menu_tab_picture
    }

    override fun onDestroy() {
        TabFragment.onDestroy()
        super.onDestroy()
    }

    private enum class TabFragment constructor(@param:IdRes private val menuId: Int, private val mFragment: BaseFragment) {
        PIC(R.id.menu_tab_picture, CommTabFragment.newInstance(CommTabFragment.FRAG_TYPE_PIC) as BaseFragment),
        WORD(R.id.menu_tab_word, CommTabFragment.newInstance(CommTabFragment.FRAG_TYPE_WORD) as BaseFragment),
        TRIP(R.id.menu_tab_trip, CommTabFragment.newInstance(CommTabFragment.FRAG_TYPE_TRIP) as BaseFragment),
        PROFILE(R.id.menu_tab_profile, ProfileFragment.newInstance() as BaseFragment);

        private var fragment: Fragment? = mFragment

        fun fragment(): Fragment? {
            if (fragment == null) {
                Fragment()
            }
            return mFragment
        }

        companion object {
            fun from(itemId: Int): TabFragment {
                return values().firstOrNull { it.menuId == itemId } ?: PIC
            }

            fun onDestroy() {
                for (fragment in values()) {
                    fragment.fragment = null
                }
            }
        }
    }
}
