package com.mapifesto.osm_datasource.node

import com.mapifesto.domain.LatLon
import com.mapifesto.domain.OsmTag
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateNode (
    private val service: OsmService,
) {
    fun execute(
        token: String,
        changeSetId: String,
        location: LatLon,
        tags: List<OsmTag>,
    ): Flow<OsmDataState<String>> = flow {

        var errorMessage: String? = null

        val lat = location.lat.toString()
        val lon = location.lon.toString()

        val osmXmlStringInit = "<osm>\n<node changeset=\"${changeSetId}\" lat=\"$lat\" lon=\"$lon\">\n"
        val osmXmlStringEnd = "</node>\n</osm>"
        val tagsAsString = tags.joinToString("") {
            "<tag k=\"${it.key}\" v=\"${fixXml(it.value)}\"/>\n"
        }
        val osmXmlString = osmXmlStringInit + tagsAsString + osmXmlStringEnd

        val response: String? = try {
            service.createNode(token = token, bodyPut = osmXmlString)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (response == null) {
            emit(OsmDataState.Error("Error executing CreateNode. Error message: $errorMessage"))
            return@flow
        }

        emit(OsmDataState.Data(response))

    }

    private fun fixXml(input: String): String {
        return input.replace("&", "&amp;").replace("&amp;amp;", "&amp;")
    }
}