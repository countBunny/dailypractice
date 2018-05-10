package cn.tianyu.dailypractice.base.network

import cn.tianyu.dailypractice.utils.LogUtil

interface HttpListener<Result : Any> {

    companion object {
        val TAG: String = "HttpListener"
    }

    fun onSuccess(result: Result)

    fun onFailed(errorMsg: String) {
        LogUtil.e(TAG, errorMsg)
    }
}