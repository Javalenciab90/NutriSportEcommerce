package com.nutrisportclone.home

import ContentWithMessageBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nutrisportclone.home.components.BottomBar
import com.nutrisportclone.home.components.CustomDrawer
import com.nutrisportclone.home.domain.BottomBarDestination
import com.nutrisportclone.home.domain.CustomDrawerState
import com.nutrisportclone.home.domain.isOpened
import com.nutrisportclone.home.domain.opposite
import com.nutrisportclone.shared.navigation.Screen
import com.nutrisportclone.shared.ui.Alpha
import com.nutrisportclone.shared.ui.BebasNeueFont
import com.nutrisportclone.shared.ui.FontSize
import com.nutrisportclone.shared.ui.IconPrimary
import com.nutrisportclone.shared.ui.Resources
import com.nutrisportclone.shared.ui.Surface
import com.nutrisportclone.shared.ui.SurfaceLighter
import com.nutrisportclone.shared.ui.TextPrimary
import com.nutrisportclone.shared.util.getScreenWidth
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphScreen(
    navigateToAuth: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToAdminPanel: () -> Unit
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestination.ProductsOverview.toString()) -> BottomBarDestination.ProductsOverview
                route.contains(BottomBarDestination.Cart.toString()) -> BottomBarDestination.Cart
                route.contains(BottomBarDestination.Categories.toString()) -> BottomBarDestination.Categories
                else -> BottomBarDestination.ProductsOverview
            }
        }
    }

    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }

    val offsetValue by remember { derivedStateOf { (screenWidth / 1.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
    )

    val animatedBackground by animateColorAsState(
        targetValue = if (drawerState.isOpened()) SurfaceLighter else Surface,
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
    )

    val animatedRadius by animateDpAsState(
        targetValue = if (drawerState.isOpened()) 20.dp else 0.dp
    )

    val viewModel = koinViewModel<HomeGraphViewModel>()
    val messageBarState = rememberMessageBarState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedBackground)
            .systemBarsPadding()
    ) {
        CustomDrawer(
            onProfileClick = {
                navigateToProfile()
            },
            onContactUsClick = { /* Handle contact us click */ },
            onSignOutClick = {
                viewModel.signOut(
                    onSuccess = {
                        navigateToAuth()
                    },
                    onError = { errorMessage ->
                        messageBarState.addError(errorMessage)
                    }
                )
            },
            onAdminPanelClick = {
                navigateToAdminPanel()
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(size = animatedRadius))
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(animatedRadius),
                    ambientColor = Color.Black.copy(alpha = Alpha.DISABLED),
                    spotColor = Color.Black.copy(alpha = Alpha.DISABLED)
                )
        ) {
            Scaffold(
                containerColor = Surface,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            AnimatedContent(
                                targetState = selectedDestination) { destination ->
                                Text(
                                    text = destination.title,
                                    fontFamily = BebasNeueFont(),
                                    fontSize = FontSize.LARGE,
                                    color = TextPrimary,
                                )
                            }
                        },
                        navigationIcon = {
                           AnimatedContent(
                               targetState = drawerState
                           ) { drawer ->
                               if (drawer.isOpened()) {
                                   IconButton(onClick = {
                                       drawerState = drawerState.opposite()
                                   }) {
                                       Icon(
                                           painter = painterResource(Resources.Icon.Close),
                                           contentDescription = "Close Icon",
                                           tint = IconPrimary
                                       )
                                   }
                               } else {
                                   IconButton(onClick = {
                                       drawerState = drawerState.opposite()
                                   }) {
                                       Icon(
                                           painter = painterResource(Resources.Icon.Menu),
                                           contentDescription = "Menu Icon",
                                           tint = IconPrimary
                                       )
                                   }
                               }
                           }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Surface,
                            scrolledContainerColor = Surface,
                            navigationIconContentColor = IconPrimary,
                            titleContentColor = TextPrimary,
                            actionIconContentColor = IconPrimary
                        )
                    )
                }
            ) { paddingValues ->
                ContentWithMessageBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        ),
                    messageBarState = messageBarState,
                    errorMaxLines = 2,
                    contentBackgroundColor = Surface
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            startDestination = Screen.ProductsOverview
                        ) {
                            composable<Screen.ProductsOverview> {
                                Text("Products Overview")
                            }
                            composable<Screen.Cart> {
                                Text("Cart")
                            }
                            composable<Screen.Categories> {
                                Text("Categories")
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier.padding(all = 12.dp)
                        ) {
                            BottomBar(
                                selected = selectedDestination,
                                onSelected = { destination ->
                                    navController.navigate(destination.screen) {
                                        launchSingleTop = true
                                        popUpTo<Screen.ProductsOverview> {
                                            saveState = true
                                            inclusive = false
                                        }
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}