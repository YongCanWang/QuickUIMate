package com.tomcan.quickuimate.view

import android.app.Activity
import android.content.Intent
import com.tomcan.frame.v.BaseActivity
import com.tomcan.quickui.adapter.ViewPagerFragmentAdapter
import com.tomcan.quickuimate.databinding.ActivityViewpageBinding
import com.tomcan.quickuimate.viewmodel.ViewPagerViewModel

/**
 * @author LiteMob
 * @description:
 * @date: 2025/7/12 21:58
 */
class ViewPagerActivity : BaseActivity<ActivityViewpageBinding, ViewPagerViewModel>() {

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, ViewPagerActivity::class.java))
        }
    }


    override fun getLayout() = ActivityViewpageBinding.inflate(layoutInflater)

    override fun onStarted() {
        super.onStarted()
        initViewPage()
    }

    private fun initViewPage() {
        binding.viewPager.adapter =
            ViewPagerFragmentAdapter(
                supportFragmentManager,
                lifecycle,
                ArrayList<ViewPagerFragment>().apply {
                    add(
                        ViewPagerFragment()
                    )
                })
    }
}