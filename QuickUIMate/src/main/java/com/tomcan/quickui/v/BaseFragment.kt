package com.tomcan.quickui.v

import androidx.databinding.ViewDataBinding
import com.tomcan.quickui.vm.BaseViewModel

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/9 20:29
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel<*>> :
    QuickFragment<V, VM>() {

}