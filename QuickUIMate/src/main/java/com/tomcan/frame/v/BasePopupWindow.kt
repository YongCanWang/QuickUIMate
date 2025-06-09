package com.tomcan.frame.v

import android.content.Context
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.tomcan.frame.obs.IBaseLifecycle

/**
 * @author Tom灿
 * @description: BasePopupWindow 观察Activity的生命周期
 * @date :2024/6/26 21:59
 */
abstract class BasePopupWindow<V : ViewBinding> : PopupWindow {
    private var mContext: Context
    lateinit var binding: V
    private var mIBaseLifecycle = object : IBaseLifecycle {
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

    constructor(context: Context) : super(context) {
        mContext = context
        (context as AppCompatActivity).let { activity ->
            setOnDismissListener { activity.lifecycle.removeObserver(mIBaseLifecycle) }
            activity.lifecycle.addObserver(mIBaseLifecycle)
        }
        binding = getLayout()
        contentView = binding?.root
        setView()
    }

    fun getLayoutInflater() = (mContext as AppCompatActivity).layoutInflater

    abstract fun getLayout(): V

    abstract fun setView()
}