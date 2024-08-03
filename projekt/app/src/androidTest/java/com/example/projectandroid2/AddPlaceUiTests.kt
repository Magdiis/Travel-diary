package com.example.projectandroid2

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projectandroid2.mock.CountriesMock
import com.example.projectandroid2.navigation.Destination
import com.example.projectandroid2.navigation.NavGraph
import com.example.projectandroid2.navigation.NavigationRouterImpl
import com.example.projectandroid2.ui.activities.main.MainActivity
import com.example.projectandroid2.ui.elements.TestTagInfoElementLocation
import com.example.projectandroid2.ui.screens.addplace.AddPlaceScreen
import com.example.projectandroid2.ui.screens.addplace.TestTagButtonForAddPlace
import com.example.projectandroid2.ui.screens.addplace.TestTagEmptyLocationOrName
import com.example.projectandroid2.ui.screens.addplace.TestTagTextFieldNameForAddPlace
import com.example.projectandroid2.ui.screens.detail.DetailScreen
import com.example.projectandroid2.ui.screens.listofcountries.TestTagListOfCountriesLazyList
import com.example.projectandroid2.ui.screens.mapforadd.TestTagButtonSaveLocationEnabled
import com.example.projectandroid2.ui.screens.places.TestTagButtonAddPlace
import com.example.projectandroid2.utils.JSONUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.concurrent.thread

@HiltAndroidTest
class AddPlaceUiTests {
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
    fun test_add_place_with_empty_name_and_empty_location(){
        launchCountryScreen()
        with(composeRule){
            // way to add screen
            waitUntilExactlyOneExists(hasTestTag(TestTagListOfCountriesLazyList), timeoutMillis = 50000)
            onNodeWithTag(TestTagListOfCountriesLazyList)
                .performScrollToNode(hasText(CountriesMock.spain.name!!)).assertExists()

            onNode(hasText(CountriesMock.spain.name!!))
                .performClick()
            onNodeWithTag(TestTagButtonAddPlace)
                .assertIsDisplayed()
                .performClick()

            //save
            onNodeWithTag(TestTagButtonForAddPlace)
                .assertIsDisplayed()
                .performClick()
            onNodeWithTag(TestTagEmptyLocationOrName)
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test_add_place_with_empty_name(){
        launchCountryScreen()
        with(composeRule){
            // way to add screen
            waitUntilExactlyOneExists(hasTestTag(TestTagListOfCountriesLazyList), timeoutMillis = 50000)
            onNodeWithTag(TestTagListOfCountriesLazyList)
                .performScrollToNode(hasText(CountriesMock.spain.name!!)).assertExists()

            onNode(hasText(CountriesMock.spain.name!!))
                .performClick()
            onNodeWithTag(TestTagButtonAddPlace)
                .assertIsDisplayed()
                .performClick()
            onNodeWithTag(TestTagButtonForAddPlace)
                .assertIsDisplayed()

            //add location
            onNodeWithTag(TestTagInfoElementLocation)
                .assertIsDisplayed()
                .performClick()
            onNodeWithTag(TestTagButtonSaveLocationEnabled)
                .assertIsDisplayed()
                .performClick()
            waitUntilExactlyOneExists(hasTestTag(TestTagButtonForAddPlace), timeoutMillis = 5000)

            //save
            onNodeWithTag(TestTagButtonForAddPlace)
                .assertIsDisplayed()
                .performClick()
            onNodeWithTag(TestTagEmptyLocationOrName)
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test_add_place_with_empty_location(){
        launchCountryScreen()
        with(composeRule){
            // way to add screen
            waitUntilExactlyOneExists(hasTestTag(TestTagListOfCountriesLazyList), timeoutMillis = 50000)
            onNodeWithTag(TestTagListOfCountriesLazyList)
                .performScrollToNode(hasText(CountriesMock.spain.name!!)).assertExists()

            onNode(hasText(CountriesMock.spain.name!!))
                .performClick()
            onNodeWithTag(TestTagButtonAddPlace)
                .assertIsDisplayed()
                .performClick()

            //add name
            onNodeWithTag(TestTagTextFieldNameForAddPlace)
                .assertIsDisplayed()
                .performTextInput("Madrid")

            //save
            onNodeWithTag(TestTagButtonForAddPlace)
                .assertIsDisplayed()
                .performClick()
            onNodeWithTag(TestTagEmptyLocationOrName)
                .assertIsDisplayed()

        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test_add_place_with_location_and_name(){
        launchCountryScreen()
        with(composeRule){
            // way to add screen
            waitUntilExactlyOneExists(hasTestTag(TestTagListOfCountriesLazyList), timeoutMillis = 50000)
            onNodeWithTag(TestTagListOfCountriesLazyList)
                .performScrollToNode(hasText(CountriesMock.spain.name!!)).assertExists()

            onNode(hasText(CountriesMock.spain.name!!))
                .performClick()
            onNodeWithTag(TestTagButtonAddPlace)
                .assertIsDisplayed()
                .performClick()
            //add name
            onNodeWithTag(TestTagTextFieldNameForAddPlace)
                .assertIsDisplayed()
                .performTextInput("Madrid")

            //add location
            onNodeWithTag(TestTagInfoElementLocation)
                .assertIsDisplayed()
                .performClick()


            onNodeWithTag(TestTagButtonSaveLocationEnabled)
                .assertIsDisplayed()
                .performClick()

            waitUntilExactlyOneExists(hasTestTag(TestTagButtonForAddPlace), timeoutMillis = 5000)
            //save
            onNodeWithTag(TestTagButtonForAddPlace)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagButtonAddPlace)
                .assertIsDisplayed()

        }
    }

    private fun launchCountryScreen(){
        composeRule.activity.setContent{
            MaterialTheme {
                NavGraph(startDestination = Destination.CountryListScreen.route)
            }
        }
    }
}
