package com.mapifesto.osm_datasource

import com.mapifesto.domain.UserDetails
import com.mapifesto.overpass_datasource.OsmDataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface OsmIntermediary {

    fun getUserDetails(token: String, callback: (OsmDataState<UserDetails>) -> Unit)

}

class OsmIntermediaryImpl: OsmIntermediary {

    override fun getUserDetails(token: String, callback: (OsmDataState<UserDetails>) -> Unit) {
        val getUserDetails = OsmInteractors.build().getUserDetails
        getUserDetails.execute(token = token).onEach { dataState ->

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }
}

//"aCq9kW2SXeHJhnghioCDcskL1k49GjsLRo3pmCWw71U"