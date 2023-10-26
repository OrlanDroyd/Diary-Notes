package com.gmail.orlandroyd.diarynotes.presentation.screens.write

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.gmail.orlandroyd.diarynotes.model.Diary
import com.gmail.orlandroyd.diarynotes.model.Mood
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
    uiState: UiState,
    pagerState: PagerState,
    selectedDiary: Diary?,
    onBackPressed: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    LaunchedEffect(uiState.mood) {
        pagerState.scrollToPage(Mood.valueOf(uiState.mood.name).ordinal)
    }
    Scaffold(
        topBar = {
            WriteTopBar(
                selectedDiary = selectedDiary,
                onBackPressed = onBackPressed,
                onDeleteConfirmed = onDeleteConfirmed
            )
        },
        content = {
            WriteContent(
                title = uiState.title,
                onTitleChange = onTitleChange,
                description = uiState.description,
                onDescriptionChange = onDescriptionChange,
                paddingValues = it,
                pagerState = pagerState
            )
        }
    )
}