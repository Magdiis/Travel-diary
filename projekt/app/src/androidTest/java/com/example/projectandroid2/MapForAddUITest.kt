package com.example.projectandroid2

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projectandroid2.mock.CountriesMock
import com.example.projectandroid2.navigation.Destination
import com.example.projectandroid2.navigation.NavGraph
import com.example.projectandroid2.navigation.NavigationRouterImpl
import com.example.projectandroid2.ui.activities.main.MainActivity
import com.example.projectandroid2.ui.elements.TestTagInfoElementLocation
import com.example.projectandroid2.ui.screens.addplace.TestTagButtonForAddPlace
import com.example.projectandroid2.ui.screens.detail.DetailScreen
import com.example.projectandroid2.ui.screens.listofcountries.TestTagListOfCountriesLazyList
import com.example.projectandroid2.ui.screens.mapforadd.MapForAddScreen
import com.example.projectandroid2.ui.screens.mapforadd.TestTagBowWarning
import com.example.projectandroid2.ui.screens.mapforadd.TestTagButtonSaveLocationEnabled
import com.example.projectandroid2.ui.screens.places.TestTagButtonAddPlace
import com.example.projectandroid2.utils.JSONUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MapForAddUITest {
    private lateinit var navController: NavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test_outside_of_country(){
        launchMapForAddScreen()
        with(composeRule){
            waitUntilExactlyOneExists(hasTestTag(TestTagButtonSaveLocationEnabled),
                timeoutMillis = 5000)
            onNodeWithTag(TestTagButtonSaveLocationEnabled)
             .assertIsDisplayed()
                .performClick()
            Thread.sleep(2000)
            onNodeWithTag(TestTagBowWarning)
                .assertIsDisplayed()
        }
    }

    private fun launchMapForAddScreen(){
        composeRule.activity.setContent{
            MaterialTheme {
                navController = rememberNavController()
                val countryInfo = JSONUtils().convertCountryToString(CountriesMock.outsideOfCzechia)
                MapForAddScreen(
                    navigation = NavigationRouterImpl(navController),
                    countryInfo = countryInfo,
                    latitudeOfMarker = null,
                    longitudeOfMarker = null
                )
            }
        }
    }


}