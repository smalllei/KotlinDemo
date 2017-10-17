package com.zxl.kotlindemo.base


import com.zxl.kotlindemo.douban.model.DatasModel
import com.zxl.kotlindemo.douban.model.MovieModel
import com.zxl.kotlindemo.douban.model.MoviesModel
import com.zxl.kotlindemo.history.HistoryDetailModel
import com.zxl.kotlindemo.history.HistoryModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 17:39
 * @description:
 */
interface DoubanApiStores {
    companion object {
        val API_SERVER_URL = "https://api.douban.com/"
    }

     @GET("v2/movie/in_theaters")
     fun  getMoviesDatas(): Observable<DatasModel>

    @GET("/v2/movie/subject/{id}")
    fun movieData(@Path("id")id: String): Observable<MovieModel>

}