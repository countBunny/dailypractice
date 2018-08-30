package cn.tianyu.kotlin_learn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.tianyu.dailypractice.R
import cn.tianyu.kotlin_learn.section2.Color
import kotlinx.android.synthetic.main.activity_console.*

class ConsoleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        test();
    }

    private fun test() {
        console1.setText(Color.BLUE.rgb().toString())
    }
}
