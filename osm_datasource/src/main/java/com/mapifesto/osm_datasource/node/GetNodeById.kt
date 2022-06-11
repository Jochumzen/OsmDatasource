package com.mapifesto.osm_datasource.node

import com.mapifesto.domain.OsmNode
import com.mapifesto.osm_datasource.Mapper
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNodeById(
    private val service: OsmService,
) {
    fun execute(
        id: String,
    ): Flow<OsmDataState<OsmNode>> = flow {

        var errorMessage: String? = null

        val nodeDto: NodeDto? = try {
            service.getNodeById( id = id)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (nodeDto == null) {
            emit(OsmDataState.Error("Error executing GetNodeById. Error message: $errorMessage"))
            return@flow
        }

        val nodeElements = nodeDto.elements

        if(nodeElements.size != 1) {
            emit(OsmDataState.Error("Error executing GetNodeById. Unexpected size of nodeElements: ${nodeElements.size}"))
            return@flow
        }

        val nodeElement = nodeDto.elements[0]

        val node = Mapper.createNode(nodeElement)

        emit(OsmDataState.Data(node))

    }
}