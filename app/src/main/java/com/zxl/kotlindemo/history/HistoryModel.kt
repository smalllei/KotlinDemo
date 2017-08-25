package com.zxl.kotlindemo.history

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 16:41
 * @description:
 */

data class HistoryModel(val result: List<HistoryBean>,
                        val error_code: Int,
                        val reason: String) {
    class HistoryBean(val e_id: Int,
                      val title: String,
                      val day: String,
                      val date: String
    )

}