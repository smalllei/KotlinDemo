package com.zxl.kotlindemo

import android.support.v7.widget.RecyclerView
import android.view.TextureView
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 15:03
 * @description:
 */
class MainAdapter(val lists : List<String>,val listener : OnClickItemListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setPadding(24,12,12,24)
        holder.textView.setTextSize(20F)
        holder.textView.text=lists[position]
        holder.textView.setOnClickListener { _ -> listener.onClickItem(position) }
    }

    override fun getItemCount(): Int =lists.count()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    class ViewHolder(val textView : TextView) : RecyclerView.ViewHolder(textView) {

    }
    interface OnClickItemListener{
        fun onClickItem(position : Int)
    }
}