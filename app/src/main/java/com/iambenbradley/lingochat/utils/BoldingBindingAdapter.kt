package com.iambenbradley.lingochat.utils

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.widget.TextView



/**
 * Created by Ben on 12/3/2017.
 */
@android.databinding.BindingAdapter("android:typeface")
fun setTypeface(v: TextView, style: String) {
    when (style) {
        "bold" -> v.setTypeface(null, Typeface.BOLD)
        else -> v.setTypeface(null, Typeface.NORMAL)
    }
}