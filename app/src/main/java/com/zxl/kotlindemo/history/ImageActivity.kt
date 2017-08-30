package com.zxl.kotlindemo.history


import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zxl.kotlindemo.R
import com.zxl.kotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.item_image.view.*
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/25
 * @time: 16:19
 * @description:
 */
class ImageActivity : Activity(), ViewTreeObserver.OnPreDrawListener {

    var datas:ArrayList<HistoryDetailModel.HistoryDetailBean.PicUrlBean>? =null
    var index = 0

    companion object {
        val IMAGE_URL = "image_url"
        val IMAGE_INDEX = "image_index"
    }


    var adapter :VpAdapter?= null
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var imageHeight = 0
    var imageWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        initView()
    }


     fun initView() {
        datas=intent.getSerializableExtra(IMAGE_URL) as ArrayList<HistoryDetailModel.HistoryDetailBean.PicUrlBean>
        index = intent.getIntExtra(IMAGE_INDEX, 0)
        //获取屏幕宽高
        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metric)
        screenHeight = metric.heightPixels
        screenWidth = metric.widthPixels
         adapter= VpAdapter(this, datas!!)
        image_vp.adapter = adapter
        image_vp.currentItem = index
        image_vp.viewTreeObserver.addOnPreDrawListener(this)
        image_vp.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                index = position
            }
        })
    }



    override fun onBackPressed() {
        finishActivityAnim()
    }

    /** 绘制前开始动画 */
    override fun onPreDraw(): Boolean {
        image_rootView.viewTreeObserver.removeOnPreDrawListener(this)

        val view = adapter!!.currentView
        val imageView = adapter!!.primaryImageView
       Log.e(imageView.id.toString(), view!!.id.toString())
        computeImageWidthAndHeight(imageView)
        val imageData = datas!![index]
        val vx: Float = imageData.imageViewWidth * 1.0f / imageWidth
        val vy: Float = imageData.imageViewHeight * 1.0f / imageHeight
        val valueAnimator = ValueAnimator.ofFloat(0f, 1.0f)
        valueAnimator.addUpdateListener { valueAnimator ->
            val duration = valueAnimator.duration
            val playTime = valueAnimator.currentPlayTime
            var fraction = if (duration > 0) playTime.toFloat() /duration else 1f
            if (fraction > 1) fraction = 1f
            view!!.translationX = evaluateInt(fraction, imageData.imageViewX + imageData.imageViewWidth / 2 - imageView.width / 2, 0)!!.toFloat()
            view.translationY = evaluateInt(fraction, imageData.imageViewY + imageData.imageViewHeight / 2 - imageView.height / 2, 0)!!.toFloat()
            view.scaleX = evaluateFloat(fraction, vx, 1)!!
            view.scaleY = evaluateFloat(fraction, vy, 1)!!
            view.alpha = fraction
            image_rootView.setBackgroundColor(evaluateArgb(fraction, Color.TRANSPARENT, Color.BLACK));

        }
        //进场动画监听
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(p0: Animator?) {
                image_rootView.setBackgroundColor(Color.BLACK)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
                image_rootView.setBackgroundColor(0x0)
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })
        valueAnimator.duration = 1000.toLong()
        valueAnimator.start()
        return true
    }

    fun finishActivityAnim() {
        val view = adapter!!.currentView
        val imageView = adapter!!.primaryImageView
        computeImageWidthAndHeight(imageView)
        val imageData = datas!![index]
        val vx: Float = imageData.imageViewWidth * 1.0f / imageWidth
        val vy: Float = imageData.imageViewHeight * 1.0f / imageHeight
        val valueAnimator = ValueAnimator.ofFloat(0f, 1.0f)
        valueAnimator.addUpdateListener { valueAnimator ->
            val duration = valueAnimator.duration
            val playTime = valueAnimator.currentPlayTime
            var fraction = if (duration > 0) playTime.toFloat()/duration else 1f
            if (fraction > 1) fraction = 1f
            view!!.translationX = evaluateInt(fraction, 0, imageData.imageViewX + imageData.imageViewWidth / 2 - imageView.width / 2)!!.toFloat()
            view.translationY = evaluateInt(fraction, 0, imageData.imageViewY + imageData.imageViewHeight / 2 - imageView.height / 2)!!.toFloat()
            view.scaleX = evaluateFloat(fraction, 1, vx)!!
            view.scaleY = evaluateFloat(fraction, 1, vy)!!
            view.alpha = 1 - fraction
            image_rootView.setBackgroundColor(evaluateArgb(fraction, Color.BLACK, Color.TRANSPARENT))
        }
        //进场动画监听
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(p0: Animator?) {
                finish()
                overridePendingTransition(0, 0);
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
                image_rootView.setBackgroundColor(0x0)
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })
        valueAnimator.duration = 1000.toLong()
        valueAnimator.start()
    }

    /** Integer 估值器  */
    fun evaluateInt(fraction: Float, startValue: Int?, endValue: Int?): Int? {
        val startInt = startValue!!
        return (startInt + fraction * (endValue!! - startInt)).toInt()
    }

    /** Float 估值器  */
    fun evaluateFloat(fraction: Float, startValue: Number, endValue: Number): Float? {
        val startFloat = startValue.toFloat()
        return startFloat + fraction * (endValue.toFloat() - startFloat)
    }

    /** Argb 估值器  */
    fun evaluateArgb(fraction: Float, startValue: Int, endValue: Int): Int {
        val startA = startValue shr 24 and 0xff
        val startR = startValue shr 16 and 0xff
        val startG = startValue shr 8 and 0xff
        val startB = startValue and 0xff

        val endA = endValue shr 24 and 0xff
        val endR = endValue shr 16 and 0xff
        val endG = endValue shr 8 and 0xff
        val endB = endValue and 0xff

        return startA + (fraction * (endA - startA)).toInt() shl 24 or (startR + (fraction * (endR - startR)).toInt() shl 16//
                ) or (startG + (fraction * (endG - startG)).toInt() shl 8//
                ) or startB + (fraction * (endB - startB)).toInt()
    }

    /**
     * 计算图片宽高
     */
    fun computeImageWidthAndHeight(imageView: ImageView) {
        val drawable = imageView.drawable
        //真实高度
        val intrinsicHeight = drawable.intrinsicHeight

        //真实宽度
        val intrinsicWidth = drawable.intrinsicWidth
        Log.e("computeImage","${intrinsicHeight}${intrinsicWidth}")
        // 计算出与屏幕的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满，就是宽度没充满
        var h = screenHeight * 1.0f / intrinsicHeight
        var w = screenWidth * 1.0f / intrinsicWidth
        if (h > w) h = w
        else w = h
        imageWidth = intrinsicWidth * w.toInt()
        imageHeight = intrinsicHeight * h.toInt()

    }

    /**viewpager 适配器*/
    class VpAdapter(val context: Context, val datas: ArrayList<HistoryDetailModel.HistoryDetailBean.PicUrlBean>) : PagerAdapter() {
        var currentView : View? = null
            private set
        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return datas.size
        }

        override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
            super.setPrimaryItem(container, position, `object`)
            currentView = `object` as View
        }


        val primaryImageView: ImageView
            get() =  currentView!!.item_image_pv as ImageView


        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val view = LayoutInflater.from(context).inflate(R.layout.item_image, container, false)


            view.item_image_pv.onPhotoTapListener = PhotoViewAttacher.OnPhotoTapListener { view: View?, x: Float, y: Float -> (context as ImageActivity).finishActivityAnim() }
            view.item_image_pv.setImageResource(R.drawable.ic_default_color);
            Glide.with(context)
                    .load(datas[position].url)
                    .crossFade()
                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略
                    .into(view.item_image_pv)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            if (container != null) {
                container.removeView(`object` as View)
            }
        }


    }
}