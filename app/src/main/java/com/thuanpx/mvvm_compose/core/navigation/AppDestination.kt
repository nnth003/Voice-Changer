package com.thuanpx.mvvm_compose.core.navigation

import androidx.navigation.*
import com.thuanpx.mvvm_compose.model.entity.Pokemon

const val KeyId = "id"
const val KeyPokemon = "KeyPokemon"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?> = "" to null

    object Splash: AppDestination("splash")

    object Up : AppDestination()

    object Home : AppDestination("home")

    object Detail: AppDestination("detail") {
        fun addParcel(value: Pokemon) = apply {
            parcelableArgument = KeyPokemon to value
        }
    }

    object Second : AppDestination("second/{$KeyId}") {

        override val arguments = listOf(
            navArgument(KeyId) { type = NavType.StringType }
        )

        fun createRoute(id: String) = apply {
            destination = "second/$id"
        }
    }
}
