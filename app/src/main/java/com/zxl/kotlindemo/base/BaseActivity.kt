package com.zxl.kotlindemo.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription
import io.reactivex.internal.subscriptions.SubscriptionHelper
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.SafeSubscriber
import org.reactivestreams.Subscriber
import java.util.ArrayList

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 14:39
 * @description:
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    val isTest = true
    val TAG = this.javaClass.simpleName
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showTestToast(msg: String) {
        if (isTest) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 退出时关闭所有界面
     */
    fun closeApp() {
        for (activity in openActivity) {
            activity.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        openActivity.add(this)
        initView()
        initEvent()

    }

    override fun onDestroy() {
        openActivity.remove(this)
        if (!mCompositeDisposable.isDisposed)
            mCompositeDisposable.dispose()
        super.onDestroy()
    }


    open fun <M> addDisposable(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Consumer<M> { m -> subscriber.onNext(m) },
                                Consumer<Throwable> { t -> subscriber.onError(t) },
                                Action { -> subscriber.onComplete() })
        )
    }


    protected abstract val layoutId: Int

    protected abstract fun initView()

    protected abstract fun initEvent()


    /**
     * View点击
     */
    abstract fun widgetClick(v: View)

    override fun onClick(v: View) {
        if (fastClick())
            widgetClick(v)
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    private fun fastClick(): Boolean {
        var lastClick: Long = 0
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false
        }
        lastClick = System.currentTimeMillis()
        return true
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    @JvmOverloads
    fun startActivity(clz: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                               requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    companion object {
        var openActivity: MutableList<Activity> = ArrayList()
    }
}