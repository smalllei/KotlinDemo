package com.zxl.kotlindemo.base


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

    @GET("todayOnhistory/queryEvent.php")
    fun loadOnHistory(@Query("key") key: String, @Query("date") date: String): Observable<HistoryModel>
}