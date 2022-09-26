package com.example.assignment

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment.components.DisplayAlertDialog
import com.example.assignment.components.DisplayProgressDialog
import com.example.assignment.components.displayListItem
import com.example.assignment.components.displayMissionDetails
import com.example.assignment.data.Item
import com.example.assignment.viewmodels.DisplayMissionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    isLoading: Boolean,
    listOfMissionDetails: Result<List<Item>>?,
    displayMissionViewModel: DisplayMissionViewModel
) {
    val configuration = LocalConfiguration.current
    val window = rememberWindowSize()
    val navController = rememberNavController()
    val openDialog = remember { mutableStateOf(true) }
    if ((window.width == WindowType.Expanded) || (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)) {
        if (isLoading) {
            DisplayProgressDialog()
        } else {
            listOfMissionDetails?.onSuccess {
                Row {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                    ) {
                        itemsIndexed(
                            items = it
                        ) { index, listItem ->
                            displayListItem(item = listItem, modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                                .background(if (displayMissionViewModel.selectedItem.value == index) Color.Gray else Color.LightGray)
                                .clickable {
                                    displayMissionViewModel.selectedItem.value = index
                                    displayMissionViewModel.selectedItemMissionDetails.value =
                                        listItem.details
                                })
                        }
                    }
                    displayMissionDetails(missionDetail = displayMissionViewModel.selectedItemMissionDetails.value)
                }
            }
            listOfMissionDetails?.onFailure {
                DisplayAlertDialog(openDialog)
            }
        }
    } else {
        NavHost(navController = navController, startDestination = "missionlist") {
            composable("missionlist") {
                if (isLoading) {
                    DisplayProgressDialog()
                } else {
                    listOfMissionDetails?.onSuccess {
                        LazyColumn {
                            itemsIndexed(
                                items = it
                            ) { _, listItem ->
                                displayListItem(item = listItem, modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "listItem", listItem.details
                                        )
                                        navController.navigate("details")
                                    }
                                )
                            }
                        }
                    }
                    listOfMissionDetails?.onFailure {
                        DisplayAlertDialog(openDialog)
                    }
                }
            }

            composable("details") {
                val details =
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("listItem")
                displayMissionDetails(missionDetail = details)
            }
        }
    }
}