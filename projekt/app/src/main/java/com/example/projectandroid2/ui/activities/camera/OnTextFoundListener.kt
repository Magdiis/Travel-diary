package com.example.projectandroid2.ui.activities.camera
import com.example.projectandroid2.mlkit.GraphicOverlay
import com.google.mlkit.vision.text.Text
import java.lang.Exception

interface OnTextFoundListener {
    fun onTextFound(text:Text)
    fun onFailure(exception: Exception)

}
