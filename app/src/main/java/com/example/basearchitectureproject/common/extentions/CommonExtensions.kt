package com.example.basearchitectureproject.common.extentions

import androidx.appcompat.app.ActionBar
import java.lang.reflect.Field

fun ActionBar.disableShowHideAnimation() {
    try {
        javaClass.getDeclaredMethod("setShowHideAnimationEnabled", Boolean::class.javaPrimitiveType)
            .invoke(this, false)
    } catch (exception: Exception) {
        try {
            val mActionBarField: Field? =
                javaClass.superclass?.getDeclaredField("mActionBar")
            mActionBarField?.let {
                mActionBarField.setAccessible(true)
                val icsActionBar: Any = mActionBarField.get(this)
                val mShowHideAnimationEnabledField: Field =
                    icsActionBar.javaClass.getDeclaredField("mShowHideAnimationEnabled")
                mShowHideAnimationEnabledField.setAccessible(true)
                mShowHideAnimationEnabledField.set(icsActionBar, false)
                val mCurrentShowAnimField: Field =
                    icsActionBar.javaClass.getDeclaredField("mCurrentShowAnim")
                mCurrentShowAnimField.setAccessible(true)
                mCurrentShowAnimField.set(icsActionBar, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}