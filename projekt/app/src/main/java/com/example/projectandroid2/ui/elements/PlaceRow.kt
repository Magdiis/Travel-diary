package com.example.projectandroid2.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projectandroid2.R
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.theme.basicMargin
import com.example.projectandroid2.ui.theme.primaryColor

@Composable
fun PlaceRow(place: Place, navigation: INavigationRouter){
    Card(
        modifier = Modifier
            .padding(top = basicMargin(), start = basicMargin(), end = basicMargin(), bottom = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navigation.navigateToDetail(place.id!!)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row( ){
                Box(modifier = Modifier
                    .padding(start = 8.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(primaryColor)

                ){
                    Icon(painterResource(id = returnPlaceIcon(place.type)),null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(35.dp), tint = Color.White)
                }
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = place.name,style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp))
                    Text(text = place.type, style = MaterialTheme.typography.bodySmall)
                }
            }
            Icon(painterResource(id = returnSmileIcon(place.smile))
                ,null,tint = primaryColor,modifier = Modifier
                .padding(8.dp)
                .size(35.dp))
         
        }
    }
}


fun returnPlaceIcon(place: String):Int {
    when(place) {
        "City" -> {
            return R.drawable.city
        }

        "Historical Building" -> {
            return R.drawable.historical_building
        }

        "Forest" -> {
            return R.drawable.forest
        }

        "Mountains" -> {
            return R.drawable.landscape
        }

        "Beach" -> {
            return R.drawable.beach
        }

        else -> {
            return return R.drawable.city
        }
    }
}

fun returnSmileIcon(place: String):Int {
    when(place) {
        "Satisfied" -> {
            return R.drawable.satisfied
        }

        "Neutral" -> {
            return R.drawable.neutral
        }

        "Dissatisfied" -> {
            return R.drawable.dissatisfied
        }

        "Very dissatisfied" -> {
            return R.drawable.very_dissatisfied
        }

        "Very satisfied" -> {
            return R.drawable.very_satisfied
        }

        else -> {
            return return R.drawable.satisfied
        }
    }
}

