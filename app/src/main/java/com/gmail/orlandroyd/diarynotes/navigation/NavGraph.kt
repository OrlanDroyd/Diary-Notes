package com.gmail.orlandroyd.diarynotes.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gmail.orlandroyd.diarynotes.presentation.screens.auth.AuthenticationScreen
import com.gmail.orlandroyd.diarynotes.presentation.screens.auth.AuthenticationViewModel
import com.gmail.orlandroyd.diarynotes.presentation.screens.home.HomeScreen
import com.gmail.orlandroyd.diarynotes.util.Constants.WRITE_SCREEN_ARGUMENT_ID
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            }
        )
        writeRoute()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(route = Screen.Authentication.route) {

        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
//            onSuccessfulFirebaseSignIn = { tokenId ->
//                viewModel.signInWithMongoAtlas(
//                    tokenId = tokenId,
//                    onSuccess = {
//                        messageBarState.addSuccess("Successfully Authenticated!")
//                        viewModel.setLoading(false)
//                    },
//                    onError = {
//                        messageBarState.addError(it)
//                        viewModel.setLoading(false)
//                    }
//                )
//            },
//            onFailedFirebaseSignIn = {
//                messageBarState.addError(it)
//                viewModel.setLoading(false)
//            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = {
                navigateToHome()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(navigateToWrite: () -> Unit) {
    composable(route = Screen.Home.route) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        HomeScreen(
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSignOutClicked = {/*TODO*/ },
            navigateToWrite = navigateToWrite,
        )
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}