package com.thuanpx.mvvm_compose.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thuanpx.mvvm_compose.model.entity.Pokemon

/**
 * Created by ThuanPx on 23/04/2023.
 */

@Composable
fun ItemPokemon(
    pokemon: Pokemon,
    onItemClick: (Pokemon) -> Unit,
) {
    Card(
        onClick = { onItemClick(pokemon) },
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        AsyncImage(
            model = pokemon.getImageUrl(),
            contentDescription = null,
        )
    }
}