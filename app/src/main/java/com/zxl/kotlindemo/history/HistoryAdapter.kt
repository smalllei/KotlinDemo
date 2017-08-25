package com.zxl.kotlindemo.history

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.zxl.kotlindemo.R
import kotlinx.android.synthetic.main.item_history.view.*

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/25
 * @time: 12:05
 * @description:
 */
class HistoryAdapter(val lists: List<HistoryModel.HistoryBean>, val context: Context,val listener:OnClickItemListener) : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {
    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HistoryHolder {

        return HistoryHolder(LayoutInflater.from(context).inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.itemView.setOnClickListener { _ -> listener.onClickItem(position) }
        holder.itemView.item_history_iv.setImageResource(R.mipmap.bg)
        holder.itemView.item_history_title.text=lists[position].title
        holder.itemView.item_history_time.text=lists[position].date
        holder.itemView.item_history_des.text=lists[position].day

    }

    class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // var image: ImageView = itemView.findViewById(R.id.item_history_iv)
    }
    interface OnClickItemListener{
        fun onClickItem(position: Int)
    }
}