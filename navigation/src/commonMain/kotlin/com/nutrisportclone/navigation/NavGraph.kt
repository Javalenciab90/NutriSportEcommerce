package com.nutrisportclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nutrisportclone.admin_panel.ui.AdminPanelScreen
import com.nutrisportclone.auth.AuthScreen
import com.nutrisportclone.category_search.ui.CategorySearchScreen
import com.nutrisportclone.details.ui.DetailsScreen
import com.nutrisportclone.home.HomeGraphScreen
import com.nutrisportclone.manage_product.ui.ManageProductScreen
import com.nutrisportclone.profile.ui.ProfileScreen
import com.nutrisportclone.shared.domain.models.ProductCategory
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
                },
                navigateToAdminPanel = {
                    navController.navigate(Screen.AdminPanel)
                },
                navigateToDetails = { productId ->
                    navController.navigate(Screen.Details(productId))
                },
                navigateToCategorySearch = { category ->
                    navController.navigate(Screen.CategorySearch(category))
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

        composable<Screen.AdminPanel> {
            AdminPanelScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToManageProduct = { productId ->
                    navController.navigate(Screen.ManageProduct(productId))
                }
            )
        }

        composable<Screen.ManageProduct> {
            val productId = it.toRoute<Screen.ManageProduct>().productId
            ManageProductScreen(
                productId = productId,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.Details> {
            DetailsScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.CategorySearch> {
            val category = ProductCategory.valueOf(it.toRoute<Screen.CategorySearch>().category)
            CategorySearchScreen(
                category = category,
                navigateToDetails = { productId ->
                    navController.navigate(Screen.Details(productId))
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}