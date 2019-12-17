package com.tongchen.twatcher.base.http

/**
 * @author TongChen
 * @date 2019/12/17  14:06
 * <p>
 * Desc: 用于标志解析数据类型的注解
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResponseFormat(val value: String = "") {
    companion object {

        const val JSON = "json"

        const val STRING = "string"

        const val XML = "xml"
    }
}