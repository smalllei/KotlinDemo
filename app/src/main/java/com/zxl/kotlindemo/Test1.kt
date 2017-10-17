package com.zxl.kotlindemo

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.view.ViewTreeObserver

/**
 * @author: smalllei
 * *
 * @date: 2017/10/16
 * *
 * @time: 下午2:42
 * *
 * @description:
 */
class Test1 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val swipeRefreshLayout = SwipeRefreshLayout(this)
        swipeRefreshLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                swipeRefreshLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)

            }
        })
    }
}
