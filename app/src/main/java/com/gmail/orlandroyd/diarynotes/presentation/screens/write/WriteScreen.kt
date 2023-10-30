package com.gmail.orlandroyd.diarynotes.presentation.screens.write

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.gmail.orlandroyd.diarynotes.model.Diary
import com.gmail.orlandroyd.diarynotes.model.Mood
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
    uiState: UiState,
    pagerState: PagerState,
    moodName: () -> String,
    onBackPressed: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSaveClicked: (Diary) -> Unit,
    onDateTimeUpdated: (ZonedDateTime) -> Unit
) {
    LaunchedEffect(uiState.mood) {
        pagerState.scrollToPage(Mood.valueOf(uiState.mood.name).ordinal)
    }
    Scaffold(
        topBar = {
            WriteTopBar(
                moodName = moodName,
                onBackPressed = onBackPressed,
                onDeleteConfirmed = onDeleteConfirmed,
                onDateTimeUpdated = onDateTimeUpdated,
                uiState = uiState
            )
        },
        content = {
            WriteContent(
                uiState = uiState,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange,
                paddingValues = it,
                pagerState = pagerState,
                onSaveClicked = onSaveClicked
            )
        }
    )
}