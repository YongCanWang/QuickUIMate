package com.tomcan.frame.v

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.tomcan.frame.vm.QuickViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/9 21:03
 */
abstract class QuickActivity<V : ViewDataBinding, VM : QuickViewModel<*>> :
    AppCompatActivity() {
    open val TAG: String = javaClass.simpleName
    private var mIsFirstVisit = true
    lateinit var binding: V
    lateinit var viewModel: VM
    private var mGenericSuperclass: ParameterizedType? = null
    private var mViewModelProvider: ViewModelProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGenericSuperclass = javaClass.genericSuperclass as ParameterizedType
        checkNotNull(mGenericSuperclass)
        val actualTypeArguments: Array<out Type>? = mGenericSuperclass?.actualTypeArguments
        val vmClass = actualTypeArguments?.get(1) as Class<VM>
        mViewModelProvider = ViewModelProvider(this)
        viewModel = mViewModelProvider?.get<VM>(vmClass)!!
        viewModel.let { lifecycle.addObserver(it) }
        addCallback()
        binding = DataBindingUtil.setContentView<V>(this, layout())
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

    private fun addCallback() {
        onBackPressedDispatcher.addCallback(
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

    open fun backPressed() {

    }

    fun <T : QuickViewModel<*>> getQuickViewModel(quickViewModel: Class<T>): T? {
        val t: T? = mViewModelProvider?.get(quickViewModel)
        lifecycle.addObserver(t!!)
        return t
    }

    override fun onDestroy() {
        mIsFirstVisit = true
        binding.unbind()
        lifecycle.removeObserver(viewModel)
        mViewModelProvider = null
        super.onDestroy()
    }
}