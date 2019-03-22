package com.jacobgb24.smashrandomizer

import android.os.Build
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