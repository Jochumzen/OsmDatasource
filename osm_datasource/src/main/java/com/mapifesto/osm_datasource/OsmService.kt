package com.mapifesto.osm_datasource

import com.mapifesto.domain.Bobo
import com.mapifesto.osm_datasource.changeset.ChangesetsDto
import com.mapifesto.osm_datasource.node.NodeDto
import com.mapifesto.osm_datasource.relation.RelationDto
import com.mapifesto.osm_datasource.user.UserDto
import com.mapifesto.osm_datasource.way.WayDto
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.statement.*


interface OsmService {

    suspend fun createChangeset(token: String, bodyPut: String): String

    suspend fun closeChangeset(token: String, id: String)

    suspend fun queryChangeset(open: Boolean, displayName: String): ChangesetsDto?

    suspend fun gerUserDetails(token: String): UserDto?

    suspend fun getNodeById(id: String): NodeDto?

    suspend fun updateNode(token: String, id: String, bodyPut: String): String?

    suspend fun createNode(token: String, bodyPut: String): String?

    suspend fun getWayById(id: String): WayDto?

    suspend fun updateWay(token: String, id: String, bodyPut: String): String?

    suspend fun createWay(token: String, bodyPut: String): String?

    suspend fun getRelationById(id: String): RelationDto?

    suspend fun updateRelation(token: String, id: String, bodyPut: String): String?

    suspend fun createRelation(token: String, bodyPut: String): String?

    suspend fun createNote(token: String, bobo: Bobo): HttpResponse?

    companion object Factory {
        fun build(): OsmService {
            return OsmServiceImpl(
                httpClient = HttpClient(Android) {
/*                    HttpResponseValidator {
                        validateResponse { response: HttpResponse ->
                            val statusCode = response.status.value
                            if (statusCode == 404)
                                throw ServerResponseException(response)
                        }
                    }*/
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = false
                                isLenient = false
                            }
                        )
                    }
                    //install(HttpTimeout)
                }
            )
        }
    }
}


/*interface XxxService {

    suspend fun addMom(id: Int, age: Int): String

    companion object Factory {
        fun build(): XxxService {
            return XxxServiceImpl(
                httpClient = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = false
                                isLenient = false
                            }
                        )
                    }
                }
            )
        }
    }
}

suspend fun method1(var1: Boolean, var2: String): ReturnType1Dto?

suspend fun method2(): String

suspend fun method3(var1: Boolean): List<ReturnType1Dto>

data class ReturnType1Dto(
    val x: Int
)*/

