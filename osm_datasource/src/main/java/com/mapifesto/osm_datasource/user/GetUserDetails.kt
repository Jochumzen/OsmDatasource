package com.mapifesto.osm_datasource.user

import com.mapifesto.domain.UserDetails
import com.mapifesto.osm_datasource.Mapper
import com.mapifesto.osm_datasource.OsmService
import com.mapifesto.overpass_datasource.OsmDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserDetails (
    private val service: OsmService,
) {
    fun execute(
        token: String,
    ): Flow<OsmDataState<UserDetails>> = flow {

        var errorMessage: String? = null

        val userDtoResult: UserDto? = try {
            service.gerUserDetails(token)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message?: "No error message provided"
            null
        }

        if (userDtoResult == null) {
            emit(OsmDataState.Error("Error executing MyInteractor. Error message: $errorMessage"))
            return@flow
        }

        val userDetails = Mapper.createUserDetails(userDtoResult.user)
        emit(OsmDataState.Data(userDetails))

    }
}