package com.mypackage.it314_health_center.helpers

import android.annotation.SuppressLint
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.mypackage.it314_health_center.R

internal object HelperFunctions {

    @SuppressLint("ClickableViewAccessibility")
    fun setPasswordView(view: EditText) {
        view.setOnTouchListener(View.OnTouchListener { _, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= view.right - view.compoundDrawables[drawableRight].bounds.width()
                ) {

                    if (view.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                        view.inputType = InputType.TYPE_CLASS_TEXT
                        view.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_eye_close,
                            0
                        )
                    } else {
                        view.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        view.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_eye_open,
                            0
                        )
                    }
                    // your action here
                    return@OnTouchListener true
                }
            }
            false
        })
    }
}