package com.example.cryptoexample

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val userName: String? = null,
    val password: String? = null

)
