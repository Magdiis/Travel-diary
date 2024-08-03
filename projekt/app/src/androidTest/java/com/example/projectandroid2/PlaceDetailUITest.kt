package com.example.projectandroid2

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projectandroid2.mock.PlaceMock
import com.example.projectandroid2.navigation.Destination
import com.example.projectandroid2.navigation.NavGraph
import com.example.projectandroid2.navigation.NavigationRouterImpl
import com.example.projectandroid2.ui.activities.main.MainActivity
import com.example.projectandroid2.ui.screens.detail.DetailScreen
import com.example.projectandroid2.ui.screens.detail.TestTagDescription
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PlaceDetailUITest {
    private lateinit var navController: NavHostController
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

    }

    @Test
    fun test_detail_screen_with_description(){
        launchPlaceDetailWithDescription()
        with(composeRule){
            onNodeWithTag(TestTagDescription)
                .assertIsDisplayed()
            Thread.sleep(5000)
        }
    }

    @Test
    fun test_detail_screen_without_description(){
        launchPlaceDetailWithoutDescription()
        with(composeRule){
            onNodeWithTag(TestTagDescription)
                .assertDoesNotExist()
            Thread.sleep(5000)
        }
    }


    private fun launchPlaceDetailWithDescription(){
        composeRule.activity.setContent{
            MaterialTheme {
                navController = rememberNavController()
                DetailScreen(navigation = NavigationRouterImpl(navController), id = 1L)
            }
        }
    }

    private fun launchPlaceDetailWithoutDescription(){
        composeRule.activity.setContent{
            MaterialTheme {
                navController = rememberNavController()
                DetailScreen(navigation = NavigationRouterImpl(navController), id = 2L)
            }
        }
    }
}