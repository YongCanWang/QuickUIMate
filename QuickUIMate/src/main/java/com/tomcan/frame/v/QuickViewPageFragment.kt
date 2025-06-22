package com.tomcan.frame.v

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tomcan.frame.vm.QuickViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author TomCan
 * @description: 由于ViewPage中的Fragment在切换之时，会重复的销毁和创建，重走生命周期方法onCreateView、onDestroy
 * 故在onDestroy 方法中不再执行binding的解绑以及ViewModel的释放等代码
 * 以此来保证View的复用，以及避免binding出现NullPointerException错误的问题
 * 并且提供了destroy方法，在需要的时候调用，释放相关资源对象
 * @date :2019/3/20 9:17
 */
abstract class QuickViewPageFragment<V : ViewDataBinding, VM : QuickViewModel<*>> : Fragment() {
    val TAG: String = javaClass.simpleName
    private var mIsFirstVisit = true
    lateinit var binding: V
    private val mLazy = lazy { getLazyViewModel() }
    val viewModel: VM by mLazy
    private val mViewModelProvider: ViewModelProvider by lazy { ViewModelProvider(this) }
    private var mIsResume = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getLayout()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (mIsFirstVisit) {
            addCallback()
            onStarted()
        }
        mIsFirstVisit = false
    }

    override fun onResume() {
        super.onResume()
        if (mIsResume) {
            onReStart()
        }
        mIsResume = true
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

    abstract fun getLayout(): V

    /**
     * 在整个Activity生命周期中只调用一次
     */
    open fun onStarted() {

    }

    /**
     * 在整个Activity生命周期中可能会被调用多次，注意Activity首次被创建时不会被调用
     */
    open fun onReStart() {

    }

    fun backPressedEnabled(): Boolean {
        return false
    }

    fun backPressed() {
    }

    private fun getLazyViewModel(): VM {
        val mGenericSuperclass = javaClass.genericSuperclass as ParameterizedType
        val actualTypeArguments: Array<out Type>? = mGenericSuperclass.actualTypeArguments
        val vmClass = actualTypeArguments?.get(1) as Class<VM>
        val v = mViewModelProvider[vmClass]
        lifecycle.addObserver(v)
        return v
    }

    fun <T : QuickViewModel<*>> getQuickViewModel(quickViewModel: Class<T>): T {
        val t: T = mViewModelProvider[quickViewModel]
        lifecycle.addObserver(t)
        return t
    }

    /**
     * 解绑 binding
     * 释放 ViewModel
     */
    fun destroy() {
        mIsFirstVisit = true
        if (this::binding.isInitialized) {
            binding.unbind()
        }
        if (mLazy.isInitialized()) {
            lifecycle.removeObserver(viewModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
