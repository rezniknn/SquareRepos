package com.alexeyreznik.squarerepos.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repo(
    val id: Long,
    val name: String,
    @SerializedName("stargazers_count") val stars: Long
) : Serializable
