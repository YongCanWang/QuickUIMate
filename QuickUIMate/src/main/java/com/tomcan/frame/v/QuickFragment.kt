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
 * @description:
 * @date :2019/3/20 9:17
 */
abstract class QuickFragment<V : ViewDataBinding, VM : QuickViewModel<*>> : Fragment() {
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

    open fun backPressedEnabled(): Boolean {
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

    override fun onDestroy() {
        super.onDestroy()
        mIsFirstVisit = true
        if (this::binding.isInitialized) {
            binding.unbind()  // TODO 待优化 会自动释放，不需要手动释放
        }
        if (mLazy.isInitialized()) {
            lifecycle.removeObserver(viewModel)
        }
    }
}
