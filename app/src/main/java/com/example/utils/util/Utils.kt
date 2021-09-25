package com.example.utils.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.univ_mem.course.config.ConfigActivity
import com.example.univ_mem.MyApplication
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/9/4.
 * TIME : 20:21.
 */

/**
 * 工具类：
 * 设置背景
 */
object Utils {
    private var PATH: String? = null
    private const val BG_NAME = "bg.jpg"
    private var bgBitmap: Bitmap? = null
    private const val SINGLE_DOUBLE_WEEK = 0
    private const val SINGLE_WEEK = 1
    private const val DOUBLE_WEEK = 2
    private val WEEK_OPTIONS = arrayOf("周", "单周", "双周")

    @JvmStatic
    fun setPATH(PATH: String?) {
        Utils.PATH = PATH
    }

    /**
     * 设置背景图片
     *
     * @param context
     * @param imageView
     */
    @JvmStatic
    fun setBackGround(context: Context, imageView: ImageView) {
        setBackGround(context, imageView, Config.getBgId())
    }

    @JvmStatic
    fun setBackGround(context: Context, imageView: ImageView, id: Int) {

        if (id == ConfigActivity.CUSTOM_BG_ID) {
            refreshBg(context, id)
            imageView.setImageBitmap(bgBitmap)
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, id))
        }

    }

    @JvmStatic
    val date: String
        get() {
            val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA)
            return simpleDateFormat.format(Date())
        }

    /**
     * 计算1970年1月4号0时0分0秒(周一)至今有多少周
     * 用于更新周数，用一年周数的话跨年会产生问题
     *
     * @return
     */
    @JvmStatic
    val weekNum: Long
        get() {
            //System.currentTimeMillis()返回的是1970年1月4号0时0分0秒距今多少毫秒
            val day = System.currentTimeMillis() / (1000 * 60 * 60 * 24) - 4 //减四为1月4日距今多少天
            //Log.d("weeknum",String.valueOf(day/7+1));
            return day / 7 + 1
        }

    /**
     * 刷新背景
     *
     * @param context
     * @param id
     */
    @JvmStatic
    fun refreshBg(context: Context, id: Int) {
        if (id == ConfigActivity.CUSTOM_BG_ID) {
            val file = File(PATH, BG_NAME)
            if (file.exists()) {
                bgBitmap = BitmapFactory.decodeFile(file.absolutePath)
            }
        }
    }

    /**
     * 设置CardView透明度
     *
     * @param cardView
     */
    @JvmStatic
    fun setCardViewAlpha(cardView: CardView) {
        cardView.alpha = Config.getCardViewAlpha()
    }

    /**
     * 判断是单周、双周、还是周
     *
     * @param weekOfTerm
     * @return
     */
    @JvmStatic
    fun getWeekOptionFromWeekOfTerm(weekOfTerm: Int): Int {
        var singleWeek: Int = 0x55555555 //二进制:0101,0101,0101,0101,0101,0101,0101,0101
        var doubleWeek: Int = singleWeek.inv() //二进制:1010,1010,1010,1010,1010,1010,1010,1010

        //如果总周数是偶数则互换，保证算法的正确性
        if (Config.getMaxWeekNum() % 2 == 0) {
            val temp = singleWeek
            singleWeek = doubleWeek
            doubleWeek = temp
        }
        //快速判断是否有单周或者双周
        val hasSingleWeek = singleWeek and weekOfTerm != 0
        val hasDoubleWeek = doubleWeek and weekOfTerm != 0
        return if (hasSingleWeek && hasDoubleWeek) {
            SINGLE_DOUBLE_WEEK
        } else if (hasSingleWeek) {
            SINGLE_WEEK
        } else if (hasDoubleWeek) {
            DOUBLE_WEEK
        } else {
            -1
        }
    }

    /**
     * @param weekOfTerm
     * @return 获取格式为"1-9,19,20-25 [周]"的周数
     */
    @JvmStatic
    fun getFormatStringFromWeekOfTerm(weekOfTerm: Int): String {
        if (!isWeekOfTermValid(weekOfTerm)) {
            return "请选择周数"
        }
        return getStringFromWeekOfTerm(weekOfTerm) +
                " [" +
                WEEK_OPTIONS[getWeekOptionFromWeekOfTerm(weekOfTerm)] +
                "]"
    }

    /**
     * @param weekOfTerm
     * @return 是否有上课的周数
     */
    @JvmStatic
    fun isWeekOfTermValid(weekOfTerm: Int): Boolean {
        return ((1 shl Config.getMaxWeekNum()) - 1) and weekOfTerm != 0
    }

    /**
     * 生成1-18,19,25格式的周数
     *
     * @param weekOfTerm
     * @return
     */
    @JvmStatic
    fun getStringFromWeekOfTerm(weekOfTerm: Int): String {
        var weekOfTermVar = weekOfTerm
        if (weekOfTermVar == 0) {
            return ""
        }

        val stringBuilder = StringBuilder()
        val weekOptions = getWeekOptionFromWeekOfTerm(weekOfTermVar)
        val week = BooleanArray(Config.getMaxWeekNum())
        for (i in Config.getMaxWeekNum() - 1 downTo 0) {
            week[i] = weekOfTermVar and 0x01 == 1
            weekOfTermVar = weekOfTermVar shr 1
        }
        var start = 1
        var space = 2
        when (weekOptions) {
            SINGLE_DOUBLE_WEEK -> space = 1
            SINGLE_WEEK -> {
            }
            DOUBLE_WEEK -> start = 2
            else -> return "error"
        }
        var count = 0
        var i = start
        while (i <= Config.getMaxWeekNum()) {
            if (week[i - 1]) {
                if (count == 0) {
                    stringBuilder.append(i)
                }
                count += 1
            } else {
                if (count == 1) {
                    stringBuilder.append(',')
                } else if (count > 1) {
                    stringBuilder.append('-')
                    stringBuilder.append(i - space)
                    stringBuilder.append(',')
                }
                count = 0
            }
            i += space
        }
        if (count > 1) {
            stringBuilder.append('-')
            var max = Config.getMaxWeekNum()
            if (start == 1 && max % 2 == 0) { //单周
                max--
            } else if (start == 2 && max % 2 == 1) { //双周
                max--
            }
            stringBuilder.append(max)
        }
        val len = stringBuilder.length - 1
        if (stringBuilder[len] == ',') stringBuilder.deleteCharAt(len)
        return stringBuilder.toString()
    }

    @JvmStatic
    fun formatTime(time: Int): String {
        return if (time < 10) "0$time" else time.toString()
    }

    /**
     * 获取星期
     *
     * @return 返回1-7代表周几 周日为1
     */
    @JvmStatic
    val weekOfDay: Int
        get() {
            val date = Date()
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar[Calendar.DAY_OF_WEEK]
        }


    @JvmStatic
    fun showToast(@StringRes textId: Int, isShortLength: Boolean) {
        showToast(MyApplication.getApplication().resources.getString(textId), isShortLength)
    }

    @JvmStatic
    fun showToast(@StringRes textId: Int) {
        showToast(textId, true)
    }

    @JvmStatic
    fun showToast(text: String?) {
        showToast(text, true)
    }

    /**
     * 展示提醒
     * @param text 内容
     */
    @JvmStatic
    fun showToast(text: String?, isShortLength: Boolean) {
        if (!TextUtils.isEmpty(text)) {
            val duration = if (isShortLength) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
            Toast.makeText(MyApplication.getApplication(), text, duration).show()
        }
    }

    @JvmStatic
    fun getStatusBarAndActionBarHeight(activity: Activity): Int {
        return activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
    }
}