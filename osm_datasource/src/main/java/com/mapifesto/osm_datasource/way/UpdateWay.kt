package com.mapifesto.osm_datasource.way

import com.mapifesto.domain.LatLon
import com.mapifesto.domain.OsmTag
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateWay(
    private val service: OsmService,
) {
    fun execute(
        token: String,
        elementId: String,
        changeSetId: String,
        version: String,
        tags: List<OsmTag>,
        nodes: List<Long>,
    ): Flow<OsmDataState<String>> = flow {

        var errorMessage: String? = null

        val osmXmlStringInit = "<osm>\n<way id=\"$elementId\" visible=\"true\" version=\"$version\" changeset=\"${changeSetId}\" >\n"
        val osmXmlStringEnd = "</way>\n</osm>"
        val tagsAsString = tags.joinToString("") {
            "<tag k=\"${it.key}\" v=\"${fixXml(it.value)}\"/>\n"
        }
        val nodesAsString = nodes.joinToString("") {
            "<nd ref=\"${it}\"/>\n"
        }
        val osmXmlString = osmXmlStringInit + tagsAsString + nodesAsString + osmXmlStringEnd

        val response: String? = try {
            service.updateWay(token = token, id = elementId, bodyPut = osmXmlString)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (response == null) {
            emit(OsmDataState.Error("Error executing UpdateWay. Error message: $errorMessage"))
            return@flow
        }

        emit(OsmDataState.Data(response))

    }

    private fun fixXml(input: String): String {
        return input.replace("&", "&amp;").replace("&amp;amp;", "&amp;")
    }
}