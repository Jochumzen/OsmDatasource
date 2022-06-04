package com.mapifesto.osm_datasource

import com.mapifesto.osm_datasource.changeset.ChangesetsDto
import com.mapifesto.osm_datasource.node.NodeDto
import com.mapifesto.osm_datasource.user.UserDto
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*


interface OsmService {

    suspend fun queryChangeset(open: Boolean, displayName: String): ChangesetsDto?

    suspend fun createChangeset(token: String): String

    suspend fun closeChangeset(id: String, token: String)

    suspend fun getNode(): NodeDto?

    suspend fun gerUserDetails(token: String): UserDto?

    companion object Factory {
        fun build(): OsmService {
            return OsmServiceImpl(
                httpClient = HttpClient(Android) {
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

