package com.mapifesto.osm_datasource

import com.mapifesto.domain.*
import com.mapifesto.domain.OsmWay

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface OsmIntermediary {

    fun getUserDetails(token: String, callback: (OsmDataState<UserDetails>) -> Unit)

    fun getOpenChangeSetsData(displayName: String, callback: (OsmDataState<OpenChangesetsData>) -> Unit)

    fun createChangeSet(token: String, callback: (OsmDataState<Long>) -> Unit)

    fun closeChangeSet(token: String, id: Long, callback: (OsmDataState<String>) -> Unit)

    fun getNodeById(id: Long, callback: (OsmDataState<OsmNode>) -> Unit)

    fun updateNode(token: String, elementId: Long, displayName: String, version: Int, location: LatLon, tags: List<OsmTag>, callback: (OsmDataState<String>) -> Unit)

    fun createNode(token: String, displayName: String, location: LatLon, tags: List<OsmTag>, callback: (OsmDataState<String>) -> Unit)

    fun getWayById(id: Long, callback: (OsmDataState<OsmWay>) -> Unit)

    fun updateWay(token: String, elementId: Long, displayName: String, version: Int, tags: List<OsmTag>, nodes: List<Long>, callback: (OsmDataState<String>) -> Unit)

    fun createWay(token: String, displayName: String, tags: List<OsmTag>, nodes: List<Long>, callback: (OsmDataState<String>) -> Unit)

    fun getRelationById(id: Long, callback: (OsmDataState<OsmRelation>) -> Unit)

    fun updateRelation(token: String, elementId: Long, displayName: String, version: Int, tags: List<OsmTag>, members: List<OsmRelationMember>, callback: (OsmDataState<String>) -> Unit)

    fun createRelation(token: String, displayName: String, tags: List<OsmTag>, members: List<OsmRelationMember>, callback: (OsmDataState<String>) -> Unit)

    //fun getElementByIdAndType(id: Long, type: OsmElementType, callback: (OsmDataState<OsmRelation>) -> Unit)
}

class OsmIntermediaryImpl(): OsmIntermediary {

    private var changeSetId: Long? = null

