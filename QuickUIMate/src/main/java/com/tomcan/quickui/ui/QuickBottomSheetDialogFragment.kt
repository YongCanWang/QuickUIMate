package com.tomcan.quickui.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomcan.frame.vm.QuickViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author Tom灿
 * @description:
 * @date: 2024/12/8 17:50
 */
abstract class QuickBottomSheetDialogFragment<V : ViewDataBinding, VM : QuickViewModel<*>> :
    BottomSheetDialogFragment() {
    val TAG: String = javaClass.simpleName
    private var mIsFirstVisit = true
    lateinit var binding: V
    val viewModel: VM? by lazy { getLazyViewModel() } // TODO 可为null 待优化
    private var mViewModelProvider: ViewModelProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModelProvider = ViewModelProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!this::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (mIsFirstVisit) {
            addCallback()
            onStarted()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mIsFirstVisit) {
            onReStart()
        }
        mIsFirstVisit = false
    }

    private fun getLazyViewModel(): VM? {
        val mGenericSuperclass = javaClass.genericSuperclass as ParameterizedType
        val actualTypeArguments: Array<out Type>? = mGenericSuperclass?.actualTypeArguments
        actualTypeArguments?.takeIf { it.size > 1 }?.let {
            val vmClass = actualTypeArguments[1] as Class<VM>
            mViewModelProvider?.get(vmClass)?.let {
                lifecycleScope.launch {
                    lifecycle.addObserver(it)
                }
                return it
            }
        }
        return null
    }

    private fun addCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(backPressedEnabled()) {
                override fun handleOnBackPressed() {
                    backPressed()
                }
            })
    }

    abstract fun layout(): Int

    /**
     * 在整个Activity生命周期中只调用一次
     */
    abstract fun onStarted()

    /**
     * 在整个Activity生命周期中可能会被调用多次，注意Activity首次被创建时不会被调用
     */
    abstract fun onReStart()

    open fun backPressedEnabled(): Boolean {
        return false
    }

    fun backPressed() {

    }

    fun <T : QuickViewModel<*>> getQuickViewModel(quickViewModel: Class<T>): T {
        val t: T? = mViewModelProvider?.get(quickViewModel)
        lifecycle.addObserver(t!!)
        return t
    }

    override fun onDestroy() {
        mIsFirstVisit = true
        binding.unbind()
        viewModel?.let {
            lifecycle.removeObserver(it)
        }
        mViewModelProvider = null
        super.onDestroy()
    }
}
