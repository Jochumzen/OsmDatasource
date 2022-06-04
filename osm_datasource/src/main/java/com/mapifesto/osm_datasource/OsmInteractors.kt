package com.mapifesto.osm_datasource

import com.mapifesto.osm_datasource.changeset.CloseChangeset
import com.mapifesto.osm_datasource.changeset.CreateChangeset
import com.mapifesto.osm_datasource.changeset.GetChangesetsData
import com.mapifesto.osm_datasource.node.GetNodeById
import com.mapifesto.osm_datasource.user.GetUserDetails

data class OsmInteractors(
    val getChangesetsData: GetChangesetsData,
    val createChangeset: CreateChangeset,
    val closeChangeset: CloseChangeset,
    val getNodeById: GetNodeById,
    val getUserDetails: GetUserDetails,
) {
    companion object Factory {
        fun build(): OsmInteractors{
            val service = OsmService.build()
            return OsmInteractors(
                getChangesetsData = GetChangesetsData(
                    service = service,
                ),
                createChangeset = CreateChangeset(
                    service = service,
                ),
                closeChangeset = CloseChangeset(
                    service = service,
                ),
                getNodeById = GetNodeById(
                    service = service,
                ),
                getUserDetails = GetUserDetails(
                    service = service
                )
            )
        }

    }
}