package com.tomcan.frame.v

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.tomcan.frame.obs.IBaseLifecycle
import com.tomcan.quickui.utils.ActivityUtils.Companion.getActivity


/**
 * @author Tom灿
 * @description: BaseDialog 观察Activity的生命周期
 * @date :2024/6/26 21:50
 */
abstract class BaseDialog<V : ViewBinding> : AlertDialog {
    lateinit var binding: V
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
        getActivity(context)?.let {
            (it as AppCompatActivity).let { activity ->
                setOnDismissListener {
                    activity.lifecycle.removeObserver(mIBaselLifecycle)
                }
                activity.lifecycle.addObserver(mIBaselLifecycle)
            }
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
        getActivity(context)?.let {
            (it as AppCompatActivity).let { activity ->
                if (!isShowing && !activity.isFinishing && !activity.isDestroyed) {
                    show()
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    abstract fun getLayout(): V
}