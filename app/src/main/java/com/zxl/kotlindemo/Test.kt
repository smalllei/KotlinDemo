package com.zxl.kotlindemo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView

import com.zxl.kotlindemo.base.DoubanApiClient
import com.zxl.kotlindemo.douban.model.DatasModel

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author: zhaoxiaolei
 * *
 * @date: 2017/8/24
 * *
 * @time: 15:49
 * *
 * @description:
 */

class Test : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val points = IntArray(2)
        for (i in 0..9) {
        }
        val view = GridView(this)
        view.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> }

    }

    fun data() {
        DoubanApiClient.retrofit().getMoviesDatas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<DatasModel> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(datasModel: DatasModel) {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }


}
