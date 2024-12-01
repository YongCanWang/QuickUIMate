package com.tomcan.quickui.utils

import android.content.Context


/**
 * @author Tom灿
 * @description:
 * @date: 2024/12/1 18:15
 */
class Utils {
    companion object {
        /**
         * dp转px
         */
        fun dp2px(context: Context, dp: Float): Int {
            val density = context.resources.displayMetrics.density
            return (dp * density + 0.5f).toInt()
        }

        /**
         * sp转px
         */
        fun sp2px(context: Context, sp: Float): Int {
            val scaledDensity = context.resources.displayMetrics.scaledDensity
            return (sp * scaledDensity + 0.5f).toInt()
        }

        /**
         * px转dp
         */
        fun px2dp(context: Context, px: Float): Int {
            val density = context.resources.displayMetrics.density
            return (px / density + 0.5f).toInt()
        }

        /**
         * px转sp
         */
        fun px2sp(context: Context, px: Float): Int {
            val scaledDensity = context.resources.displayMetrics.scaledDensity
            return (px / scaledDensity + 0.5f).toInt()
        }
    }
}