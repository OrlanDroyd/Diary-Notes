package com.gmail.orlandroyd.diarynotes.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.orlandroyd.diarynotes.util.Constants.APP_ID
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.launch

@Composable
fun HomeContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val scope = rememberCoroutineScope()
        Text(text = "HOME")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                scope.launch {
                    App.create(APP_ID).currentUser?.logOut()
                }
            },
            content = { Text(text = "Logout") }
        )
    }
}