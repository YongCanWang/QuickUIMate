package com.tomcan.quickuimate.view

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tomcan.frame.v.BaseActivity
import com.tomcan.quickuimate.R
import com.tomcan.quickuimate.adap.WebpAdapter
import com.tomcan.quickuimate.databinding.ActivityWebpBinding
import com.tomcan.quickuimate.viewmodel.WebpViewModel

/**
 * @author Tom灿
 * @description: 瀑布流布局
 * @date: 2024/11/10 10:37
 */
class WebpActivity : BaseActivity<ActivityWebpBinding, WebpViewModel>() {

    override fun layout() = R.layout.activity_webp

    override fun onStarted() {
        addStaggeredGridLayoutManager(binding.rvList)
        binding.rvList.adapter = WebpAdapter(binding.rvList, 12, 12).apply {
            addData(viewModel.getData())
            setOnItemClickListener { view, position ->
                Toast.makeText(this@WebpActivity, "" + position, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onReStart() {

    }

    /**
     * 设置列表布局
     */
    private fun addStaggeredGridLayoutManager(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }
}