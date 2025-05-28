package com.tomcan.frame.v

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.tomcan.frame.obs.IBaseLifecycle

/**
 * @author Tom灿
 * @description: BaseDialog 观察Activity的生命周期
 * @date :2024/6/26 21:50
 */
abstract class BaseDialog<V : ViewDataBinding> : AlertDialog {
    var binding: V? = null
    private val mIBaselLifecycle = object : IBaseLifecycle {
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
        (context as AppCompatActivity).let { activity ->
            setOnDismissListener {
                activity.lifecycle.removeObserver(mIBaselLifecycle)
                binding?.unbind()
                binding = null
            }
            activity.lifecycle.addObserver(mIBaselLifecycle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getLayout()
        binding?.let {
            setContentView(it.root)
        }
    }

    fun showDialog() {
        context?.let {
            (it as AppCompatActivity).let { activity ->
                if (!isShowing && !activity.isFinishing && !activity.isDestroyed) {
                    show()
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding?.unbind()
        binding = null
    }

    abstract fun getLayout(): V
}