package com.mapifesto.osm_datasource.node

import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.overpass_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNodeById(
    private val service: OsmService,
) {
    fun execute(

    ): Flow<OsmDataState<NodeDto>> = flow {


        val node: NodeDto? = try {
            service.getNode( )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


        if (node != null) {
            emit(OsmDataState.Data(node))
        } else {
            emit(OsmDataState.Error("No"))
        }
    }
}