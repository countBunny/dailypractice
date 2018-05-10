package cn.tianyu.dailypractice.utils

import timber.log.Timber

object LogUtil {

    fun d(tag: String, msg: String) {
        Timber.d("TAG:%s MESSAGE:%s", tag, msg)
    }

    fun e(tag: String, msg: String) {
        Timber.d("TAG:%s MESSAGE:%s", tag, msg)
    }
}