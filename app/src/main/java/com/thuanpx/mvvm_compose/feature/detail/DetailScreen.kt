package com.thuanpx.mvvm_compose.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.thuanpx.mvvm_compose.model.entity.Pokemon

/**
 * Created by ThuanPx on 24/04/2023.
 */
@Composable
fun DetailRoute(
    pokemon: Pokemon?
) {
    DetailScreen(pokemon = pokemon)
}

@Composable
private fun DetailScreen(
    pokemon: Pokemon?
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = pokemon?.getImageUrl(),
            contentDescription = "",
        )
    }
}