    override fun getUserDetails(token: String, callback: (OsmDataState<UserDetails>) -> Unit) {
        val getUserDetails = OsmInteractors.build().getUserDetails
        getUserDetails.execute(token = token).onEach { dataState ->

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun getOpenChangeSetsData(displayName: String, callback: (OsmDataState<OpenChangesetsData>) -> Unit) {
        val getChangesetsData = OsmInteractors.build().getChangesetsData
        getChangesetsData.execute(displayName = displayName).onEach { dataState ->

/*            if (dataState is OsmDataState.Data && dataState.data.iDOfOpenChangeSet != 0L)
                changeSetId = dataState.data.iDOfOpenChangeSet*/

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun createChangeSet(token: String, callback: (OsmDataState<Long>) -> Unit) {
        val createChangeset = OsmInteractors.build().createChangeset
        createChangeset.execute(token = token).onEach { dataState ->

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun closeChangeSet(token: String, id: Long, callback: (OsmDataState<String>) -> Unit) {
        val closeChangeset = OsmInteractors.build().closeChangeset
        closeChangeset.execute(token = token, id = id.toString()).onEach { dataState ->

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun getNodeById(id: Long, callback: (OsmDataState<OsmNode>) -> Unit) {
        val getNodeById = OsmInteractors.build().getNodeById
        getNodeById.execute(id = id.toString()).onEach { dataState ->

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun updateNode(
        token: String,
        elementId: Long,
        displayName: String,
        version: Int,
        location: LatLon,
        tags: List<OsmTag>,
        callback: (OsmDataState<String>) -> Unit,
    ) {

        if(changeSetId == null) {
            getOrCreateChangeSet(displayName = displayName, token = token) { error ->
                if(error == null) {

                    val updateNode = OsmInteractors.build().updateNode
                    updateNode.execute(
                        token = token,
                        elementId = elementId.toString(),
                        changeSetId = changeSetId.toString(),
                        version = version.toString(),
                        location = location,
                        tags = tags
                    ).onEach { dataState ->

                        callback(dataState)

                    }.launchIn(CoroutineScope(Dispatchers.Main))

                } else {
                    callback(OsmDataState.Error(error = error))
                }
            }

        }


    }

    override fun createNode(
        token: String,
        displayName: String,
        location: LatLon,
        tags: List<OsmTag>,
        callback: (OsmDataState<String>) -> Unit,
    ) {

        if(changeSetId == null) {
            getOrCreateChangeSet(displayName = displayName, token = token) { error ->
                if(error == null) {

                    val createNode = OsmInteractors.build().createNode
                    createNode.execute(
                        token = token,
                        changeSetId = changeSetId.toString(),
                        location = location,
                        tags = tags
                    ).onEach { dataState ->

                        callback(dataState)

                    }.launchIn(CoroutineScope(Dispatchers.Main))

                } else {
                    callback(OsmDataState.Error(error = error))
                }
            }

        }
    }


    override fun getWayById(id: Long, callback: (OsmDataState<OsmWay>) -> Unit) {
        val getWayById = OsmInteractors.build().getWayById
        getWayById.execute(id = id.toString()).onEach { dataState ->

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun updateWay(
        token: String,
        elementId: Long,
        displayName: String,
        version: Int,
        tags: List<OsmTag>,
        nodes: List<Long>,
        callback: (OsmDataState<String>) -> Unit,
    ) {

        if(changeSetId == null) {
            getOrCreateChangeSet(displayName = displayName, token = token) { error ->
                if(error == null) {

                    val updateWay = OsmInteractors.build().updateWay
                    updateWay.execute(
                        token = token,
                        elementId = elementId.toString(),
                        changeSetId = changeSetId.toString(),
                        version = version.toString(),
                        tags = tags,
                        nodes = nodes,
                    ).onEach { dataState ->

                        callback(dataState)

                    }.launchIn(CoroutineScope(Dispatchers.Main))

                } else {
                    callback(OsmDataState.Error(error = error))
                }
            }

        }


    }

    override fun createWay(
        token: String,
        displayName: String,
        tags: List<OsmTag>,
        nodes: List<Long>,
        callback: (OsmDataState<String>) -> Unit,
    ) {

        if(changeSetId == null) {
            getOrCreateChangeSet(displayName = displayName, token = token) { error ->
                if(error == null) {

                    val createWay = OsmInteractors.build().createWay
                    createWay.execute(
                        token = token,
                        changeSetId = changeSetId.toString(),
                        tags = tags,
                        nodes = nodes,
                    ).onEach { dataState ->

                        callback(dataState)

                    }.launchIn(CoroutineScope(Dispatchers.Main))

                } else {
                    callback(OsmDataState.Error(error = error))
                }
            }

        }
    }

    override fun getRelationById(id: Long, callback: (OsmDataState<OsmRelation>) -> Unit) {
        val getRelationById = OsmInteractors.build().getRelationById
        getRelationById.execute(id = id.toString()).onEach { dataState ->

            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun updateRelation(
        token: String,
        elementId: Long,
        displayName: String,
        version: Int,
        tags: List<OsmTag>,
        members: List<OsmRelationMember>,
        callback: (OsmDataState<String>) -> Unit,
    ) {

        if(changeSetId == null) {
            getOrCreateChangeSet(displayName = displayName, token = token) { error ->
                if(error == null) {

                    val updateRelation = OsmInteractors.build().updateRelation
                    updateRelation.execute(
                        token = token,
                        elementId = elementId.toString(),
                        changeSetId = changeSetId.toString(),
                        version = version.toString(),
                        tags = tags,
                        members = members,
                    ).onEach { dataState ->

                        callback(dataState)

                    }.launchIn(CoroutineScope(Dispatchers.Main))

                } else {
                    callback(OsmDataState.Error(error = error))
                }
            }

        }


    }

    override fun createRelation(
        token: String,
        displayName: String,
        tags: List<OsmTag>,
        members: List<OsmRelationMember>,
        callback: (OsmDataState<String>) -> Unit,
    ) {

        if(changeSetId == null) {
            getOrCreateChangeSet(displayName = displayName, token = token) { error ->
                if(error == null) {

                    val createRelation = OsmInteractors.build().createRelation
                    createRelation.execute(
                        token = token,
                        changeSetId = changeSetId.toString(),
                        tags = tags,
                        members = members,
                    ).onEach { dataState ->

                        callback(dataState)

                    }.launchIn(CoroutineScope(Dispatchers.Main))

                } else {
                    callback(OsmDataState.Error(error = error))
                }
            }

        }
    }

/*    override fun getElementByIdAndType(
        id: Long,
        type: OsmElementType,
        callback: (OsmDataState<OsmElement>) -> Unit
    ) {
        when(type) {
            OsmElementType.NODE -> {
                getNodeById(id) {
                    //Handle node
                }
            }
            OsmElementType.WAY -> {
                getWayById(id) {
                    //Handle way
                }
            }
            OsmElementType.RELATION -> {
                getRelationById(id) {
                    //Handle relation
                }
            }
        }
    }*/

    private fun getOrCreateChangeSet(token: String, displayName: String, callback: (String?) -> Unit) {

        getOpenChangeSetsData(displayName) { openChangeSetData ->
            when(openChangeSetData) {
                is OsmDataState.Error -> callback(openChangeSetData.error)
                is OsmDataState.Data -> {

                    //First, check if there is an open changeSet
                    if (openChangeSetData.data.iDOfOpenChangeSet != 0L) {

                        changeSetId = openChangeSetData.data.iDOfOpenChangeSet
                        callback(null)

                    } else {

                        //No, create one.
                        createChangeSet(token = token) { createChangeSetData ->
                            when(createChangeSetData) {
                                is OsmDataState.Error -> callback(createChangeSetData.error)
                                is OsmDataState.Data -> {
                                    changeSetId = createChangeSetData.data
                                    callback(null)
                                }
                            }

                        }
                    }
                }
            }
        }
    }

/*    private fun getOpenChangeSetsDataLocal(displayName: String, callback: (OsmDataState<OpenChangesetsData>) -> Unit) {
        val getChangesetsData = OsmInteractors.build().getChangesetsData
        getChangesetsData.execute(displayName = displayName).onEach { dataState ->

            if (dataState is OsmDataState.Data && dataState.data.iDOfOpenChangeSet != 0L)
                changeSetId = dataState.data.iDOfOpenChangeSet
            callback(dataState)

        }.launchIn(CoroutineScope(Dispatchers.Main))
    }*/
}

class OsmIntermediaryMockup(): OsmIntermediary {
    override fun getUserDetails(token: String, callback: (OsmDataState<UserDetails>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getOpenChangeSetsData(
        displayName: String,
        callback: (OsmDataState<OpenChangesetsData>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun createChangeSet(token: String, callback: (OsmDataState<Long>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun closeChangeSet(token: String, id: Long, callback: (OsmDataState<String>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getNodeById(id: Long, callback: (OsmDataState<OsmNode>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateNode(
        token: String,
        elementId: Long,
        displayName: String,
        version: Int,
        location: LatLon,
        tags: List<OsmTag>,
        callback: (OsmDataState<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun createNode(
        token: String,
        displayName: String,
        location: LatLon,
        tags: List<OsmTag>,
        callback: (OsmDataState<String>) -> Unit
    ) {
        callback(OsmDataState.Data("12345"))
    }

    override fun getWayById(id: Long, callback: (OsmDataState<OsmWay>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateWay(
        token: String,
        elementId: Long,
        displayName: String,
        version: Int,
        tags: List<OsmTag>,
        nodes: List<Long>,
        callback: (OsmDataState<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun createWay(
        token: String,
        displayName: String,
        tags: List<OsmTag>,
        nodes: List<Long>,
        callback: (OsmDataState<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getRelationById(id: Long, callback: (OsmDataState<OsmRelation>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateRelation(
        token: String,
        elementId: Long,
        displayName: String,
        version: Int,
        tags: List<OsmTag>,
        members: List<OsmRelationMember>,
        callback: (OsmDataState<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun createRelation(
        token: String,
        displayName: String,
        tags: List<OsmTag>,
        members: List<OsmRelationMember>,
        callback: (OsmDataState<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}
