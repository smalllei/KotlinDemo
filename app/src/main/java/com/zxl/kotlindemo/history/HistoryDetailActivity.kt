package com.zxl.kotlindemo.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.ApiCallback
import com.zxl.kotlindemo.base.ApiClient
import com.zxl.kotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_history_detail.*
import kotlinx.android.synthetic.main.item_history_detail.view.*
import org.reactivestreams.Subscription
import java.io.Serializable

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

    @SuppressLint("NewApi")
    override fun initView() {

        history_detail_gv.adapter = gvAdapter
        data();
        history_detail_gv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            //图片的宽高，位置信息
            for (index in 0..datas.size - 1) {
                var urls = datas[index]
                val view: View = history_detail_gv.getChildAt(index)
                urls.imageViewHeight = view.height
                urls.imageViewWidth = view.width
                val points = IntArray(2)
                view.getLocationInWindow(points)
                urls.imageViewX = points[0]
                urls.imageViewY = points[1] - getStatusHeight(this)
               // urls.imageViewY = points[1] - statusHeight
            }
//            val intent=Intent(this,TestActivity::class.java)
//            intent.putExtra(ImageActivity.IMAGE_URL, datas as Serializable)
//            intent.putExtra(ImageActivity.IMAGE_INDEX, i)
//            startActivity(intent)

            val b = Bundle()
            Log.e("HistoryDetailActivity", datas[0].url)
            b.putSerializable(ImageActivity.IMAGE_URL, datas as Serializable)
            b.putInt(ImageActivity.IMAGE_INDEX, i)
            startActivity(ImageActivity::class.java, b)
            overridePendingTransition(0, 0)
        }

    }

    fun data() {
        addDisposable(ApiClient.retrofit().loadOnHistoryDetail("812c1e47851fda4c18a157cd89917c84", intent.getIntExtra("id", 1)), object : ApiCallback<HistoryDetailModel>() {
            override fun onSuccess(model: HistoryDetailModel) {
                history_detail_content.text = model.result[0].content
                history_detail_title.text = model.result[0].title
                datas.clear()
                datas.addAll(model.result[0].picUrl)
                Log.e("HistoryDetailActivity", datas.size.toString())
                gvAdapter.notifyDataSetChanged()

            }

            override fun onFailure(msg: String?) {
                Log.e("onFailure", msg)

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

    /**
     * 获得状态栏的高度
     */
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val o = clazz.newInstance()
            val height = clazz.getField("status_bar_height").get(clazz).toString().toInt()
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight

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
            Glide.with(context)
                    .load(ivList[position].url)
                    .crossFade()
                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略
                    .into(v.item_history_detail_iv)
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