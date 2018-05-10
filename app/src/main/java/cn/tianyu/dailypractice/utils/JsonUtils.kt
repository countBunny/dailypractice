package cn.tianyu.dailypractice.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtils {

    val gson: Gson by lazy {
        Gson()
    }

    inline fun <reified T> parseObj(jsonStr: String): T = gson.fromJson(jsonStr, T::class.java)

    fun <T> parseObj(jsonStr: String, clz: Class<T>): T = gson.fromJson(jsonStr, clz)

    inline fun <reified T> parseList(jsonStr: String): List<T> {
        val typeToken: TypeToken<List<T>> = object : TypeToken<List<T>>() {}
        return gson.fromJson(jsonStr, typeToken.type)
    }
}