package cn.tianyu.dailypractice.base.network

import android.webkit.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class SaveCookieManager : CookieJar {

    override fun saveFromResponse(url: HttpUrl?, cookies: MutableList<Cookie>?) {
        if (null == url || cookies == null) {
            return
        }
        saveCookie(cookies, url.host())
    }

    override fun loadForRequest(url: HttpUrl?): MutableList<Cookie> {
        return MutableList<Cookie>(0){
            Cookie.Builder().domain("client-android").build()
        }
    }

    /**
     * 获取cookie
     * @param cookies
     * @param url
     */
    private fun saveCookie(cookies: Collection<Cookie>, url: String) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true);

        cookies.map {
            cookieManager.setCookie(url, it.toString());
        }
        cookieManager.flush()
    }
}