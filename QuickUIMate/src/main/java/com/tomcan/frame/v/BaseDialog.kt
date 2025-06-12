package com.tomcan.frame.v

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
abstract class BaseDialog<V : ViewBinding> : Dialog {
    lateinit var binding: V
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

    constructor(context: Context) : super(context) {
        getActivity(context)?.let {
            (it as AppCompatActivity).let { activity ->
                setOnDismissListener {
                    activity.lifecycle.removeObserver(mIBaseLifecycle)
                }
                activity.lifecycle.addObserver(mIBaseLifecycle)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = getLayout().also { setContentView(it.root) }
    }

    override fun show() {
        getActivity(context)?.let {
            (it as AppCompatActivity).let { activity ->
                if (!isShowing && !activity.isDestroyed && !activity.isFinishing) {
                    super.show()
                }
            }
        }
    }

    override fun dismiss() {
        if (isShowing) {
            super.dismiss()
        }
    }

    abstract fun getLayout(): V
}