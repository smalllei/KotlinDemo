package com.zxl.kotlindemo.custview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/25
 * @time: 15:23
 * @description:  scrollView显示不全
 */
class MyGridView : GridView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}