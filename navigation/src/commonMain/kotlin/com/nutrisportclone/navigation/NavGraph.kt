package com.nutrisportclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nutrisportclone.auth.AuthScreen
import com.nutrisportclone.home.HomeGraphScreen
import com.nutrisportclone.profile.ui.ProfileScreen
import com.nutrisportclone.shared.navigation.Screen

@Composable
fun SetupNavigationGraph(
    startDestination: Screen = Screen.Auth
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Auth
    ) {
        composable<Screen.Auth> {
            AuthScreen {
                navController.navigate(Screen.HomeGraph) {
                    popUpTo<Screen.Auth> { inclusive = true }
                }
            }
        }

        composable<Screen.HomeGraph> {
            HomeGraphScreen(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo<Screen.HomeGraph> { inclusive = true }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                }
            )
        }

        composable<Screen.Profile> {
            ProfileScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}