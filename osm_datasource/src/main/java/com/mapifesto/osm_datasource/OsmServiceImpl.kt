package com.mapifesto.osm_datasource


import com.mapifesto.domain.Bobo
import com.mapifesto.osm_datasource.EndPoints.CLOSE_CHANGESET
import com.mapifesto.osm_datasource.EndPoints.QUERY_CHANGESETS
import com.mapifesto.osm_datasource.EndPoints.CREATE_CHANGESET
import com.mapifesto.osm_datasource.EndPoints.DETAILS
import com.mapifesto.osm_datasource.EndPoints.GET_CHANGESETS
import com.mapifesto.osm_datasource.EndPoints.NODE
import com.mapifesto.osm_datasource.EndPoints.RELATION
import com.mapifesto.osm_datasource.EndPoints.WAY
import com.mapifesto.osm_datasource.changeset.ChangesetsDto
import com.mapifesto.osm_datasource.node.NodeDto
import com.mapifesto.osm_datasource.relation.RelationDto
import com.mapifesto.osm_datasource.user.UserDto
import com.mapifesto.osm_datasource.way.WayDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class OsmServiceImpl(
    private val httpClient: HttpClient,
): OsmService {

    override suspend fun createChangeset(token: String, bodyPut: String): String {
        return httpClient.put {
            url("$CREATE_CHANGESET")
            body = bodyPut
            header(key = "Authorization", value = "Bearer $token")
        }
    }

    override suspend fun closeChangeset(token: String, id: String) {
        return httpClient.put {
            url("$CLOSE_CHANGESET/$id/close")
            header(key = "Authorization", value = "Bearer $token")
        }
    }

    override suspend fun queryChangeset(open: Boolean, displayName: String): ChangesetsDto? {
        return if(displayName == "") null else {
            httpClient.get {
                url(QUERY_CHANGESETS)
                parameter(key ="display_name", value = displayName)
                if(open)
                    parameter(key ="open", value = "true")
                header(key = "Accept", value = "application/json")
            }
        }
    }


    override suspend fun gerUserDetails(token: String): UserDto? {
        return httpClient.get {
            url(DETAILS)
            header(key = "Accept", value = "application/json")
            header(key = "Authorization", value = "Bearer $token")
        }
    }

    override suspend fun getNodeById(id: String): NodeDto? {
        return httpClient.get {
            url("$NODE/$id")
            header(key = "Accept", value = "application/json")
        }

    }

    override suspend fun updateNode(token: String, id: String, bodyPut: String): String? {
        return httpClient.put {
            url("$NODE/$id")
            body = bodyPut
            header(key = "Authorization", value = "Bearer $token")
        }
    }

    override suspend fun createNode(token: String, bodyPut: String): String? {
        return httpClient.put {
            url("$NODE/create")
            body = bodyPut
            header(key = "Authorization", value = "Bearer $token")
        }
    }


    override suspend fun getWayById(id: String): WayDto? {
        return httpClient.get {
            url("$WAY/$id")
            header(key = "Accept", value = "application/json")
        }

    }

    override suspend fun updateWay(token: String, id: String, bodyPut: String): String? {
        return httpClient.put {
            url("$WAY/$id")
            body = bodyPut
            header(key = "Authorization", value = "Bearer $token")
        }
    }

    override suspend fun createWay(token: String, bodyPut: String): String? {
        return httpClient.put {
            url("$WAY/create")
            body = bodyPut
            header(key = "Authorization", value = "Bearer $token")
        }
    }


    override suspend fun getRelationById(id: String): RelationDto? {
        return httpClient.get {
            url("$RELATION/$id")
            header(key = "Accept", value = "application/json")
        }

    }

    override suspend fun updateRelation(token: String, id: String, bodyPut: String): String? {
        return httpClient.put {
            url("$RELATION/$id")
            body = bodyPut
            header(key = "Authorization", value = "Bearer $token")
        }
    }

    override suspend fun createRelation(token: String, bodyPut: String): String? {
        return httpClient.put {
            url("$RELATION/create")
            body = bodyPut
            header(key = "Authorization", value = "Bearer $token")
        }
    }

    override suspend fun createNote(token: String, bobo: Bobo): HttpResponse? {
        return httpClient.post {
            url("https://api.openstreetmap.org/api/0.6/notes.json")
            contentType(ContentType.Application.Json)
            body = bobo
        }
    }
}

