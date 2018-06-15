package cn.tianyu.dailypractice.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.tianyu.dailypractice.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main_menu.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = MainMenuAdapter()
        val list = resources.getStringArray(R.array.main_menu).asList()
        adapter.mData = list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }
}

class MainMenuAdapter : RecyclerView.Adapter<MainMenuAdapter.VH>() {

    var mData: List<String> = ArrayList()
        get() = field

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(LayoutInflater.from(parent.context).inflate(R.layout.item_main_menu, parent, false))

    override fun getItemCount(): Int = mData?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setupDatas(mData[position])
    }


    class VH(private val view: View) : RecyclerView.ViewHolder(view) {

        fun setupDatas(s: String) {
            with(s){
                view.tvTitle.text = s
                view.setOnClickListener {
                    when(layoutPosition){
                        0 -> {
                            itemView.context.toast("position 0 been clicked")
//                            itemView.context.startActivity<>()
                        }
                        1 -> {
                            itemView.context.startActivity<LayoutParamTestActivity>()
                        }
                        2->{
                            itemView.context.startActivity<SpanPromptActivity>()
                        }
                        3->{
                            itemView.context.startActivity<NavigationActivity>()
                        }
                        4->{
                            itemView.context.startActivity<RecyclerLayoutActivity>()
                        }
                        else ->{
                            Toast.makeText(itemView.context, "undefined ops!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

}
