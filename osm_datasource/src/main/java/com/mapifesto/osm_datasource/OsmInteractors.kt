package com.mapifesto.osm_datasource

import com.mapifesto.osm_datasource.changeset.CloseChangeset
import com.mapifesto.osm_datasource.changeset.CreateChangeset
import com.mapifesto.osm_datasource.changeset.GetChangesetsData
import com.mapifesto.osm_datasource.node.CreateNode
import com.mapifesto.osm_datasource.node.GetNodeById
import com.mapifesto.osm_datasource.node.UpdateNode
import com.mapifesto.osm_datasource.note.CreateNote
import com.mapifesto.osm_datasource.relation.CreateRelation
import com.mapifesto.osm_datasource.relation.GetRelationById
import com.mapifesto.osm_datasource.relation.UpdateRelation
import com.mapifesto.osm_datasource.user.GetUserDetails
import com.mapifesto.osm_datasource.way.CreateWay
import com.mapifesto.osm_datasource.way.GetWayById
import com.mapifesto.osm_datasource.way.UpdateWay

data class OsmInteractors(
    val getChangesetsData: GetChangesetsData,
    val createChangeset: CreateChangeset,
    val closeChangeset: CloseChangeset,
    val getUserDetails: GetUserDetails,
    val getNodeById: GetNodeById,
    val updateNode: UpdateNode,
    val createNode: CreateNode,
    val getWayById: GetWayById,
    val updateWay: UpdateWay,
    val createWay: CreateWay,
    val getRelationById: GetRelationById,
    val updateRelation: UpdateRelation,
    val createRelation: CreateRelation,
    val createNote: CreateNote,
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
                getUserDetails = GetUserDetails(
                    service = service
                ),
                getNodeById = GetNodeById(
                    service = service,
                ),
                updateNode = UpdateNode(
                    service = service
                ),
                createNode = CreateNode(
                    service = service
                ),
                getWayById = GetWayById(
                    service = service,
                ),
                updateWay = UpdateWay(
                    service = service
                ),
                createWay = CreateWay(
                    service = service
                ),
                getRelationById = GetRelationById(
                    service = service,
                ),
                updateRelation = UpdateRelation(
                    service = service
                ),
                createRelation = CreateRelation(
                    service = service
                ),
                createNote = CreateNote(
                    service = service
                )
            )
        }

    }
}