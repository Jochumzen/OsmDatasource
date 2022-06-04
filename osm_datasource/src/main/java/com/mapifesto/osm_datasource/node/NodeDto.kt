package com.mapifesto.osm_datasource.node

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NodeDto(

    @SerialName("version")
    val version: String,

    @SerialName("generator")
    val generator: String,

    @SerialName("copyright")
    val copyright: String,

    @SerialName("attribution")
    val attribution: String,

    @SerialName("license")
    val license: String,

    @SerialName("elements")
    val elements: List<ElementDto>,
) {

    @Serializable
    data class ElementDto(

        @SerialName("type")
        val type: String,

        @SerialName("id")
        val id: Long,

        @SerialName("lat")
        val lat: Double,

        @SerialName("lon")
        val lon: Double,

        @SerialName("timestamp")
        val timestamp: String,

        @SerialName("version")
        val version: Int,

        @SerialName("changeset")
        val changeset: Long,

        @SerialName("user")
        val user: String,

        @SerialName("uid")
        val uid: Long,

        @SerialName("tags")
        val tags: Map<String,String>,
    )

}

