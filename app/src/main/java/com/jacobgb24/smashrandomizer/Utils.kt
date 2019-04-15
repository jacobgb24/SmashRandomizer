package com.jacobgb24.smashrandomizer

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat

/**
 * Wrapper for setting both the contentDescription and tooltipText
 * @param str the string to set these properties to
 */
fun ImageView.setHelp(str: String) {
    contentDescription = str
    if (Build.VERSION.SDK_INT >= 26) {
        tooltipText = str
    }
    else {
        TooltipCompat.setTooltipText(this, str)
    }
}


/**
 * Applies a foreground ripple using the button's background as a bounding mask
 * @param color the desired color of the ripple
 */
fun ImageButton.addRippleFG(color: String = "#44DEDEDE") {
    val ripple = RippleDrawable(ColorStateList.valueOf(Color.parseColor(color)), null, drawable)
    foreground = ripple
}


/**
 * Returns a color int with an optional alpha value.
 * @param color: A color int, e.g. R.color.green
 * @param alpha: An int between 0-255 specifying alpha channel
 */
fun getColor(context: Context, color: Int, alpha: Int = 255): Int {
    val baseColor = ContextCompat.getColor(context, color)
    return when (color == android.R.color.transparent) {
        true -> color  // leave transparent alone, otherwise we'd apply alpha to it
        false -> Color.argb(alpha, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor))
    }
}


/**
 * Plays the given sound after an optional delay
 * @param sound a resource id from raw. e.g. R.raw.congrats
 * @param delay milliseconds to wait before playing
 */
fun playSound(context: Context, sound: Int, delay: Long = 0) {
    object : CountDownTimer(delay, delay) {
        override fun onFinish() {
            val mp = MediaPlayer.create(context, sound)
            mp.setOnCompletionListener {
                mp.stop()
                mp.reset()
                mp.release()
            }
            mp.setOnPreparedListener {
                mp.start()
            }
        }

        override fun onTick(millisUntilFinished: Long) {

        }
    }.start()
}


fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.asDP(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun getBoolPref(pref: String, context: Context): Boolean {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(pref, false)
}