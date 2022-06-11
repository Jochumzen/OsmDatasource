package com.mapifesto.osm_datasource.changeset

import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CloseChangeset(
    private val service: OsmService,
) {
    fun execute(
        token: String,
        id: String
    ): Flow<OsmDataState<String>> = flow {

        var errorMessage: String? = null

        try {
            service.closeChangeset(token = token, id = id)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
        }

        if (errorMessage != null) {
            emit(OsmDataState.Error("Error executing CloseChangeset. Error message: $errorMessage"))
            return@flow
        }

        emit(OsmDataState.Data("OK"))

    }
}