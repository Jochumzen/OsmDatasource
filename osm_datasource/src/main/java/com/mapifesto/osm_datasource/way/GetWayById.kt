package com.mapifesto.osm_datasource.way

import com.mapifesto.domain.OsmWay
import com.mapifesto.osm_datasource.Mapper
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWayById(
    private val service: OsmService,
) {
    fun execute(
        id: String,
    ): Flow<OsmDataState<OsmWay>> = flow {

        var errorMessage: String? = null

        val wayDto: WayDto? = try {
            service.getWayById(id = id)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (wayDto == null) {
            emit(OsmDataState.Error("Error executing GetWayById. Error message: $errorMessage"))
            return@flow
        }

        val wayElements = wayDto.elements

        if(wayElements.size != 1) {
            emit(OsmDataState.Error("Error executing GetWayById. Unexpected size of nodeElements: ${wayElements.size}"))
            return@flow
        }

        val wayElement = wayDto.elements[0]

        val way = Mapper.createWay(wayElement)

        emit(OsmDataState.Data(way))

    }
}