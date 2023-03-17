package com.example.basearchitectureproject.util

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object SnacksBar {

    //display SnackBar
    fun errorSnackBar(view: View, s: String, context: Context) {

        Snackbar.make(
            view,
            s, Snackbar.LENGTH_LONG
        ).setBackgroundTint(
            ContextCompat.getColor(context, android.R.color.holo_red_light)
        )
            .setActionTextColor(
                ContextCompat.getColor(context, android.R.color.white)
            ).show()

    }
}