package com.tomcan.frame.vm

import android.app.Application
import com.tomcan.frame.m.BaseModel

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/9 20:33
 */
abstract class BaseViewModel<M : BaseModel>(application: Application) :
    QuickViewModel<M>(application) {

}