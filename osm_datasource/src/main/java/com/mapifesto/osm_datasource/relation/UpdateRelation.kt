package com.mapifesto.osm_datasource.relation

import com.mapifesto.domain.OsmRelationMember
import com.mapifesto.domain.OsmTag
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateRelation(
    private val service: OsmService,
) {
    fun execute(
        token: String,
        elementId: String,
        changeSetId: String,
        version: String,
        tags: List<OsmTag>,
        members: List<OsmRelationMember>,
    ): Flow<OsmDataState<String>> = flow {

        var errorMessage: String? = null

        val osmXmlStringInit = "<osm>\n<relation id=\"$elementId\" visible=\"true\" version=\"$version\" changeset=\"${changeSetId}\" >\n"
        val osmXmlStringEnd = "</relation>\n</osm>"
        val tagsAsString = tags.joinToString("") {
            "<tag k=\"${it.key}\" v=\"${fixXml(it.value)}\"/>\n"
        }
        val membersAsString = members.joinToString("") {
            "<member type=\"${it.type}\" ref=\"${it.ref}\" role=\"${it.role}\"/>\n"
        }
        val osmXmlString = osmXmlStringInit + tagsAsString + membersAsString + osmXmlStringEnd

        val response: String? = try {
            service.updateRelation(token = token, id = elementId, body = osmXmlString)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (response == null) {
            emit(OsmDataState.Error("Error executing UpdateRelation. Error message: $errorMessage"))
            return@flow
        }

        emit(OsmDataState.Data(response))

    }

    private fun fixXml(input: String): String {
        return input.replace("&", "&amp;").replace("&amp;amp;", "&amp;")
    }
}