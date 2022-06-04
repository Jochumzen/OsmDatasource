package com.mapifesto.osm_datasource.changeset

import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.overpass_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateChangeset(
    private val service: OsmService,
) {

    fun execute(): Flow<OsmDataState<String>> = flow {

        val x: String? = try { // catch network exceptions
            service.createChangeset()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (x != null) {
            emit(OsmDataState.Data(x))
        } else {
            emit(OsmDataState.Error("No"))
        }

    }

}