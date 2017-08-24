package com.zxl.kotlindemo.history

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_history.*

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 16:32
 * @description:  历史的今天
 */
class HistoryActivity : BaseActivity(){
    override val layoutId: Int
        get() = R.layout.activity_history

    override fun initView() {
        history_recycler.layoutManager=LinearLayoutManager(this)

    }

    override fun initEvent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun widgetClick(v: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}