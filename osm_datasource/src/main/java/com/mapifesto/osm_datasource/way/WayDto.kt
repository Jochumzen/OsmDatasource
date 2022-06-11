package com.mapifesto.osm_datasource.way

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WayDto(

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

        @SerialName("nodes")
        val nodes: List<Long>,

        @SerialName("tags")
        val tags: Map<String,String>,
    )

}

