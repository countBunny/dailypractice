package cn.tianyu.dailypractice.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.widget.TextView
import cn.tianyu.dailypractice.R
import cn.tianyu.dailypractice.utils.LogUtil
import kotlinx.android.synthetic.main.activity_span_prompt.*
import org.jetbrains.anko.ctx

class SpanPromptActivity : AppCompatActivity() {

    companion object {
        val TAG: String = "SpanPromptActivity"
    }

    val bulletSpan = BulletSpan(Color.BLUE)
    val mSpanFactory: Spannable.Factory = object : Spannable.Factory() {
        override fun newSpannable(source: CharSequence?): Spannable {
            if (source == null) {
                return newSpannable("null")
            }
            if (Spannable::class.java.isAssignableFrom(source::class.java)) {
                LogUtil.d(TAG, "spannable reuse, the instance won't be create again!")
                return source as Spannable
            }
            return super.newSpannable(source)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_span_prompt)
        var i = 0
        tvSpan1.apply {
            setSpannableFactory(mSpanFactory)
            val spannableString = SpannableString("only change spannable string, can use bufferType, that's more effective!")
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#90aaee")),
                    0, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            setText(spannableString, TextView.BufferType.SPANNABLE)
            setOnClickListener {
                when (i++) {
                    0 -> {
                        LogUtil.d(TAG, "current i is $i")
                        changeSpanColor(this)
                    }
                    1 -> {
                        LogUtil.d(TAG, "current i is $i")
                        eraseSpanInMid(this)
                    }
                    2->{
                        LogUtil.d(TAG, "current i is $i")
                        (text as Spannable).setSpan(bulletSpan, 0, spannableString.length,
                                SpannableString.SPAN_INCLUSIVE_INCLUSIVE)

                        spanColorChange(this, Color.BLUE)
                    }
                    3 -> {
                        LogUtil.d(TAG, "current i is $i")
                        spanColorChange(this)
                    }
                    else -> {
                        i = 0
                    }
                }
            }
            setBackgroundResource(R.drawable.shape_text_border)
            val dp10 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, ctx.resources.displayMetrics).toInt()
            setPadding(dp10, dp10, dp10, dp10)
        }
    }

    private fun spanColorChange(textView: TextView?, color: Int = Color.RED) {
        val colorField = BulletSpan::class.java.getDeclaredField("mColor")
        colorField.isAccessible = true
        colorField.set(bulletSpan, color)
        textView?.invalidate()
        LogUtil.d(TAG, "color changed to $color")
    }

    private fun eraseSpanInMid(textView: TextView?) {
        if (textView == null) {
            return
        }
        val spannable = textView.text as Spannable
        val mid = Math.floor(spannable.length.toDouble() / 2).toInt()
        spannable.setSpan(ForegroundColorSpan(Color.BLACK),
                mid / 2, mid * 3 / 2, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    }

    private fun changeSpanColor(textView: TextView?) {
        if (textView == null) {
            return
        }
        val spannable = textView.text as Spannable
        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#919191")),
                0, spannable.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}
