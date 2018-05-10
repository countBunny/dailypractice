package cn.tianyu.dailypractice.ui.activity

import android.app.Application
import cn.tianyu.dailypractice.extensions.DelegateExt
import timber.log.Timber

class App : Application() {
    companion object {
        var instance:App by DelegateExt.notNullSingleValue<App>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //LogUtil based on this
        Timber.plant(Timber.DebugTree())
    }
}