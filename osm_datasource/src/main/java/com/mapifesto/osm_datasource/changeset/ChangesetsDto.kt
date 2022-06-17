package com.mapifesto.osm_datasource.changeset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangesetsDto(
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

    @SerialName("changesets")
    val changesets: List<ChangeSetDto>,

    ) {
    @Serializable
    data class ChangeSetDto(

        @SerialName("id")
        val id: Long,

        @SerialName("created_at")
        val createdAt: String,

        @SerialName("open")
        val open: Boolean,

        @SerialName("comments_count")
        val commentsCount: Int,

        @SerialName("changes_count")
        val changesCount: Int,

        @SerialName("min_lat")
        val MinLat: Double? = null,

        @SerialName("min_lon")
        val MinLon: Double? = null,

        @SerialName("max_lat")
        val MaxLat: Double? = null,

        @SerialName("max_lon")
        val MaxLon: Double? = null,

        @SerialName("closed_at")
        val closedAt: String? = null,

        @SerialName("uid")
        val uid: Long,

        @SerialName("user")
        val user: String,

        @SerialName("tags")
        val tags: TagsDto,

        ) {

        @Serializable
        data class TagsDto(

            @SerialName("comment")
            val comment: String,

            @SerialName("source")
            val source: String? = null,

            @SerialName("created_by")
            val createdBy: String,
        )

    }
}



/*@Serializable
data class YyyDto(

    @SerialName("greeting_phrase")
    val greetingPhrase: String,

    @SerialName("number_of_cases")
    val numberOfCases : Int,
)*/

/*@Serializable
data class YyyDto(

    @SerialName("phrase")
    val phrase: String,

    @SerialName("number_of_cases")
    val person : PersonDto,
) {

    @Serializable
    data class PersonDto(

        @SerialName("name")
        val name: String,

        @SerialName("friend")
        val friend : Boolean,
    )
}*/



/*@Serializable
data class DataDto(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name : String,
)*/

/*@Serializable
data class YyyDto(

    @SerialName("phrase")
    val phrase: String,

    @SerialName("data")
    val data : List<DataDto>,
) {

    @Serializable
    data class DataDto(

        @SerialName("id")
        val id: Int,

        @SerialName("name")
        val name : String,
    )

}*/
/*
@Serializable
data class YyyDto(

    @SerialName("phrase")
    val phrase: String,

    @SerialName("data")
    val data : List<String>,
)
*/

/*@Serializable
data class YyyDto(

    @SerialName("phrase")
    val phrase: String,

    @SerialName("data")
    val data : Map<String,String>,
)*/



/*@Serializable
data class YyyDto(

    @SerialName("phrase")
    val phrase: String,

    @SerialName("non_mandatory_age")
    val age : Int? = null,
)*/

/*@Serializable
data class YyyDto(

    @SerialName("phrase")
    val phrase: String,

    @SerialName("cases")
    val cases : Int,

)*/



