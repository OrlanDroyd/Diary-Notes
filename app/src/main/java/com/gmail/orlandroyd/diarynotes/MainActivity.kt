package com.gmail.orlandroyd.diarynotes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.gmail.orlandroyd.diarynotes.navigation.Screen
import com.gmail.orlandroyd.diarynotes.navigation.SetupNavGraph
import com.gmail.orlandroyd.diarynotes.ui.theme.DiaryNotesTheme
import com.gmail.orlandroyd.diarynotes.util.Constants.APP_ID
import com.google.firebase.FirebaseApp
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {

    private var keepSplashOpen = true

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpen
        }
        FirebaseApp.initializeApp(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DiaryNotesTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController,
                    onDataLoaded = { keepSplashOpen = false }
                )
            }
        }
    }

    private fun getStartDestination(): String {
        val user = App.create(APP_ID).currentUser
        return if (user != null && user.loggedIn) Screen.Home.route
        else Screen.Authentication.route
    }

}