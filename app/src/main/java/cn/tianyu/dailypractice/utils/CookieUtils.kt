package cn.tianyu.dailypractice.utils

import android.webkit.CookieManager

class CookieUtils {

    companion object {
        fun setAcceptThirdPartCookies(allowed: Boolean){
            CookieManager.getInstance().setAcceptCookie(allowed)
        }
    }
}