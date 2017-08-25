package com.zxl.kotlindemo.custview

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/25
 * @time: 17:21
 * @description:
 */
class ImageViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    /** 修复图片在ViewPager控件中缩放报错的BUG */
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException){
            ex.printStackTrace()
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        }catch (ex : IllegalArgumentException){
            ex.printStackTrace()
        }
        return false
    }

}