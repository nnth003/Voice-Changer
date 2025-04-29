package com.pixelzlab.app.feature.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pixelzlab.app.core.navigation.AppDestination
import com.pixelzlab.app.designsystem.component.AppOverlayLoadingWheel
import com.pixelzlab.app.model.entity.Pokemon
import com.pixelzlab.app.utils.extension.collectAsEffect

/**
 * Created by pixelzlab on 02/01/2023.
 */

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: AppDestination) -> Unit
) {
    // TODO request post notification
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val pokemons by viewModel.pokemons.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    viewModel.navigator.collectAsEffect {
        navigator(it)
    }
    error?.let {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
    }

    HomeScreen(
        pokemons = pokemons,
        isLoading = isLoading,
        onItemClick = { navigator(AppDestination.Detail.addParcel(it)) }
    )
}

@Composable
fun HomeScreen(
    pokemons: List<Pokemon>,
    isLoading: Boolean,
    onItemClick: (Pokemon) -> Unit,
) {
    Scaffold(
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.background(Color.Black)
            ) {
                items(pokemons.size) { index ->
                    ItemPokemon(
                        pokemon = pokemons[index],
                        onItemClick = onItemClick
                    )
                }
            }

            AnimatedVisibility(
                visible = isLoading,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                ) + fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    AppOverlayLoadingWheel(
                        modifier = Modifier
                            .align(Alignment.Center),
                        contentDesc = "",
                    )
                }
            }
        }
    }
}
