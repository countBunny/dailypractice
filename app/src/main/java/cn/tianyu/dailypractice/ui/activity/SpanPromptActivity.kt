package cn.tianyu.dailypractice.ui.activity

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
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

    val bulletSpan = BulletPointSpan(40, Color.BLUE, 20)
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
                    else -> {
                        i = 0
                    }
                }
            }
            setBackgroundResource(R.drawable.shape_text_border)
            val dp10 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, ctx.resources.displayMetrics).toInt()
            setPadding(dp10, dp10, dp10, dp10)
        }
        tvSpan2.apply {
            val spannableString = SpannableString("only change spannable property, \ninvoke text invalidate to update it, that's more effective!")
            spannableString.setSpan(bulletSpan, 33, spannableString.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = spannableString
            setOnClickListener {
                when (i++) {
                    0 -> {
                        LogUtil.d(TAG, "current i is $i")
                        spanColorChange(this)
                    }
                    1 -> {
                        LogUtil.d(TAG, "current i is $i")
                        spanColorChange(this, Color.BLUE)
                    }
                    else -> {
                        i = 0
                        spanColorChange(this, Color.BLACK)
                    }
                }
            }
            setBackgroundResource(R.drawable.shape_text_border)
            val dp10 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, ctx.resources.displayMetrics).toInt()
            setPadding(dp10, dp10, dp10, dp10)
        }
    }

    private fun spanColorChange(textView: TextView?, color: Int = Color.RED) {
        bulletSpan.color = color
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

    class BulletPointSpan(gapWidth: Int, var color: Int, val bulletRadius: Int) : BulletSpan(gapWidth, color) {
        override fun getLeadingMargin(first: Boolean): Int {
            val gapField = BulletSpan::class.java.getDeclaredField("mGapWidth")
            gapField.isAccessible = true
            val gapWidth = gapField.getInt(this)
            LogUtil.d(TAG, "current gapWidth is $gapWidth")
            return gapWidth + bulletRadius * 2
        }

        override fun drawLeadingMargin(c: Canvas?, p: Paint?, x: Int, dir: Int, top: Int, baseline: Int, bottom: Int, text: CharSequence?, start: Int, end: Int, first: Boolean, l: Layout?) {
            if (p == null || c == null || text == null) {
                return
            }
            if ((text as Spanned).getSpanStart(this) == start) {
                val style = p.style
                val oldcolor = p.color
                p.color = color
                p.style = Paint.Style.FILL

                if (c.isHardwareAccelerated()) {
                    val bulletPath = BulletSpan::class.java.getDeclaredField("sBulletPath")
                    bulletPath.isAccessible = true
                    if (bulletPath.get(this) == null) {
                        val path = Path()
                        bulletPath.set(this, path)
                        path.addCircle(0.0f, 0.0f, bulletRadius.toFloat(), Path.Direction.CW)
                    }

                    c.save()
                    c.translate((x + dir * bulletRadius).toFloat(), (top + bottom) / 2.0f)
                    c.drawPath(bulletPath.get(this) as Path, p)
                    c.restore()
                } else {
                    c.drawCircle((x + dir * bulletRadius).toFloat(), (top + bottom) / 2.0f, bulletRadius.toFloat(), p)
                }
                p.color = oldcolor
                p.style = style
            }
        }
    }
}
