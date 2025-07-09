package com.pixelzlab.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pixelzlab.app.feature.detail.navigation.detailScreen
import com.pixelzlab.app.feature.detail.navigation.navigateToDetail
import com.pixelzlab.app.feature.home.navigation.homeScreen
import com.pixelzlab.app.feature.home.navigation.navigateToHome
import com.pixelzlab.app.feature.splash.navigation.splashRoute
import com.pixelzlab.app.feature.splash.navigation.splashScreen
import com.pixelzlab.app.model.entity.Pokemon

/**
 * App navigation graph using type-safe navigation with nested routes
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = splashRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        splashScreen(
            onSplashFinished = { navController.navigateToHome() }
        )
        
        homeScreen(
            onPokemonClick = { pokemon -> navController.navigateToDetail(pokemon) }
        )
        
        detailScreen(
            onBackClick = { navController.navigateUp() }
        )
    }
} 