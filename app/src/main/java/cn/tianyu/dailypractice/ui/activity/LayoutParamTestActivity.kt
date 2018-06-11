package cn.tianyu.dailypractice.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.ViewGroup
import cn.tianyu.dailypractice.R
import kotlinx.android.synthetic.main.activity_layout_param_test.*
import org.jetbrains.anko.ctx

class LayoutParamTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_param_test)
        test1.setOnClickListener {
            val params = child.layoutParams as ViewGroup.MarginLayoutParams
            if (params.topMargin > 0) {
                params.topMargin = 0
            } else {
                params.topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f,
                        ctx.resources.displayMetrics).toInt()
            }
            child.requestLayout()
        }
    }
}
