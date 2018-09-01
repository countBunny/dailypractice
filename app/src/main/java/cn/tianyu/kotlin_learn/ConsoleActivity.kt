package cn.tianyu.kotlin_learn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.tianyu.dailypractice.R
import cn.tianyu.dailypractice.utils.LogUtil
import cn.tianyu.kotlin_learn.section2.*
import cn.tianyu.kotlin_learn.section3.InvokeFromJava
import cn.tianyu.kotlin_learn.section3.joinToString
import kotlinx.android.synthetic.main.activity_console.*

class ConsoleActivity : AppCompatActivity() {

    companion object {
        val TAG = "ConsoleActivity"
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
        for ((index, element) in list.withIndex()){
            LogUtil.d(TAG, "$index: $element")
        }
        val list2 = listOf(1,2,3)
        console2.setText("${console2.text} \n${joinToString(list2, ";", "(", ")")}")
        InvokeFromJava.invokeKotlinMethod()
    }
}
