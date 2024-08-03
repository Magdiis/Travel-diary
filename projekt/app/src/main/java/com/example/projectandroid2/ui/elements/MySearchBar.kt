package com.example.projectandroid2.ui.elements

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.projectandroid2.R
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.utils.returnStringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    countries: List<Country?>?,
    navigation: INavigationRouter
){
    val focusManager = LocalFocusManager.current
    var text by remember {
        mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = text ,
        onQueryChange ={
            text = it
        } ,
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text(stringResource(R.string.placeholderSearchBar)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = { if(active){
            IconButton(onClick = { active = false; text=""; focusManager.clearFocus()})
            { Icon(Icons.Default.Clear,contentDescription = null) } }
        }) {
        var find = countries!!.filter { country ->
            stringResource(id = returnStringResource(country!!.name!!)).uppercase().contains(text.uppercase())
        }
        LazyColumn {
            find.forEach{
                item{
                    CountryRow(country = it!!, navigation = navigation)
                }
            }
        }

    }
}
