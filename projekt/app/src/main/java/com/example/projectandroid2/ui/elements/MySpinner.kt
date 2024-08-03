package com.example.projectandroid2.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projectandroid2.R
import com.example.projectandroid2.ui.theme.basicMargin


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySpinner(
    onValueChange: (String)-> Unit,
    options: List<String>,

){
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember {
        mutableStateOf(options[0])
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .padding()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                ) },

            )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },

        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        when(selectionOption){
                            "City"-> {
                                MenuItem(R.drawable.city, R.string.city)
                            }
                            "Historical Building"-> {
                                MenuItem(
                                    R.drawable.historical_building,
                                    R.string.historicalBuilding
                                )
                            }
                            "Forest"-> {
                                MenuItem(R.drawable.forest, R.string.forest)
                            }
                            "Mountains"-> {
                                MenuItem(R.drawable.landscape, R.string.mountains)
                              }
                            "Beach"-> {
                                MenuItem(R.drawable.beach, R.string.beach)
                                }
                            "Very satisfied" -> {
                                MenuItem(R.drawable.very_satisfied, R.string.verySatisfied)
                            }
                            "Satisfied" -> {
                                MenuItem(R.drawable.satisfied, R.string.satisfied)
                            }
                            "Neutral" -> {
                                MenuItem(R.drawable.neutral, R.string.neutral)
                            }
                            "Dissatisfied" -> {
                                MenuItem(R.drawable.dissatisfied, R.string.dissatisfied)
                            }
                            "Very dissatisfied" -> {
                                MenuItem(R.drawable.very_dissatisfied, R.string.veryDissatisfied)
                            }
                            else -> {Text(selectionOption, modifier = Modifier.padding(5.dp))}
                        } },
                    onClick = {
                        selectedOptionText = selectionOption
                        onValueChange(selectedOptionText)
                        expanded = false
                    }, modifier = Modifier
                        .exposedDropdownSize()
                )
            }
        }
    }


}

@Composable
fun MenuItem(myPainterResource: Int, myStringResource:Int){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Icon(painter = painterResource(id = myPainterResource), null, modifier = Modifier.padding(10.dp))
        Text(text = stringResource(id = myStringResource))
    }
}
