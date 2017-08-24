package com.zxl.kotlindemo.history

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 16:41
 * @description:
 */

data class HistoryModel(val result: List<HistoryBean>,
                        val error: Int,
                        val reason: String) {
    class HistoryBean(val _id: Int,
                      val title: String,
                      val pic: String,
                      val year: Int,
                      val month: Int,
                      val day: Int,
                      val des: String,
                      val lunar: String
    )

}