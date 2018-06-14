package cn.tianyu.dailypractice.ui.activity.base

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import cn.tianyu.dailypractice.interfaces.OnFragmentInteractionListener
import cn.tianyu.dailypractice.utils.LogUtil

open class FragmentInteractActivity : AppCompatActivity(), OnFragmentInteractionListener {

    companion object {
        val TAG = "FragmentInteractActivity"
    }

    override fun onFragmentInteraction(uri: Uri) {
        LogUtil.d(TAG, "received fragment interact uri = ${uri.toString()}")
    }

}