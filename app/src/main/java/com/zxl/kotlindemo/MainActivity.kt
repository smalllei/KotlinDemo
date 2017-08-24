package com.zxl.kotlindemo

import android.support.v7.widget.LinearLayoutManager
import android.view.View


import com.zxl.kotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        var items = listOf<String>(
                "历史的今天",
                "java",
                "php",
                "C",
                "C++",
                "android",
                "ios",
                "风萧萧兮易水寒",
                "壮士一去兮不复还",
                "世人笑我太痴颠",
                "我笑他人看不穿"
        )
        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = MainAdapter(items, object : MainAdapter.OnClickItemListener {
            override fun onClickItem(position: Int) {
              showToast(items[position])
            }
        })


    }


    override fun initEvent() {

    }

    override fun widgetClick(v: View) {

    }


}
