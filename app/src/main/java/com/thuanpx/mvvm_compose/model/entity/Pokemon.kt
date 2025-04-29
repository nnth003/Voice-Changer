package com.thuanpx.mvvm_compose.model.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * Created by ThuanPx on 23/04/2023.
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Pokemon(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "url") val url: String? = null
) : Parcelable {
    fun getImageUrl(): String {
        val index = url?.split("/".toRegex())?.dropLast(1)?.last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
    }
}