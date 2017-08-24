package com.zxl.kotlindemo.base

import org.reactivestreams.Subscriber
import retrofit2.HttpException

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 17:54
 * @description:
 */
abstract class ApiCallback<T> : Subscriber<T> {
    abstract fun onSuccess(model: T)
    abstract fun onFailure(msg: String?)
    abstract fun onFinish()

    override fun onComplete() {
        onFinish()
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(t: Throwable?) {
        if (t is HttpException) {
            val code = t.code()
            var msg = t.message()
            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试"
            }
            onFailure(msg)
        } else {
            onFailure(t.toString())
        }
        onFinish()
    }
}