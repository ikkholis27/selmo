package com.example.selmo.model

import com.google.gson.annotations.SerializedName

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.internal.*

@Serializable
data class Salmo (
    @SerializedName("sensor")
    val sensor: List<Sensor> = ArrayList(),

    @SerialName("created_at")
    val createdAt: String? = null
)
