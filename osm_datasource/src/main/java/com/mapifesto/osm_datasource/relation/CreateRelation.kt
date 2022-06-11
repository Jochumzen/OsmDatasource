package com.mapifesto.osm_datasource.relation

import com.mapifesto.domain.OsmRelationMember
import com.mapifesto.domain.OsmTag
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateRelation (
    private val service: OsmService,
) {
    fun execute(
        token: String,
        changeSetId: String,
        tags: List<OsmTag>,
        members: List<OsmRelationMember>,
    ): Flow<OsmDataState<String>> = flow {

        emit(OsmDataState.Error("Not yet implemented"))

    }

}