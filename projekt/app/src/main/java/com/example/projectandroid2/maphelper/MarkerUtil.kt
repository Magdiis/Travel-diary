package com.example.projectandroid2.maphelper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.projectandroid2.R

class MarkerUtil {
    companion object {
        fun createMarkerIconFromResource(context: Context, resource: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(context, resource)
            drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bm)
            drawable.draw(canvas)
            return bm
        }


        fun createBitmapMarker(
            context: Context,
            type: String
        ): Bitmap {
            return when(type){
                "City" -> {
                     createMarkerIconFromResource(context, R.drawable.citymap__1_)
                }

                "Historical Building" -> {
                    createMarkerIconFromResource(context, R.drawable.historicalbuildingmap__1_)
                }

                "Forest" -> {
                    createMarkerIconFromResource(context, R.drawable.forestmap__1_)
                }

                "Mountains" -> {
                    createMarkerIconFromResource(context, R.drawable.landscapemap__1_)
                }

                "Beach" -> {
                    createMarkerIconFromResource(context, R.drawable.beachmap__1_)
                }

                else -> {
                    createMarkerIconFromResource(context, R.drawable.citymap__1_)
                }

            }
        }
    }

}
