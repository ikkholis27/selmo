package com.example.selmo.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.internal.*

@Serializable
data class Login
    (
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("nohp")
    var nohp: String? = null,

    @SerializedName("api_token")
    var apiToken: String? = null,

    @SerializedName("status")
    var status: String? = null
)