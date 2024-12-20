package com.tomcan.quickuimate.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.tomcan.quickui.ui.QuickBottomSheetDialogFragment
import com.tomcan.quickuimate.R
import com.tomcan.quickuimate.databinding.FragmentDialogMyBottomBinding
import com.tomcan.quickuimate.viewmodel.HomeViewModel

/**
 * @author Litemob
 * @description:
 * @date: 2024/12/19 17:44
 */
class MyBottomFragmentDialog :
    QuickBottomSheetDialogFragment<FragmentDialogMyBottomBinding, HomeViewModel>() {

    override fun layout() = R.layout.fragment_dialog_my_bottom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MyBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
//        binding.root.setBackgroundResource(R.drawable.shape_fragment_dialog_bottom_bg)
        return binding.root
    }

    override fun onStarted() {
        (binding.root.parent as ViewGroup).setBackgroundResource(R.drawable.shape_fragment_dialog_bottom_bg)
//        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_fragment_dialog_bottom_bg)
        (dialog?.window?.decorView as ViewGroup).let {
            for (i in 0..<it.childCount) {
                Log.e(TAG, "onStarted: " + it.getChildAt(i).id)
            }
        }
//        dialog?.window?.findViewById<ViewGroup>(R.id.design_bottom_sheet)?.setBackgroundResource(R.drawable.shape_fragment_dialog_bottom_bg)
//            ?.setBackgroundResource(R.drawable.shape_fragment_dialog_bottom_bg)
    }

    override fun onReStart() {

    }
}