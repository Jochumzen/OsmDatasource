package com.mapifesto.osm_datasource.relation

import com.mapifesto.domain.OsmRelation
import com.mapifesto.osm_datasource.Mapper
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRelationById(
    private val service: OsmService,
) {
    fun execute(
        id: String,
    ): Flow<OsmDataState<OsmRelation>> = flow {

        var errorMessage: String? = null

        val relationDto: RelationDto? = try {
            service.getRelationById(id = id)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (relationDto == null) {
            emit(OsmDataState.Error("Error executing GetRelationById. Error message: $errorMessage"))
            return@flow
        }

        val relationElements = relationDto.elements

        if(relationElements.size != 1) {
            emit(OsmDataState.Error("Error executing GetRelationById. Unexpected size of relationElements: ${relationElements.size}"))
            return@flow
        }

        val relationElement = relationDto.elements[0]

        val relation = Mapper.createRelation(relationElement)

        emit(OsmDataState.Data(relation))

    }
}