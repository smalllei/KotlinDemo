package com.zxl.kotlindemo.douban.model

/**
 * @author: smalllei
 * @date: 2017/10/13
 * @time: 下午6:17
 * @description:
 */
class DatasModel (val count: Int,
                  val start: Int,
                  val total: Int,
                  val subjects: List<MoviesModel>,
                  val title: String)