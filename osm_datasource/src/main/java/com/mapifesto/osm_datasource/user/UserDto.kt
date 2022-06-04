package com.mapifesto.osm_datasource.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(

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

    @SerialName("user")
    val user: UserDetailsDto,

    ) {
    @Serializable
    data class UserDetailsDto(

        @SerialName("id")
        val id: Long,

        @SerialName("display_name")
        val displayName: String,

        @SerialName("account_created")
        val accountCreated: String,

        @SerialName("description")
        val description: String,

        @SerialName("contributor_terms")
        val contributorTerms: ContributorTermsDto,

        @SerialName("img")
        val img: ImgDto,

        @SerialName("roles")
        val roles: List<String>,

        @SerialName("changesets")
        val changesets: ChangesetsDto,

        @SerialName("traces")
        val traces: TracesDto,

        @SerialName("blocks")
        val blocks: BlocksDto,

        @SerialName("home")
        val home: HomeDto,

        @SerialName("languages")
        val languages: List<String>,

        @SerialName("messages")
        val messages: MessagesDto,

        ) {
        @Serializable
        data class ContributorTermsDto(
            @SerialName("agreed")
            val agreed: Boolean,

            @SerialName("pd")
            val pd: Boolean,
        )

        @Serializable
        data class ImgDto(
            @SerialName("href")
            val href: String,
        )

        @Serializable
        data class ChangesetsDto(
            @SerialName("count")
            val count: Int,
        )

        @Serializable
        data class TracesDto(
            @SerialName("count")
            val count: Int,
        )

        @Serializable
        data class BlocksDto(

            @SerialName("received")
            val received: ReceivedDto,

            ) {

            @Serializable
            data class ReceivedDto(

                @SerialName("count")
                val count: Int,

                @SerialName("active")
                val active: Int,
            )

        }

        @Serializable
        data class HomeDto(
            @SerialName("lat")
            val lat: Double,

            @SerialName("lon")
            val lon: Double,

            @SerialName("zoom")
            val zoom: Int,
        )

        @Serializable
        data class MessagesDto(

            @SerialName("received")
            val received: ReceivedDto,

            @SerialName("sent")
            val sent: SentDto,
        ) {

            @Serializable
            data class ReceivedDto(
                @SerialName("count")
                val count: Int,

                @SerialName("unread")
                val unread: Int,
            )

            @Serializable
            data class SentDto(
                @SerialName("count")
                val count: Int,
            )

        }


    }
}

