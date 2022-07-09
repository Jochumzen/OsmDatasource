package com.mapifesto.osm_datasource.note

import com.mapifesto.domain.Bobo
import com.mapifesto.domain.LatLon
import com.mapifesto.domain.OsmBoboNote
import com.mapifesto.domain.OsmTag
import com.mapifesto.osm_datasource.OsmDataState
import com.mapifesto.osm_datasource.OsmService
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateNote(
    private val service: OsmService,
) {

    fun execute(
        token: String,
        bobo: OsmBoboNote,
    ): Flow<OsmDataState<HttpResponse>> = flow {

        var errorMessage: String? = null

        val response: HttpResponse? = try {
            service.createNote(token = token, bobo = bobo)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (response == null) {
            emit(OsmDataState.Error("Error executing CreateNote. Error message: $errorMessage"))
            return@flow
        }

        emit(OsmDataState.Data(response))

    }

}