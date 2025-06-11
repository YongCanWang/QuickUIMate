package com.tomcan.quickui.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomcan.frame.obs.IBaseLifecycle
import com.tomcan.frame.vm.QuickViewModel
import com.tomcan.quickui.utils.ActivityUtils
import com.tomcan.quickui.utils.ActivityUtils.Companion
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
    open val TAG: String = javaClass.simpleName
    private var mIsFirstVisit = true
    lateinit var binding: V
    open val viewModel: VM? by lazy { getLazyViewModel() } // TODO 可为null 待优化
    private var mViewModelProvider: ViewModelProvider? = null
    private val mIBaseLifecycle = object : IBaseLifecycle {
        override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
        }

        override fun onCreate() {
        }

        override fun onDestroy() {
            dismiss()
        }

        override fun onStart() {
        }

        override fun onStop() {
        }

        override fun onResume() {
        }

        override fun onPause() {
        }
    }

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
            binding = getLayout()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (mIsFirstVisit) {
            ActivityUtils.getActivity(context)?.let {
                (it as AppCompatActivity).let { activity ->
                    activity.lifecycle.addObserver(mIBaseLifecycle)
                }
            }
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

    abstract fun getLayout(): V

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

    override fun dismiss() {
        requireActivity()?.takeIf { !it.isFinishing && !it.isDestroyed }?.let {
            takeIf { isAdded }?.let {
                dialog?.takeIf {
                    it.isShowing
                }?.let {
                    super.dismiss()
                }
            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
//        requireActivity()?.takeIf { !it.isFinishing && !it.isDestroyed }?.let {
//            takeIf { !isAdded }?.let { super.show(manager, tag) }
//        }
        super.show(manager, tag)
    }

    override fun onDestroy() {
        mIsFirstVisit = true
        binding.unbind()
        viewModel?.let {
            lifecycle.removeObserver(it)
        }
        mViewModelProvider = null
        ActivityUtils.getActivity(context)?.let {
            (it as AppCompatActivity).let { activity ->
                activity.lifecycle.removeObserver(mIBaseLifecycle)
            }
        }
        super.onDestroy()
    }
}
