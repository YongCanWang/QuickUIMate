package com.tomcan.frame.v

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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomcan.frame.obs.IBaseLifecycle
import com.tomcan.frame.vm.QuickViewModel
import com.tomcan.quickui.utils.ActivityUtils.Companion.getActivity
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
    private val mLazy = lazy { getLazyViewModel() }
    val viewModel: VM by mLazy
    private val mViewModelProvider: ViewModelProvider by lazy { ViewModelProvider(this) }
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
            getActivity(context)?.let {
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

//    override fun dismiss() {
//        requireActivity()?.takeIf { !it.isDestroyed && !it.isFinishing }?.let {
//            takeIf { isAdded }?.let {
//                dialog?.takeIf {
//                    it.isShowing
//                }?.let {
//                    super.dismiss()
//                }
//            }
//        }
//    }

    open fun show(manager: FragmentManager) {
        super.show(manager, javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsFirstVisit = true
        if (this::binding.isInitialized) {
            binding.unbind()
        }
        if (mLazy.isInitialized()) {
            lifecycle.removeObserver(viewModel)
        }
        getActivity(context)?.let {
            (it as AppCompatActivity).let { activity ->
                activity.lifecycle.removeObserver(mIBaseLifecycle)
            }
        }
    }
}
