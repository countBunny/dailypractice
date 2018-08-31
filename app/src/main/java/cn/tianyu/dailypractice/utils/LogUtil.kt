package cn.tianyu.dailypractice.utils

import android.text.TextUtils
import android.util.Log
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException


object LogUtil {

    fun d(tag: String, msg: String) {
        Timber.d("TAG:%s MESSAGE:%s", tag, msg)
    }

    fun e(tag: String, msg: String) {
        Timber.d("TAG:%s MESSAGE:%s", tag, msg)
    }
}

/**
 *  eg: CacheDiaPath = context.getCacheDir().toString()
 */
class FileLoggingTree(val CacheDiaPath: String) : Timber.Tree() {
    companion object {
        val TAG = "FileLoggingTree"
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (TextUtils.isEmpty(CacheDiaPath)) {
            return
        }
        val file = File(CacheDiaPath + "/log.txt")
        Log.v(TAG, "file.path:" + file.getAbsolutePath() + ",message:" + message)
        var writer: FileWriter? = null
        var bufferedWriter: BufferedWriter? = null
        try {
            writer = FileWriter(file)
            bufferedWriter = BufferedWriter(writer)
            bufferedWriter.write(message)
            bufferedWriter.flush()
        } catch (e: IOException) {
            Log.v(TAG, "存储文件失败")
            e.printStackTrace()
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close()
            }
        }
    }
}