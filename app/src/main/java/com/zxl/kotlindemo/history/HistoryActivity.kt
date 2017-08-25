package com.zxl.kotlindemo.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.ApiCallback
import com.zxl.kotlindemo.base.ApiClient
import com.zxl.kotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_history.*
import org.reactivestreams.Subscription

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 16:32
 * @description:  历史的今天
 */
class HistoryActivity : BaseActivity(), HistoryAdapter.OnClickItemListener {

    val lists: ArrayList<HistoryModel.HistoryBean> = ArrayList<HistoryModel.HistoryBean>()
    override val layoutId: Int
        get() = R.layout.activity_history

    override fun initView() {
        history_recycler.layoutManager = LinearLayoutManager(this)
        history_recycler.adapter = HistoryAdapter(lists, this, this)


    }

    override fun onClickItem(position: Int) {

        val b: Bundle = Bundle()
        b.putInt("id", lists[position].e_id)
        startActivity(HistoryDetailActivity::class.java,b )
    }


    override fun initEvent() {
        history_search.setOnClickListener(this)
    }

    override fun widgetClick(v: View) {
        when (v.id) {
            R.id.history_search -> date()
        }
    }

    fun date() {

        addDisposable(ApiClient.retrofit().loadOnHistory("812c1e47851fda4c18a157cd89917c84", "${history_month.text}/${history_day.text}"), object : ApiCallback<HistoryModel>() {
            override fun onSubscribe(s: Subscription?) {

            }

            override fun onSuccess(model: HistoryModel) {
                Log.e("onSuccess", Gson().toJson(model))
                lists.clear()
                lists.addAll(model.result)
                history_recycler.adapter.notifyDataSetChanged()
            }

            override fun onFailure(msg: String?) {
                Log.e("onFailure", msg)
                showToast(msg!!)
            }

            override fun onFinish() {

            }

        })
    }
}