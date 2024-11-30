package com.tomcan.quickuimate.viewmodel

import android.app.Application
import com.google.gson.Gson
import com.tomcan.quickui.m.BaseModel
import com.tomcan.quickui.vm.BaseViewModel
import com.tomcan.quickuimate.bean.InfoBean
import com.tomcan.quickuimate.bean.Skin
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

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

        for (i in 11 until 30) {
            add(InfoBean().apply {
                name = "王靖雯" + i
                path = "8.jpg"
            })
        }

    }

    fun getSkinData() = readSkinDataForAssets(application).skin

    private fun readSkinDataForAssets(application: Application): Skin {
        val stringBuilder = StringBuilder()
        try {
            val inputStream = application.assets.open("skin/skin.json")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while ((bufferedReader.readLine().also { line = it }) != null) {
                stringBuilder.append(line)
            }
            inputStream.close()
            bufferedReader.close()
        } catch (e: IOException) {
        }
        return Gson().fromJson(
            stringBuilder.toString(),
            Skin::class.java
        )
    }
}