package com.zxl.kotlindemo.base

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 17:24
 * @description:
 */
class BaseModel<T> (val error: Int,
                 val reason: String,
                 val result: T)