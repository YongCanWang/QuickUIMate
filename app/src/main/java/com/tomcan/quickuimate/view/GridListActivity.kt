package com.tomcan.quickuimate.view

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tomcan.frame.v.BaseActivity
import com.tomcan.quickuimate.adap.GridListAdapter
import com.tomcan.quickuimate.databinding.ActivityGridListBinding
import com.tomcan.quickuimate.viewmodel.WaterfallViewModel

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/10 10:37
 */
class GridListActivity : BaseActivity<ActivityGridListBinding, WaterfallViewModel>() {

    override fun getLayout() = ActivityGridListBinding.inflate(layoutInflater)

    override fun onStarted() {
        addGridLayoutManager(binding.rvList)
        binding.rvList.adapter = GridListAdapter(binding.rvList, 12, 12).apply {
            addData(viewModel.getSkinData())
            setOnItemClickListener { view, position ->
                Toast.makeText(this@GridListActivity, "" + position, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onReStart() {

    }

    /**
     * 设置列表布局
     */
    private fun addGridLayoutManager(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager =
                GridLayoutManager(this@GridListActivity, 2)
        }
    }
}