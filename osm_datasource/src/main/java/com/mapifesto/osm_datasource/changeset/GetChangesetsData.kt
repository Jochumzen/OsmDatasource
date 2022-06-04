package com.mapifesto.osm_datasource.changeset

import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.overpass_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChangesetsData(
    private val service: OsmService,
) {
    fun execute(
        displayName: String,
    ): Flow<OsmDataState<ChangesetsData>> = flow {

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

        val iDOfOpenChangeSet = if (changesetsDto.changesets.isNotEmpty())
            changesetsDto.changesets[0].id
        else 0

        emit(
            OsmDataState.Data(
                ChangesetsData(
                    numberOfOpenChangesets = changesetsDto.changesets.size,
                    iDOfOpenChangeSet = iDOfOpenChangeSet
                )
            )
        )

    }
}