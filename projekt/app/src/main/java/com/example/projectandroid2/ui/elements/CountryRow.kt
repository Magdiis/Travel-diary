package com.example.projectandroid2.ui.elements

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.example.projectandroid2.extension.shimmerLoadingAnimation
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.utils.JSONUtils

@Composable
fun CountryRow(country: Country, navigation: INavigationRouter){
    Box(modifier = Modifier.fillMaxWidth()){
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigation.navigateToListOfPlacesScreen(
                        JSONUtils().convertCountryToString(
                            country
                        )
                    )
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(country.name != null && country.flag != null && country.lat != null){
                    SubcomposeAsyncImage(model = country.flag!!, contentDescription = null) {
                        Log.d("STATE",painter.state.toString())
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error){
                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                                    .background(color = Color.LightGray, shape = CircleShape)
                                    .size(70.dp)
                                    .shimmerLoadingAnimation()
                            )

                        }  else {
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(top = 8.dp, start = 16.dp, end = 16.dp,bottom = 8.dp)
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .border(BorderStroke(1.dp, Color.DarkGray), CircleShape)

                            )
                        }
                    }
                    Text(text = country.name!!, style = MaterialTheme.typography.titleMedium)
                }
            }
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.LightGray)
        }

    }
}