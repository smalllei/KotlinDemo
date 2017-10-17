package com.zxl.kotlindemo.douban.model

/**
 * @author: smalllei
 * @date: 2017/10/16
 * @time: 下午5:47
 * @description:  影视信息详情
 */
class MovieModel (val rating:RatingModel,
                  val reviews_count: Int,
                  val wish_count: Int,
                  val douban_site: String,
                  val year: String,
                  val images: ImageMoel,
                  val alt: String,
                  val id: String,
                  val mobile_url: String,
                  val title: String,
                  val share_url: String,
                  val schedule_url: String,
                  val countries: List<String>,
                  val genres: List<String>,
                  val collect_count: Int,
                  val casts: List<CastsModel>,
                  val original_title: String,
                  val summary: String,
                  val directors: List<CastsModel>,
                  val comments_count: Int,
                  val ratings_count: Int,
                  val aka: List<String>
                  )
