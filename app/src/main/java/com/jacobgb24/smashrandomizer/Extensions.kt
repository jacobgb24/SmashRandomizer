package com.jacobgb24.smashrandomizer

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.widget.ImageButton
import android.widget.ImageView

/**
 * Wrapper for setting both the contentDescription and tooltipText if supported
 * @param str the string to set these properties to
 */
fun ImageView.setHelp(str: String) {
    contentDescription = str
    if (Build.VERSION.SDK_INT >= 26) {
        tooltipText = str
    }
}


/**
 * Applies a foreground ripple using the button's background as a bounding mask
 * @param color the desired color of the ripple
 */
fun ImageButton.addRippleFG(color: String = "#44DEDEDE") {
    val ripple = RippleDrawable(ColorStateList.valueOf(Color.parseColor(color)), background, background)
    foreground = ripple
}