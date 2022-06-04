package com.mapifesto.osm_datasource.changeset

import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.overpass_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CloseChangeset(
    private val service: OsmService,
) {
    fun execute(
        id: String
    ): Flow<OsmDataState<String>> = flow {

        var result: String = "OK"

        try {
            service.closeChangeset(id)
        } catch (e: Exception) {
            e.printStackTrace()
            result = e.message?: "Error"
        }

        if (result == "OK") {
            emit(OsmDataState.Data(result))
        } else {
            emit(OsmDataState.Error(result))
        }

    }
}