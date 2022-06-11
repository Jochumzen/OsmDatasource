package com.mapifesto.osm_datasource

sealed class OsmDataState<T> {

    data class Error<T>(
        val error: String
    ): OsmDataState<T>()

    data class Data<T>(
        val data: T
    ): OsmDataState<T>()

}