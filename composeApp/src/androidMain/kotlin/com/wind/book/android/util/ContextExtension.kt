package com.wind.book.android.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle

const val DATA = "data"

private interface SavedStateHandleInf<T> {
    fun getBundle(item: T): Bundle
    fun getData(savedStateHandle: SavedStateHandle): T
    fun getData(intent: Intent): T
}

class SavedStateHandleHelper<T> : SavedStateHandleInf<T> {
    override fun getBundle(item: T): Bundle {
        return bundleOf(DATA to item)
    }

    override fun getData(savedStateHandle: SavedStateHandle): T {
        return savedStateHandle.get(DATA)!!
    }

    override fun getData(intent: Intent): T {
        return intent.getBundleExtra(DATA)!!.getParcelable(DATA)!!
    }
}

inline fun <reified T : Activity> Activity.startActivity(data: Parcelable? = null) {
    Intent(this, T::class.java).apply {
        data?.let {
            putExtra(DATA, SavedStateHandleHelper<Parcelable>().getBundle(data))
        }
        startActivity(this)
    }
}
