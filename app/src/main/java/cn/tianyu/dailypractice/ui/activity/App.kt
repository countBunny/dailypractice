package cn.tianyu.dailypractice.ui.activity

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager.GET_ACTIVITIES
import android.content.pm.PackageManager.GET_SERVICES
import android.os.Process
import android.util.Log
import cn.tianyu.dailypractice.BuildConfig
import cn.tianyu.dailypractice.extensions.DelegateExt
import cn.tianyu.dailypractice.utils.FileLoggingTree
import timber.log.Timber


class App : Application() {
    companion object {
        var instance: App by DelegateExt.notNullSingleValue<App>()
        val TAG: String = "App"
    }

    override fun onCreate() {
        super.onCreate()
        val processName = packageManager.getPackageInfo(packageName, GET_ACTIVITIES or GET_SERVICES).applicationInfo.processName
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = activityManager.runningAppProcesses.filter {
            it.pid == Process.myPid()
        }
        if (!list.isEmpty()) {
            if (list[0].processName == processName) {
                Log.d(TAG, "init once or more! processName is" + processName)
                instance = this
                //LogUtil based on this
                if (BuildConfig.DEBUG) {
                    Timber.plant(Timber.DebugTree())
                } else {
                    Timber.plant(FileLoggingTree(cacheDir.toString()))
                }
            }
        }
    }
}