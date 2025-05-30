package com.tomcan.quickui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * @author LiteMob
 * @description:
 * @date: 2025/5/28 17:31
 */
class ActivityUtils {
    companion object {
        fun getActivity(content: Context?): Activity? {
            return when (content) {
                null -> {
                    null
                }

                is Activity -> {
                    content
                }

                is ContextWrapper -> {
                    getActivity(content.baseContext)
                }

                else -> null
            }
        }
    }
}