package com.example.common_ui_view.binding

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val formatter by lazy { SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()) }

@BindingAdapter("dateText")
fun TextView.dateText(milliSeconds: Long) {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = milliSeconds
    }
    text = formatter.format(calendar.time)
}

@BindingAdapter("html")
fun TextView.html(htmlText: String) {
    text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
}
