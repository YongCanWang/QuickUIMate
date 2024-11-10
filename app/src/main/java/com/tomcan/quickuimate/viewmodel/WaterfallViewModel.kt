package com.tomcan.quickuimate.viewmodel

import android.app.Application
import com.tomcan.quickui.m.BaseModel
import com.tomcan.quickui.vm.BaseViewModel
import com.tomcan.quickuimate.bean.InfoBean

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/9 21:45
 */
class WaterfallViewModel(application: Application) : BaseViewModel<BaseModel>(application) {

    fun getData() = ArrayList<InfoBean>().apply {
        for (i in 1 until 11) {
            add(InfoBean().apply {
                name = "王靖雯" + i
                path = "$i.jpg"
            })
        }
    }
}