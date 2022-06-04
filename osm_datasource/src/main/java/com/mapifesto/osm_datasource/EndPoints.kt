package com.mapifesto.osm_datasource

object EndPoints {

    private const val useOsmTest = true

    private const val OSM_URL_LIVE = "https://api.openstreetmap.org"
    private const val OSM_URL_TEST = "https://master.apis.dev.openstreetmap.org"

    private val OSM_URL = if(useOsmTest) OSM_URL_TEST else OSM_URL_LIVE

    private const val CHANGESET_API = "/api/0.6/changesets"
    private const val USER_DETAILS_API = "/api/0.6/user/details"
    private const val NODE_API = "/api/0.6/node"

    val GET_CHANGESETS = "$OSM_URL$CHANGESET_API"
    val CHANGESET = "$OSM_URL$CHANGESET_API"
    val DETAILS = "$OSM_URL$USER_DETAILS_API"
    val NODE = "$OSM_URL$NODE_API"



/*    private const val MOM_URL = "https://api.yourmom.com"
    private const val AGE_API = "/api/age"
    const val AGE = "$MOM_URL$AGE_API"*/

}