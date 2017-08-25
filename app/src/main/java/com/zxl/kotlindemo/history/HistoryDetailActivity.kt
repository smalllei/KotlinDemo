package com.zxl.kotlindemo.history

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.ApiCallback
import com.zxl.kotlindemo.base.ApiClient
import com.zxl.kotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_history_detail.*
import kotlinx.android.synthetic.main.item_history_detail.view.*
import org.reactivestreams.Subscription

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/25
 * @time: 14:10
 * @description:
 */
class HistoryDetailActivity : BaseActivity() {

    val datas: ArrayList<HistoryDetailModel.HistoryDetailBean.PicUrlBean> = ArrayList<HistoryDetailModel.HistoryDetailBean.PicUrlBean>()
    val gvAdapter: MyGvAdapter = MyGvAdapter(this@HistoryDetailActivity, datas)
    override val layoutId: Int
        get() = R.layout.activity_history_detail

    override fun initView() {

        history_detail_gv.adapter = gvAdapter
        data();

    }

    fun data() {
        addDisposable(ApiClient.retrofit().loadOnHistoryDetail("812c1e47851fda4c18a157cd89917c84", intent.getIntExtra("id",1)), object : ApiCallback<HistoryDetailModel>() {
            override fun onSuccess(model: HistoryDetailModel) {
                history_detail_content.text = model.result[0].content
                history_detail_title.text = model.result[0].title
                datas.clear()
                datas.addAll(model.result[0].picUrl)
                gvAdapter.notifyDataSetChanged()

            }

            override fun onFailure(msg: String?) {
                Log.e("onFailure",msg)

            }

            override fun onFinish() {
            }

            override fun onSubscribe(s: Subscription?) {
            }

        })
    }

    override fun initEvent() {
    }

    override fun widgetClick(v: View) {
    }


    class MyGvAdapter(val context: Context, val ivList: List<HistoryDetailModel.HistoryDetailBean.PicUrlBean>) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var holder: GvHolder
            var v: View
            if (convertView == null) {
                v = LayoutInflater.from(context).inflate(R.layout.item_history_detail, null, false)
                holder = GvHolder(v)
                v.tag = holder
            } else {
                v = convertView
                holder = v.tag as GvHolder
            }
            Glide.with(context).load(ivList[position].url).into(v.item_history_detail_iv)
            v.item_history_detail_tv.text = ivList[position].pic_title
            return v
        }

        override fun getItem(p0: Int): Any {
            return ivList[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return ivList.size
        }

        override fun notifyDataSetChanged() {
            super.notifyDataSetChanged()
        }

        class GvHolder(var holderView: View)
    }
}