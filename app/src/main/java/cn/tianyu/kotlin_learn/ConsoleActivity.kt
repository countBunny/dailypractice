package cn.tianyu.kotlin_learn

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import cn.tianyu.dailypractice.R
import cn.tianyu.dailypractice.utils.LogUtil
import cn.tianyu.kotlin_learn.section2.*
import cn.tianyu.kotlin_learn.section3.InvokeFromJava
import cn.tianyu.kotlin_learn.section3.joinToString
import cn.tianyu.kotlin_learn.section3.parsePathInRegex
import kotlinx.android.synthetic.main.activity_console.*

class ConsoleActivity : AppCompatActivity() {

    companion object {
        val TAG = "ConsoleActivity"
        val REQUEST_CODE_PERMISSION = 101;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        test();
    }

    private fun test() {
        console1.setText("${getMnemonic(Color.BLUE)} \n1 + 2 + 4 = ${eval(Sum(Sum(Num(1), Num(2)), Num(4)))}")

        for (i in 1..100) {
            LogUtil.d(TAG, fizzBuzz(i))
        }
        val list = arrayListOf("10", "11", "1001")
        for ((index, element) in list.withIndex()) {
            LogUtil.d(TAG, "$index: $element")
        }
        val list2 = listOf(1, 2, 3)
        console2.setText("${console2.text} \n${joinToString(list2, ";", "(", ")")}")
        InvokeFromJava.invokeKotlinMethod()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION)
        } else {
            readExternalFileInfo()
        }
        LogUtil.d(TAG, """
            | //
            |//
            |/ \
        """)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            for ((index, value) in permissions.withIndex()) {
                if (value == Manifest.permission.READ_EXTERNAL_STORAGE && grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    readExternalFileInfo()
                }
            }
        }
    }

    private fun readExternalFileInfo() {
        val externalFilesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        LogUtil.d(TAG, "externalStoragePath is ${externalFilesDir.absolutePath}")
        val listFiles = externalFilesDir.listFiles()
        if (listFiles != null && !listFiles.isEmpty()) {
            val firstFile = listFiles.asSequence().filter { it.isFile }.first()
            if (firstFile != null) {
                parsePathInRegex(firstFile.absolutePath)
            } else {
                LogUtil.d(TAG, "No file found!")
            }
        } else {
            LogUtil.d(TAG, "list file result is NULL!")
        }
    }
}
