package com.mapifesto.osm_datasource.way

import com.mapifesto.domain.LatLon
import com.mapifesto.domain.OsmTag
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateWay (
    private val service: OsmService,
) {
    fun execute(
        token: String,
        changeSetId: String,
        tags: List<OsmTag>,
        nodes: List<Long>,
    ): Flow<OsmDataState<String>> = flow {

        emit(OsmDataState.Error("Not yet implemented"))

    }

}