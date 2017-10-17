package com.zxl.kotlindemo.douban.model

/**
 * @author: smalllei
 * @date: 2017/10/13
 * @time: 下午5:40
 * @description:  电影信息
 */
class MoviesModel(val rating: RatingModel,
                  val genres: List<String>,
                  val title: String,
                  val casts: List<CastsModel>,
                  val collect_count: Int,
                  val original_title: String,
                  val subtype: String,
                  val directors: List<CastsModel>,
                  val year:String,
                  val images: ImageMoel,
                  val alt: String,
                  val id: String
                  )