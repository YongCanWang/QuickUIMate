package com.tomcan.quickui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tomcan.frame.v.QuickViewPageFragment

/**
 * @author Tom灿
 * @description:
 * @date :2024/5/3 18:25
 */
class ViewPagerFragmentAdapter : FragmentStateAdapter {
    private var mFragments: List<QuickViewPageFragment<*, *>>

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: List<QuickViewPageFragment<*, *>>
    ) : super(fragmentManager, lifecycle) {
        this.mFragments = fragments
    }

    override fun getItemCount() = mFragments.size

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}