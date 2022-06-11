package com.mapifesto.osm_datasource.changeset

import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.osm_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateChangeset(
    private val service: OsmService,
) {

    fun execute(
        token: String,
    ): Flow<OsmDataState<Long>> = flow {

        val body = """<osm>
                    <changeset>
                        <tag k="created_by" v="Mapifesto"/>
                        <tag k="comment" v="Testing"/>
                    </changeset>
                </osm>"""

        var errorMessage: String? = null

        val response: String? = try { // catch network exceptions
            service.createChangeset(token = token, body = body)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message
            null
        }

        if (response == null) {
            emit(OsmDataState.Error("Error executing CreateChangeset. Error message: $errorMessage"))
            return@flow
        }

        try {
            val value = response.toLong()
            emit(OsmDataState.Data(value))
        }

        catch (ex: NumberFormatException) {
            emit(OsmDataState.Error("Error converting response to long in CreateChangeset"))
        }

    }

}