package com.tomcan.quickuimate.view

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tomcan.frame.v.BaseActivity
import com.tomcan.quickuimate.adap.WaterfallAdapter
import com.tomcan.quickuimate.databinding.ActivityWaterfallBinding
import com.tomcan.quickuimate.viewmodel.WaterfallViewModel

/**
 * @author Tom灿
 * @description: 瀑布流布局
 * @date: 2024/11/10 10:37
 */
class WaterfallActivity : BaseActivity<ActivityWaterfallBinding, WaterfallViewModel>() {

    override fun getLayout(): ActivityWaterfallBinding =
        ActivityWaterfallBinding.inflate(layoutInflater)

    override fun onStarted() {
        addGridLayoutManager(binding.rvList)
        binding.rvList.adapter = WaterfallAdapter(binding.rvList).apply {
            addData(viewModel.getData())
            setOnItemClickListener { view, position ->
                Toast.makeText(this@WaterfallActivity, "" + position, Toast.LENGTH_SHORT).show()
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
//            layoutManager = LinearLayoutManager(context).apply {
//                orientation = RecyclerView.VERTICAL
//                reverseLayout = false
//            }
//            layoutManager = GridLayoutManager(this@WaterfallActivity, 2)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            //            addItemDecoration(ItemDecoration(20));//单位px
        }
    }
}