package com.example.assignment.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.assignment.R

@Composable
fun DisplayProgressDialog() {
    Row(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.purple_200),
        )
    }
}