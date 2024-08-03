package com.example.projectandroid2.ui.screens.scan

import android.app.Activity
import android.app.ActivityOptions
import android.content.ContextWrapper
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.projectandroid2.R
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.activities.camera.CameraXLivePreviewActivity
import com.example.projectandroid2.ui.activities.main.MainActivity

@Composable
fun ScanScreen(
    navigation: INavigationRouter
){
    ScanScreenContent(navigation = navigation)
}

@Composable
fun ScanScreenContent(
    navigation: INavigationRouter
){
    val context = LocalContext.current
    val activity: Activity = context as Activity
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = stringResource(R.string.memory_diary), style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 50.dp))
            Image(painter = painterResource(id = R.drawable.scan), contentDescription = null,
                modifier = Modifier.padding(16.dp))
            Text(
                stringResource(R.string.please_scan_your_isic),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp))
            Button(onClick = {
                context.startActivity(Intent(context,CameraXLivePreviewActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                // context.startActivity(Intent(context,CameraXLivePreviewActivity::class.java))
            },modifier= Modifier.padding(top = 10.dp)
            ) {
                Text(text = stringResource(R.string.scan))
            }
        }

    }
}

