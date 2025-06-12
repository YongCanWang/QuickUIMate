package com.tomcan.quickuimate.view

import android.content.Context
import com.tomcan.frame.v.QuickBottomSheetDialog
import com.tomcan.quickuimate.databinding.DialogMyBottomBinding

/**
 * @author Litemob
 * @description:
 * @date: 2024/12/19 17:44
 */
class MyBottomDialog(context: Context) :
    QuickBottomSheetDialog<DialogMyBottomBinding>(context) {

    override fun getLayout() = DialogMyBottomBinding.inflate(layoutInflater)
}