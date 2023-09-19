package com.gmail.orlandroyd.diarynotes.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gmail.orlandroyd.diarynotes.R
import com.gmail.orlandroyd.diarynotes.presentation.components.DisplayAlertDialog
import com.gmail.orlandroyd.diarynotes.presentation.screens.auth.AuthenticationScreen
import com.gmail.orlandroyd.diarynotes.presentation.screens.auth.AuthenticationViewModel
import com.gmail.orlandroyd.diarynotes.presentation.screens.home.HomeScreen
import com.gmail.orlandroyd.diarynotes.util.Constants.APP_ID
import com.gmail.orlandroyd.diarynotes.util.Constants.WRITE_SCREEN_ARGUMENT_ID
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
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
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit,
) {
    composable(route = Screen.Home.route) {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var isSignOutDialogOpened by rememberSaveable {
            mutableStateOf(false)
        }

        HomeScreen(
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSignOutClicked = { isSignOutDialogOpened = true },
            navigateToWrite = navigateToWrite,
        )

        DisplayAlertDialog(
            title = stringResource(R.string.sign_out),
            message = stringResource(R.string.are_you_sure_you_want_to_sign_out_from_your_google_account),
            dialogOpened = isSignOutDialogOpened,
            onDialogClosed = { isSignOutDialogOpened = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser
                    if (user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }
                }
            })

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