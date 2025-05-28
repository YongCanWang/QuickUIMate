package com.tomcan.frame.v

import androidx.databinding.ViewDataBinding
import com.tomcan.frame.vm.BaseViewModel

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/9 22:09
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel<*>> :
    QuickActivity<V, VM>() {

}