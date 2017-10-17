package com.zxl.kotlindemo.douban

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver
import android.widget.Toast
import com.google.gson.Gson
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.DoubanApiClient
import com.zxl.kotlindemo.douban.model.DatasModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_douban.*
import me.leefeng.promptlibrary.PromptDialog

/**
 * @author: smalllei
 * @date: 2017/10/13
 * @time: 上午10:22
 * @description:  豆瓣电影列表
 */
class DoubanActivity : AppCompatActivity() {


    val moviesAdapter: MoviesAdapter = MoviesAdapter(this)
    var promptDialog: PromptDialog? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_douban)

        douban_rv.layoutManager = LinearLayoutManager(this)
        douban_rv.adapter = moviesAdapter


        toolbar.title = "豆瓣电影"
        setSupportActionBar(toolbar)
        //必须在setSupportActionBar（）方法后，否则无效啊
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }
        // supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_48dp)
        // supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        promptDialog = PromptDialog(this)
        // promptDialog!!.showLoading("加载中...")
        val refreshListerner: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener { -> getData() }
        douban_srl.setOnRefreshListener(refreshListerner)
        //在用SwipeRefreshLayout时候，一进入Activity就立刻刷新的话。动画会出现显示不完整的现象，一闪而过。解决方案：
        douban_srl.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                douban_srl.viewTreeObserver.removeOnGlobalLayoutListener(this)
                douban_srl.isRefreshing = true
                refreshListerner.onRefresh()
            }
        })

        getData()

        moviesAdapter.setOnItemClickListener { adapter, view, position ->
            val intent:Intent=Intent(this@DoubanActivity,MovieActivity::class.java)
            intent.putExtra(MovieActivity.ID,moviesAdapter.data[position].id)
            intent.putExtra(MovieActivity.NAME,moviesAdapter.data[position].title)
            startActivity(intent)
        }
    }




    /**
     * 重写菜单部分
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.toolRefresh -> Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show()
            R.id.toolGo -> Snackbar.make(douban_rv, "go", Snackbar.LENGTH_SHORT)
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 获取豆瓣电影最近热播电影数据
     */
    fun getData() {

        DoubanApiClient
                .retrofit()
                .getMoviesDatas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<DatasModel> {
                    override fun onComplete() {
                        douban_srl.isRefreshing=false
                        promptDialog!!.dismissImmediately()
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("onerror", e.toString())
                    }

                    override fun onNext(t: DatasModel?) {
                        val gson: Gson = Gson()
                        Log.e("ONext", gson.toJson(t))

                        moviesAdapter.setNewData(t!!.subjects)

                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                })
    }

}