package com.example.projectandroid2.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Rectangle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projectandroid2.R

@Composable
fun NoInternetConnection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                // .size(height = 30.dp, width = 300.dp)
                .background(MaterialTheme.colorScheme.error)

        ){
            Row(modifier = Modifier.align(Alignment.Center)) {
                Icon(painterResource(id = R.drawable.wifi_off), null,
                    tint = MaterialTheme.colorScheme.onError, modifier = Modifier.padding(end = 10.dp))
                Text(text = "No internet connection", color = MaterialTheme.colorScheme.onError)
            }

        }
    }
}