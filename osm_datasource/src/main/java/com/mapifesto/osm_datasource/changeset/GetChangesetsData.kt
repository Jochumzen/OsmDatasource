package com.mapifesto.osm_datasource.changeset

import com.mapifesto.domain.OpenChangesetsData
import com.mapifesto.osm_datasource.Mapper.createOpenChangesetsData
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChangesetsData(
    private val service: OsmService,
) {
    fun execute(
        displayName: String,
    ): Flow<OsmDataState<OpenChangesetsData>> = flow {

        var errorMessage: String? = null

        val changesetsDto: ChangesetsDto? = try {
            service.queryChangeset(
                open = true,
                displayName = displayName
            )
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (changesetsDto == null) {
            emit(OsmDataState.Error("Error executing MyInteractor. Error message: $errorMessage"))
            return@flow
        }

        val openChangesetsData = createOpenChangesetsData(changesetsDto)

        emit(
            OsmDataState.Data( openChangesetsData )
        )
    }
}