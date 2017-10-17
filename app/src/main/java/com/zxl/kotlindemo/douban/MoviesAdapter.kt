package com.zxl.kotlindemo.douban

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.douban.model.CastsModel
import com.zxl.kotlindemo.douban.model.MoviesModel

/**
 * @author: smalllei
 * *
 * @date: 2017/10/13
 * *
 * @time: 下午6:52
 * *
 * @description:
 */
class MoviesAdapter(private val context: Context) : BaseQuickAdapter<MoviesModel, BaseViewHolder>(R.layout.item_douban) {

    override fun convert(helper: BaseViewHolder, item: MoviesModel) {
        Glide.with(context).load(item.images.small).into(helper.getView<ImageView>(R.id.item_douban_iv) as ImageView)
        helper.setText(R.id.item_douban_name, item.title)
        val sb = StringBuffer()
        sb.append("演员：")
        for (model in item.casts) {
            sb.append(model.name + "\u3000")

        }
        helper.setText(R.id.item_douban_zhuyan, sb.toString())

        val sb1=StringBuffer()
        sb1.append("导演：")
        for (model in item.directors) {
            sb1.append(model.name + "\u3000")

        }
        helper.setText(R.id.item_douban_daoyan, sb1.toString())

        val sb2=StringBuffer()
        sb2.append("类型:")
        for (s in item.genres) {
            sb2.append(s + "\u3000")
        }

        helper.setText(R.id.item_douban_type, sb2.toString())
    }
}
