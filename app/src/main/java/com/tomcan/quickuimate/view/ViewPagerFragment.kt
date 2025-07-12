package com.tomcan.quickuimate.view

import android.widget.Toast
import com.tomcan.frame.v.BaseViewPageFragment
import com.tomcan.quickuimate.databinding.FragmentViewpagerBinding
import com.tomcan.quickuimate.viewmodel.ViewPagerFragmentVM

/**
 * @author LiteMob
 * @description:
 * @date: 2025/7/12 22:03
 */
class ViewPagerFragment : BaseViewPageFragment<FragmentViewpagerBinding, ViewPagerFragmentVM>() {

    override fun getLayout() = FragmentViewpagerBinding.inflate(layoutInflater)

    override fun onStarted() {
        super.onStarted()
        Toast.makeText(requireContext(), viewModel.getText(), Toast.LENGTH_SHORT).show()
    }
}