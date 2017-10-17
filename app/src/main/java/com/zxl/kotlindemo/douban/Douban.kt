package com.zxl.kotlindemo.douban

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.DoubanApiClient
import com.zxl.kotlindemo.douban.model.DatasModel

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author: smalllei
 * *
 * @date: 2017/10/16
 * *
 * @time: 上午10:32
 * *
 * @description:
 */
class Douban : AppCompatActivity() {

    private var srl: SwipeRefreshLayout? = null
    private var rv: RecyclerView? = null
    private var adapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_douban)
        srl = findViewById<SwipeRefreshLayout>(R.id.douban_srl)
        rv = findViewById<RecyclerView>(R.id.douban_rv)
        rv!!.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter(this)
        rv!!.adapter = adapter
        getData()

    }

    private fun getData() {
        DoubanApiClient.retrofit()
                .getMoviesDatas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<DatasModel> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(datasModel: DatasModel) {
                        adapter!!.setNewData(datasModel.subjects)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }
}
