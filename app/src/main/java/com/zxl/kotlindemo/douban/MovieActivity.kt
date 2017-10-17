package com.zxl.kotlindemo.douban

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.DoubanApiClient
import com.zxl.kotlindemo.douban.model.CastsModel
import com.zxl.kotlindemo.douban.model.MovieModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.item_movie_image.view.*
import me.leefeng.promptlibrary.PromptDialog


/**
 * @author: smalllei
 * @date: 2017/10/16
 * @time: 下午6:22
 * @description:  电影详情
 */
class MovieActivity : AppCompatActivity() {

    companion object {
        val ID: String = "id"
        val NAME:String="name"
        val TAG: String = this.javaClass.name
    }

    val images: ArrayList<CastsModel> = ArrayList()
    val moviesAdapter: MovieImageAdapter = MovieImageAdapter(images, this)
    var waitDialog: PromptDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        movie_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movie_rv.adapter = moviesAdapter
        waitDialog = PromptDialog(this)
        waitDialog!!.showLoading("加载中...")
        movieData()
        movie_toolbar.title=intent.getStringExtra(NAME)
        setSupportActionBar(movie_toolbar)
        movie_toolbar.setNavigationOnClickListener { view -> finish() }

    }

    fun movieData() {
        DoubanApiClient.retrofit()
                .movieData(intent.getStringExtra(ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MovieModel> {
                    override fun onComplete() {
                        waitDialog!!.dismissImmediately()
                    }

                    override fun onNext(t: MovieModel?) {
                        Glide.with(this@MovieActivity).load(t!!.images.large).into(movie_iv)
                        movie_name.text=t.original_title
                        movie_summary.text=t.summary
                        val sb: StringBuffer= StringBuffer()
                        sb.append(t.year+"/")
                        for (s in t.genres){
                            sb.append(s+"/")
                        }
                        var ss:String=sb.toString()
                        movie_type.text=ss.substring(0,ss.length-1)

                        images.addAll(t.casts)
                        images.addAll(t.directors)
                        moviesAdapter.notifyDataSetChanged()


                    }

                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, e.toString())
                    }

                })


    }


    class MovieImageAdapter(val images: List<CastsModel>, val context: Context) : RecyclerView.Adapter<MovieImageAdapter.MovieImageViewHolder>() {

        override fun onBindViewHolder(holder: MovieImageViewHolder?, position: Int) {

            Glide.with(context)
                    .load(images[position].avatars.medium)
                    .into(holder!!.itemView.item_movie_iv)

        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieImageViewHolder {
            return MovieImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie_image, parent, false))
        }

        override fun getItemCount(): Int {
            return images.size
        }

        class MovieImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    }
}