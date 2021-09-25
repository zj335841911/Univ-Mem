package com.example.utils.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/9/4.
 * TIME : 18:59.
 */

object DialogUtils {

    @JvmStatic
    fun showTipDialog(context: Context, tip: String) {
        showTipDialog(context, "提示", tip)
    }

    @JvmStatic
    fun showTipDialog(context: Context, title: String, tip: String) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(tip)
                .setPositiveButton("知道了", null)
                .create()
                .show()
    }
}