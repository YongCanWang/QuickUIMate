package com.tomcan.frame.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.tomcan.frame.m.QuickModel
import com.tomcan.frame.obs.IBaseLifecycle
import java.lang.reflect.ParameterizedType

/**
 * @author TomCan
 * @description:
 * @date :2020/8/5 15:34
 */
abstract class QuickViewModel<M : QuickModel>(val context: Application) : AndroidViewModel(
    context
), IBaseLifecycle {
    val TAG: String = javaClass.simpleName
    lateinit var model: M

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
        Log.d(TAG, "onAny")
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        val genericSuperclass = checkNotNull(javaClass.genericSuperclass as ParameterizedType)
        val actualTypeArguments = genericSuperclass.actualTypeArguments
        val vmClass = actualTypeArguments[0] as Class<M>
        try {
            model = vmClass.newInstance()
            model.application = context
        } catch (e: IllegalAccessException) {
            Log.i(TAG, e.toString())
        } catch (e: InstantiationException) {
            Log.i(TAG, e.toString())
        }
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
    }
}
