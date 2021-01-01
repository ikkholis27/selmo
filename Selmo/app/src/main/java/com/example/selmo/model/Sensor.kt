package com.example.selmo.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Sensor (

    @SerializedName("id")
    val id: String? = null,
    @SerializedName("idsensor")
    val idsensor: String? = null,
    @SerializedName("v")
    val v: String? = null,
    @SerializedName("i")
    val i: String? = null,
    @SerializedName("p")
    val p: String? = null,
    @SerializedName("e")
    val e: String? = null,
    @SerializedName("f")
    val f: String? = null,
    @SerializedName("pf")
    val pf: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)
