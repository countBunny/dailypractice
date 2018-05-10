package cn.tianyu.dailypractice.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

class MyWebView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : WebView(context, attrs, defStyleAttr, defStyleRes) {
    override fun onFinishInflate() {
        super.onFinishInflate()
        defaultSettings();
    }

    private fun defaultSettings() {
        setBackgroundColor(Color.WHITE)
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.setSupportZoom(false)
        settings.useWideViewPort = true
        settings.setSupportMultipleWindows(true)
        settings.setGeolocationEnabled(false)
        settings.setAppCacheEnabled(true)
    }
}