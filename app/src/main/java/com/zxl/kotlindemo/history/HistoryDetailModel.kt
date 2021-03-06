package com.zxl.kotlindemo.history

import java.io.Serializable

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/25
 * @time: 14:11
 * @description:
 */
data class HistoryDetailModel(val reason: String,
                              val error_code: Int,
                              val result: ArrayList<HistoryDetailBean>) : Serializable {
    data class HistoryDetailBean(val e_id: Int,
                                 val title: String,
                                 val content: String,
                                 val picNo: String,
                                 val picUrl: ArrayList<PicUrlBean>) : Serializable {


        data class PicUrlBean(val pic_title: String,
                              val id: Int,
                              val url: String,
                              var imageViewWidth: Int,
                              var imageViewHeight: Int,
                              var imageViewX: Int,
                              var imageViewY: Int) : Serializable
    }
}