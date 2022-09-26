package com.example.assignment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.assignment.R
import com.example.assignment.data.Item
import com.example.assignment.util.loadPicture


@Composable
fun displayListItem(
    item: Item,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
        ) {
            item.links.mission_patch_small?.let { url ->
                val image =
                    loadPicture(url = url, defaultImage = R.drawable.ic_launcher_foreground).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 2.dp)
                            .clip(RectangleShape),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Column {
                Text(
                    text = stringResource(id = R.string.mission_name, item.mission_name),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = stringResource(id = R.string.rocket_name, item.rocket.rocket_name),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = stringResource(
                        id = R.string.launch_site_name,
                        item.launch_site.site_name
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = stringResource(id = R.string.launch_date, item.launch_date_local),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}


@Composable
fun displayMissionDetails(
    missionDetail: String?
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        elevation = 8.dp,
    ) {
        missionDetail?.let { missionDetail ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = missionDetail,
                    modifier = Modifier
                        .align(alignment = Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}