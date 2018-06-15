package cn.tianyu.dailypractice.ui.activity

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import cn.tianyu.dailypractice.R
import cn.tianyu.dailypractice.ui.widget.PagerLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_layout.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.dip
import org.jetbrains.anko.textView

class RecyclerLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_layout)
        rvContent.layoutManager = PagerLayoutManager()
        rvContent.adapter = ColorCardAdaper()
    }

    class ColorCardAdaper : RecyclerView.Adapter<ColorCardAdaper.VH>() {

        companion object {
            val COLOR_START:Int = Color.parseColor("#FF996622")
            val COLOR_END:Int = Color.parseColor("#FF6577ee")//0xFF6577ee ！！Long type??
        }

        val evaluator = ArgbEvaluator()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val cardView = parent.context.cardView {
                radius = dip(5).toFloat()
                elevation = dip(5).toFloat()
            }
            val vh = VH(cardView)
            vh.init()
            return vh
        }

        override fun getItemCount(): Int = 20

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.setColor(position)
        }

        inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            lateinit var text:TextView
            fun init() {
                if (ViewGroup::class.java.isAssignableFrom(itemView.javaClass)) {
                    with(itemView as ViewGroup) {
                        text = textView("color card, color unknown") {
                            gravity = Gravity.CENTER
                            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                        }
                    }
                }
            }

            fun setColor(position: Int) {
                val itemCount = getItemCount()
                val fraction = position.toFloat() / itemCount
                itemView.apply {
                    val curColor = evaluator.evaluate(fraction, COLOR_START, COLOR_END) as Int
                    text.backgroundColor = curColor
                    text.text = "color card, color is $curColor"
                }
            }

        }
    }
}
