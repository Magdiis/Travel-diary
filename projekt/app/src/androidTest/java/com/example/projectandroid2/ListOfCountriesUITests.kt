package com.example.projectandroid2

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.IdlingResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.projectandroid2.mock.CountriesMock
import com.example.projectandroid2.mock.PlaceMock
import com.example.projectandroid2.navigation.Destination
import com.example.projectandroid2.navigation.NavGraph
import com.example.projectandroid2.ui.activities.main.MainActivity
import com.example.projectandroid2.ui.elements.TestTagInfoElementLocation
import com.example.projectandroid2.ui.screens.addplace.TestTagButtonForAddPlace
import com.example.projectandroid2.ui.screens.listofcountries.TestTagListOfCountriesLazyList
import com.example.projectandroid2.ui.screens.mapforadd.TestTagButtonSaveLocationEnabled
import com.example.projectandroid2.ui.screens.places.TestTagButtonAddPlace
import com.example.projectandroid2.ui.screens.places.TestTagListOfPLaces
import com.google.firestore.v1.StructuredAggregationQuery.Aggregation.Count
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ListOfCountriesUITests {
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
    fun test_mocked_countries_are_shown(){
        launchCountryScreen()
        with(composeRule){
            waitUntilExactlyOneExists(hasTestTag(TestTagListOfCountriesLazyList), timeoutMillis = 50000)
            onNodeWithTag(TestTagListOfCountriesLazyList)
                .performScrollToNode(hasText(CountriesMock.spain.name!!)).assertExists()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test_show_countries_and_navigate_where_places_are_empty(){
        launchCountryScreen()
        with(composeRule){
            waitUntilExactlyOneExists(hasTestTag(TestTagListOfCountriesLazyList), timeoutMillis = 50000)
            onNodeWithTag(TestTagListOfCountriesLazyList)
                .performScrollToNode(hasText(CountriesMock.spain.name!!)).assertExists()

            onNode(hasText(CountriesMock.spain.name!!))
                .performClick()
            onNodeWithTag(TestTagButtonAddPlace).assertIsDisplayed()
            Thread.sleep(3000)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test_show_countries_and_navigate_where_places_are_not_empty(){
        launchCountryScreen()
        with(composeRule) {
            waitUntilExactlyOneExists(
                hasTestTag(TestTagListOfCountriesLazyList),
                timeoutMillis = 50000
            )
            onNodeWithTag(TestTagListOfCountriesLazyList)
                .performScrollToNode(hasText(CountriesMock.czechia.name!!)).assertExists()

            onNode(hasText(CountriesMock.czechia.name!!))
                .performClick()
            onNodeWithTag(TestTagListOfPLaces).assertIsDisplayed()
            Thread.sleep(3000)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun launchCountryScreen(){
        composeRule.activity.setContent{
            MaterialTheme {
                NavGraph(startDestination = Destination.CountryListScreen.route)
            }
        }
    }
}