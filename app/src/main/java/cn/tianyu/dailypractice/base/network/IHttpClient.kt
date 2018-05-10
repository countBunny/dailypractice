package cn.tianyu.dailypractice.base.network

import cn.tianyu.dailypractice.extensions.toVarargArray
import cn.tianyu.dailypractice.utils.JsonUtils
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpServer {
    val client: IHttpClient = OkClient()

    inline fun <reified Result : Any> get(url: String, params: MutableMap<String, String> = HashMap(0), listener: HttpListener<Result>?,
                                          async: Boolean = false) {
        client.get(url, *params.toVarargArray(), listener = listener, result = Result::class.java, async = async)
    }

    fun post() {
        client.post()
    }
}

interface IHttpClient {

    fun <Result : Any> get(url: String, vararg args: Pair<String, String>?,
                           listener: HttpListener<Result>?, result: Class<Result>, async: Boolean) {
    }

    fun post() {}
}

class OkClient() : IHttpClient {
    val mClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(HttpLoggingInterceptor())
                .addNetworkInterceptor(HttpSaveCookieInterceptor())
                .cookieJar(SaveCookieManager())
                .build()
    }

    override fun <Result : Any> get(url: String, vararg args: Pair<String, String>?,
                                    listener: HttpListener<Result>?, result: Class<Result>, async: Boolean) {
        var urlCopy = url
        if (null != args && args.isNotEmpty()) {
            val builder = StringBuilder(url).append("?")
            args.forEach {
                if (it == null) {
                    return
                }
                builder.append(it.first).append("=").append(it.second).append("&")
            }
            builder.deleteCharAt(builder.length - 1)
            urlCopy = builder.toString()
        }
        val request = Request.Builder()
                .url(urlCopy)
                .get()
                .build()
        settleRequest(mClient.newCall(request), listener, result, async)
    }

    private fun <Result : Any> settleRequest(call: Call, listener: HttpListener<Result>?, result: Class<Result>, async: Boolean) {
        if (!async) {
            //sync dispose
            val response = call.execute()
            if (response.code() == 200 && null != listener) {
                whenSuccess(response, listener, result)
            } else {
                listener?.onFailed("response code = " + response.code() + " response is " + response.toString()
                        + "response message is " + response.message())
            }
            return
        }
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                listener?.onFailed(call?.request().toString() + e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (null == response) {
                    onFailure(call, IOException("response is null"))
                    return
                }
                if (response.code() == 200 && null != listener) {
                    whenSuccess(response, listener, result)
                } else {
                    onFailure(call, IOException("response code abnormal!"))
                }
            }
        })
    }

    private fun <Result : Any> whenSuccess(resp: Response, listener: HttpListener<Result>, result: Class<Result>) {
        with(listener){
            when (result.newInstance()) {
                is String -> {
                    onSuccess(resp.body().toString() as Result)
                }
                is JSONObject -> {
                    onSuccess(JSONObject(resp.body().toString()) as Result)
                }
                else -> {
                    onSuccess(JsonUtils.parseObj(resp.body().toString(), result))
                }
            }
        }
    }
}