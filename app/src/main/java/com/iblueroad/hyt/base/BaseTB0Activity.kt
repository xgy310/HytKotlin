package com.iblueroad.hyt.base

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.iblueroad.hyt.R

/**
 * Created by SkyXiao on 2017/9/7.
 */
abstract class BaseTB0Activity : BaseActivity() {


    var tool_bar: Toolbar? = null
    private var isShowBackBar = true

    /**
     * 初始化 Toolbar
     *
     * @param resId 标题 Title 的 resId
     */
    protected fun initToolbar(resId: Int): Toolbar? {
        initToolbar(getString(resId))
        return tool_bar
    }

    /**
     * 初始化 Toolbar
     *
     * @param title 标题 Title
     */
    protected fun initToolbar(title: String): Toolbar? {
        tool_bar = findViewById(R.id.tool_bar);
        if (null != tool_bar) {
            setSupportActionBar(tool_bar)
            val actionBar = supportActionBar
            tool_bar!!.title = title
            actionBar?.setDisplayHomeAsUpEnabled(isShowBackBar)
        }
        return tool_bar
    }

    /**
     * @param isShow 是否显示返回键
     */
    fun setBackBar(isShow: Boolean) {
        isShowBackBar = isShow
//        if (null == toolbar) return
    }
    //
    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        if (this instanceof AboutActivity) {
    //            return super.onCreateOptionsMenu(menu);
    //        }
    //        getMenuInflater().inflate(R.menu.menu_main, menu);
    //        return true;
    //    }

    //    @Override
    //    public boolean onMenuItemClick(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.menu_main_about:
    //                startActivity(new Intent(BaseTBActivity.this, AboutActivity.class));
    //                break;
    //        }
    //        return true;
    //    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
