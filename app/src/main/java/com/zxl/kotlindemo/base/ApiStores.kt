package com.zxl.kotlindemo.base


import com.zxl.kotlindemo.history.HistoryDetailModel
import com.zxl.kotlindemo.history.HistoryModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 17:39
 * @description:
 */
interface ApiStores {
    companion object {
        val API_SERVER_URL = "http://v.juhe.cn/"
    }

    /**
     * 查询当日
     */
    @GET("todayOnhistory/queryEvent.php")
    fun loadOnHistory(@Query("key") key: String?="812c1e47851fda4c18a157cd89917c84", @Query("date") date: String): Observable<HistoryModel>

    /**
     * 当日历史详情
     */
    @GET("todayOnhistory/queryDetail.php")
    fun loadOnHistoryDetail(@Query("key") key: String?="812c1e47851fda4c18a157cd89917c84", @Query("e_id") e_id: Int): Observable<HistoryDetailModel>
}