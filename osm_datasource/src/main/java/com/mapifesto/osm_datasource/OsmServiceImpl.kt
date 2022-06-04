package com.mapifesto.osm_datasource


import com.mapifesto.osm_datasource.EndPoints.CHANGESET
import com.mapifesto.osm_datasource.EndPoints.DETAILS
import com.mapifesto.osm_datasource.EndPoints.GET_CHANGESETS
import com.mapifesto.osm_datasource.changeset.ChangesetsDto
import com.mapifesto.osm_datasource.node.NodeDto
import com.mapifesto.osm_datasource.user.UserDto
import io.ktor.client.*
import io.ktor.client.request.*

class OsmServiceImpl(
    private val httpClient: HttpClient,
): OsmService {
    override suspend fun queryChangeset(open: Boolean, displayName: String): ChangesetsDto? {
        return if(displayName == "") null else {
            val openString = if(open) "&open=true" else ""
            httpClient.get {
                url("${GET_CHANGESETS}?display_name=$displayName$openString")
                header(key = "Accept", value = "application/json")
            }
        }
    }

    override suspend fun createChangeset(): String {
        return httpClient.put {
            url("$CHANGESET/create")
            body = """<osm>
                    <changeset>
                        <tag k="created_by" v="Mapifesto"/>
                        <tag k="comment" v="Testing"/>
                    </changeset>
                </osm>"""
            header(key = "Authorization", value = "Bearer aCq9kW2SXeHJhnghioCDcskL1k49GjsLRo3pmCWw71U")
        }
    }

    override suspend fun closeChangeset(id: String) {
        return httpClient.put {
            url("$CHANGESET/$id/close")
            header(key = "Authorization", value = "Bearer aCq9kW2SXeHJhnghioCDcskL1k49GjsLRo3pmCWw71U")
        }
    }

    override suspend fun getNode(): NodeDto? {
        return httpClient.get {
            url("https://master.apis.dev.openstreetmap.org/api/0.6/node/4326392324")
            header(key = "Accept", value = "application/json")
        }

    }

    override suspend fun gerUserDetails(token: String): UserDto? {
        return httpClient.get {
            url(DETAILS)
            header(key = "Accept", value = "application/json")
            header(key = "Authorization", value = "Bearer $token")
        }
    }
}

/*
class XxxServiceImpl(
    private val httpClient: HttpClient,
): XxxService {

    override suspend fun addMom(id: Int, age: Int): String {
        return httpClient.put {
            url(AGE)
            body = "<MyMom><Id=$id/><age=$age/></myMom>"
            header(key = "Authorization", value = "Bearer abc")
        }
    }
}*/
