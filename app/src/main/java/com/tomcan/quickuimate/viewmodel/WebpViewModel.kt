package com.tomcan.quickuimate.viewmodel

import android.app.Application
import com.tomcan.frame.m.BaseModel
import com.tomcan.frame.vm.BaseViewModel
import com.tomcan.quickuimate.bean.WebpBean

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/9 21:45
 */
class WebpViewModel(application: Application) : BaseViewModel<BaseModel>(application) {

    fun getData() = ArrayList<WebpBean>().apply {
        for (i in 1 until 7) {
            add(WebpBean().apply {
                name = "" + i
                path = "$i.webp"
            })
        }
    }
}