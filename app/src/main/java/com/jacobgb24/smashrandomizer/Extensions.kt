package com.jacobgb24.smashrandomizer

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.support.v7.widget.TooltipCompat
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView

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