package cn.tianyu.dailypractice.ui.activity

import android.os.Bundle
import androidx.navigation.findNavController
import cn.tianyu.dailypractice.R
import cn.tianyu.dailypractice.ui.activity.base.FragmentInteractActivity

class NavigationActivity : FragmentInteractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.fragment_nav_test_host).navigateUp()

}
