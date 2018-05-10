package cn.tianyu.dailypractice.base.network

import cn.tianyu.dailypractice.utils.LogUtil
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.Response

class HttpLoggingInterceptor : Interceptor {

    companion object {
        val TAG: String = "HttpLoggingInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        if (null == chain) {
            LogUtil.e(TAG, "this chain is null")
            return Response.Builder().code(-1)
                    .message("this chain is null")
                    .build()
        }
        val response = chain.proceed(chain.request())
        LogUtil.d(TAG, "response from server = " + response.toString()
                + "\n response body = " + response.body())
        return response
    }

}

class HttpSaveCookieInterceptor : Interceptor {

    companion object {
        val TAG: String = "HttpSaveCookieInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        if (chain == null) {
            LogUtil.e(HttpLoggingInterceptor.TAG, "this chain is null")
            return Response.Builder().code(-1)
                    .message("this chain is null")
                    .build()
        }
        return chain.let {
            val response = it.proceed(it.request())
            val cookies = Cookie.parseAll(response.request().url(), response.headers())
//            SaveCookieManager()
            if (cookies != null && !cookies.isEmpty()) {
                LogUtil.d(TAG, "cookies = " + cookies.toString())
            }
            response
        }
    }
}