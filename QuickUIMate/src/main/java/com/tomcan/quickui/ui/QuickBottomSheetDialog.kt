package com.tomcan.quickui.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * @author Tom灿
 * @description:
 * @date: 2024/12/8 17:50
 */
abstract class QuickBottomSheetDialog<V : ViewDataBinding> :
    BottomSheetDialog {
    open val TAG: String = javaClass.simpleName
    private val mContext: Context
    lateinit var binding: V

    constructor(context: Context) : this(context, -1)

    constructor(context: Context, @StyleRes theme: Int) : super(context, theme) {
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        if (!this::binding.isInitialized) {
            val inflate = LayoutInflater.from(mContext).inflate(layout(), null)
            DataBindingUtil.bind<V>(inflate)?.let {
                binding = it
            }
            setContentView(binding.root)
        }

        setOnCancelListener {
            binding.unbind()
        }
    }

    abstract fun layout(): Int

    open fun cancelDialog() {
        (mContext as AppCompatActivity)?.takeIf { !it.isFinishing && !it.isDestroyed }?.let {
            takeIf { isShowing }?.let {
                cancel()
            }
        }
    }
}
