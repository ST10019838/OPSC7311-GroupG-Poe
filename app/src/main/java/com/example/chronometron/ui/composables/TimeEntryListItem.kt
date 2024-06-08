package com.example.chronometron.ui.composables

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Unarchive
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.chronometron.api.archiveEntry
import com.example.chronometron.api.deleteTimeEntry
import com.example.chronometron.api.unarchiveEntry
import com.example.chronometron.api.updateTimeEntry
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.ui.composables.camera.ImagePreview
import com.example.chronometron.ui.viewModels.SessionState
import com.example.chronometron.utils.StringtoBitmap
import de.charlex.compose.RevealSwipe
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TimeEntryListItem(entry: TimeEntry, onClick: () -> Unit = {}) {
    var isOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isRemoved by remember { mutableStateOf(false) }
    var isDeleted by remember { mutableStateOf(false) }
    var shouldArchive by remember { mutableStateOf(entry.isArchived) }
    var bleh by remember { mutableStateOf("bleh") }


    // The following code was adapted from Youtube
    // Author: Philipp Lackner
    // Link: https://www.youtube.com/watch?v=IlI6GgC_j78
    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(500)

            if (isDeleted) {
                deleteTimeEntry(entry!!)
            } else if (shouldArchive) {
                archiveEntry(entry)
            } else {
                unarchiveEntry(entry)
            }
        }
    }


    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 500),
            shrinkTowards = Alignment.Top
        ) + fadeOut(
            animationSpec = tween(durationMillis = 500)
        )
    ) {
        RevealSwipe(
            coroutineScope = scope,
            closeOnBackgroundClick = true,
            modifier = Modifier.padding(vertical = 5.dp),
            hiddenContentStart = {
                Icon(
                    modifier = Modifier.padding(horizontal = 25.dp),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete"
                )
            },
            backgroundCardStartColor = MaterialTheme.colorScheme.error,
            hiddenContentEnd = {
                Icon(
                    modifier = Modifier.padding(horizontal = 25.dp),
                    imageVector = if (entry.isArchived) Icons.Outlined.Unarchive else Icons.Outlined.Archive,
                    contentDescription = "Archive",
                    tint = Color.White
                )
            },
            onBackgroundStartClick = {
                // The following code was adapted from Medium
                // Author: Sagar Malhotra
                // Link: https://proandroiddev.com/effect-handlers-in-jetpack-compose-a-complete-guide-e9a820d20734
                scope.launch {
                    delay(250)
                    isDeleteDialogOpen = true
                }

                true
            },
            onBackgroundEndClick = {
                scope.launch {
                    delay(250)
                    shouldArchive = !shouldArchive
                    isRemoved = true
                }

                true
            },
            backgroundCardEndColor = MaterialTheme.colorScheme.primary,
            backgroundStartActionLabel = "Delete",
            backgroundEndActionLabel = "Archive",
        ) {


            OutlinedCard(onClick = onClick) {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth(),
                ) {
                    if (entry.photograph != null) {
                        EntryImagePreview(StringtoBitmap(entry.photograph!!))

                        VerticalDivider(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
                                .fillMaxHeight(0.5f)
                                .align(Alignment.CenterVertically),
                            thickness = 1.dp
                        )
                    }


                    EntryInformation(entry = entry, modifier = Modifier.weight(1f))

                    VerticalDivider(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .fillMaxHeight(0.5f)
                            .align(Alignment.CenterVertically),
                        thickness = 1.dp
                    )


                    EntryDurationDisplay(
                        entry = entry,
                        onClick = { isOpen = !isOpen })
                }

                AnimatedVisibility(visible = isOpen) {
                    EntryTimesDisplay(entry)
                }
            }
        }
    }


    if (isDeleteDialogOpen) {
        TimeEntryDeletionDialog(
            onDismiss = {
                isDeleteDialogOpen = false
            },
            onConfirm = {
                isDeleted = true
                isRemoved = true
            })
    }

}

@Composable
private fun EntryImagePreview(image: Bitmap) {
    var isImagePreviewOpen by remember { mutableStateOf(false) }

    TextButton(
        onClick = { isImagePreviewOpen = true },
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .fillMaxHeight(),
        shape = RoundedCornerShape(0)
    ) {
        Icon(
            imageVector = Icons.Default.Image, contentDescription = "View Image"
        )
    }


    if (isImagePreviewOpen) {
        ImagePreview(
            image = image.asImageBitmap(),
            onFinish = {
                isImagePreviewOpen = false
            })
    }
}

@Composable
private fun EntryInformation(entry: TimeEntry, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            entry.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(IntrinsicSize.Max),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Category,
                contentDescription = "Category Tag",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                "${entry.category.name}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun EntryDurationDisplay(
    entry: TimeEntry,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .width(IntrinsicSize.Max)
            .fillMaxHeight(),
        shape = RoundedCornerShape(0)
    ) {
        Text(
            text = "${entry.duration!!.hours}h ${entry.duration!!.minutes}m",
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun EntryTimesDisplay(entry: TimeEntry) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                Text(
                    text = "Start Time",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${entry.startTime!!.hours} : ${entry.startTime!!.minutes}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }


            Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                Text(
                    text = "End Time",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${entry.endTime!!.hours} : ${entry.endTime!!.minutes}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}