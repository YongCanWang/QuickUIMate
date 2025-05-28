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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MyBottomSheetDialog)
    }

    override fun getLayout() = FragmentDialogMyBottomBinding.inflate(layoutInflater)

    override fun onStarted() {

    }

    override fun onReStart() {

    }
}