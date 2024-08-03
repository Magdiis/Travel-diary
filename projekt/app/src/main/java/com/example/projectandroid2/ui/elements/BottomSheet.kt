package com.example.projectandroid2.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projectandroid2.extension.round
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.theme.primaryColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    place: Place,
    navigation: INavigationRouter
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier.height(350.dp),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalAlignment = Alignment.Start) {
            Text(text = place.name, style = MaterialTheme.typography.headlineSmall)
            Row {
                Text(text = place.country, style = MaterialTheme.typography.labelMedium)
            }

            Divider(modifier = Modifier.padding(top = 16.dp))
            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.LocationOn, contentDescription = "", tint = primaryColor,
                    modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp))
                Text(text = "lat: ${place.latitude.round()}, lon: ${place.longitude.round()}")
            }
            Button(onClick = {
                scope.launch { modalBottomSheetState.hide() }.invokeOnCompletion {
                    navigation.navigateToDetail(place.id!!)
                }
            }) {
                Text(text = "Go to detail")
            }

        }

    }
}
