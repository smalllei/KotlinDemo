package com.zxl.kotlindemo.history

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.zxl.kotlindemo.R

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/30
 * @time: 11:20
 * @description:
 */
class TestActivity :Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val datas = intent.getSerializableExtra(ImageActivity.IMAGE_URL)  as ArrayList<HistoryDetailModel.HistoryDetailBean.PicUrlBean>
        var index = intent.getIntExtra(ImageActivity.IMAGE_INDEX, 0)

        Log.e("Test",datas.size.toString())
    }
}