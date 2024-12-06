package com.tomcan.frame.v

import androidx.databinding.ViewDataBinding
import com.tomcan.frame.vm.BaseViewModel

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/9 20:29
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel<*>> :
    QuickFragment<V, VM>() {

}