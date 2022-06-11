package com.mapifesto.osmdatasource

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import com.mapifesto.domain.*
import com.mapifesto.domain.OsmWay
import com.mapifesto.osm_datasource.OsmIntermediary
import com.mapifesto.osmdatasource.ui.theme.OsmDatasourceTheme
import com.mapifesto.osm_datasource.OsmDataState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var osmIntermediary: OsmIntermediary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OsmDatasourceTheme {
                Compose(osmIntermediary)
            }
        }
    }
}

@Composable
fun Compose(osmIntermediary: OsmIntermediary) {

    val token = "aCq9kW2SXeHJhnghioCDcskL1k49GjsLRo3pmCWw71U"
    var showWhat by remember {mutableStateOf("")}
    var errorMsg by remember {mutableStateOf("")}
    var outlinedText by remember {mutableStateOf("")}
    var changesetsData by remember { mutableStateOf<OpenChangesetsData?>(null)}
    var osmNode by remember { mutableStateOf<OsmNode?>(null)}
    var osmWay by remember { mutableStateOf<OsmWay?>(null)}
    var osmRelation by remember { mutableStateOf<OsmRelation?>(null)}
    var userData by remember { mutableStateOf<UserDetails?>(null)}
    var createdChangesetId by remember { mutableStateOf(0L)}
    var newId by remember { mutableStateOf("")}
    var page by remember { mutableStateOf("start")}

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {

            when(page) {
                "start" -> {
                    Text("OSM Data source")
                    Button(
                        onClick = {
                            page = "changesets"
                        }
                    ) {
                        Text("Changesets")
                    }
                    Button(
                        onClick = {
                            page = "user_details"
                        }
                    ) {
                        Text("User details")
                    }
                    Button(
                        onClick = {
                            page = "nodes"
                        }
                    ) {
                        Text("Nodes")
                    }
                    Button(
                        onClick = {
                            page = "ways"
                        }
                    ) {
                        Text("Ways")
                    }
                    Button(
                        onClick = {
                            page = "relations"
                        }
                    ) {
                        Text("Relations")
                    }
                }
                "changesets" -> {
                    Text("OSM Data source: Changesets")

                    Button(
                        onClick = {
                            page = "start"
                        }
                    ) {
                        Text("Start")
                    }

                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            osmIntermediary.getOpenChangeSetsData(displayName = "Jochumzen") { dataState ->
                                when(dataState) {
                                    is OsmDataState.Error -> { errorMsg = dataState.error}
                                    is OsmDataState.Data -> {
                                        showWhat = "openChangeSets"
                                        changesetsData = dataState.data
                                    }
                                }
                            }

                        }
                    ) {
                        Text("Get open changesets")
                    }

                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            osmIntermediary.createChangeSet(token = token) { dataState ->
                                when(dataState) {
                                    is OsmDataState.Error -> { errorMsg = dataState.error}
                                    is OsmDataState.Data -> {
                                        showWhat = "changeSetCreated"
                                        createdChangesetId = dataState.data
                                    }
                                }
                            }

                        }
                    ) {
                        Text("Create changeset")
                    }

                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            val localChangeSetData = changesetsData
                            if(localChangeSetData == null || localChangeSetData.iDOfOpenChangeSet == 0L) {
                                errorMsg = "No open changeSet. Load first."
                            } else {
                                osmIntermediary.closeChangeSet(token = token, id = localChangeSetData.iDOfOpenChangeSet) { dataState ->
                                    when(dataState) {
                                        is OsmDataState.Error -> { errorMsg = dataState.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "changeSetClosed"
                                        }
                                    }
                                }
                            }


                        }
                    ) {
                        Text("Close open changeset")
                    }

                    if(errorMsg != "") Text("Error: $errorMsg") else {
                        when(showWhat) {
                            "openChangeSets",  -> {
                                val outStr = when(changesetsData!!.numberOfOpenChangesets) {
                                    0 -> "No open changesets"
                                    1 -> "One open changeset with id = ${changesetsData!!.iDOfOpenChangeSet}"
                                    else -> "There are several open changesets"
                                }
                                Text(outStr)
                            }
                            "changeSetCreated" -> Text("New changeset created. ID: $createdChangesetId")
                            "changeSetClosed" -> Text("ChangeSet closed successfully")
                            else -> {}
                        }
                    }
                }
                "user_details" -> {
                    Text("User-details")
                    Button(
                        onClick = {
                            page = "start"
                        }
                    ) {
                        Text("Start")
                    }
                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            osmIntermediary.getUserDetails(token = "aCq9kW2SXeHJhnghioCDcskL1k49GjsLRo3pmCWw71U") {
                                when(it) {
                                    is OsmDataState.Error -> { errorMsg = it.error}
                                    is OsmDataState.Data -> {
                                        showWhat = "user_details"
                                        userData = it.data
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Get user details")
                    }
                    if(errorMsg != "") Text("Error: $errorMsg") else {
                        when(showWhat) {
                            "user_details" -> {
                                Text("UserID: ${userData!!.id}")
                                Text("Display name: ${userData!!.displayName}")
                                Image(
                                    painter = rememberImagePainter(userData!!.imgUrl),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
                "nodes" -> {
                    Text("Nodes")
                    Button(
                        onClick = {
                            page = "start"
                        }
                    ) {
                        Text("Start")
                    }
                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            osmIntermediary.getNodeById(id = 4326392324) {
                                when(it) {
                                    is OsmDataState.Error -> { errorMsg = it.error}
                                    is OsmDataState.Data -> {
                                        showWhat = "node"
                                        osmNode = it.data
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Get node (Peters bar)")
                    }

                    Row() {
                        OutlinedTextField(
                            value = outlinedText,
                            onValueChange = {
                                outlinedText = it
                            }
                        )
                        Button(
                            onClick = {
                                showWhat = ""
                                errorMsg = ""
                                osmIntermediary.getNodeById(id = outlinedText.toLong()) {
                                    when(it) {
                                        is OsmDataState.Error -> { errorMsg = it.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "node"
                                            osmNode = it.data
                                        }
                                    }
                                }
                            }
                        ) {
                            Text("Get")
                        }
                    }

                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            val localNode = osmNode
                            if(localNode == null ) {
                                errorMsg = "First load node"
                            } else {
                                val tags = localNode.tags.setTagValue(key = "note", value = "at ${Date()}")
                                osmIntermediary.updateNode(
                                    token = token,
                                    elementId = localNode.id,
                                    displayName = "Jochumzen",
                                    version = localNode.version,
                                    location = localNode.location,
                                    tags = tags.asListOfOsmTags(),
                                ) {
                                    when(it) {
                                        is OsmDataState.Error -> { errorMsg = it.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "update_success"
                                            newId = it.data
                                        }
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Update node (Peters bar)")
                    }

                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            val localChangesetData = changesetsData
                            if(localChangesetData == null || localChangesetData.iDOfOpenChangeSet == 0L) {
                                errorMsg = "First get open changesets"
                            } else {

                                val tags = listOf(OsmTag(key = "amenity", value = "bar"), OsmTag(key = "name", value = "Just a bar"), OsmTag(key = "note", value = "at ${Date().toString()}"))

                                osmIntermediary.createNode(
                                    token = token,
                                    displayName = "Jochumzen",
                                    location = LatLon(lat = 53.0, lon = 36.1),
                                    tags = tags,
                                ) {
                                    when(it) {
                                        is OsmDataState.Error -> { errorMsg = it.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "create_success"
                                            newId = it.data
                                        }
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Create node")
                    }

                    if(errorMsg != "") Text("Error: $errorMsg") else {
                        when(showWhat) {
                            "node" -> {
                                Text("Node ID: ${osmNode!!.id}, Version: ${osmNode!!.version}")

                                LazyColumn(
                                    state = rememberLazyListState()
                                ) {
                                    items(osmNode!!.tags.tags.map { "${it.key}: ${it.value}"}) {
                                        Text(it)
                                    }

                                }
                            }
                            "update_success" -> {
                                Text("Node updated successfully. New version: $newId")
                            }
                            "create_success" -> {
                                Text("Node created successfully. ID: $newId")
                            }
                        }
                    }
                }
                "ways" -> {
                    Text("Ways")
                    Button(
                        onClick = {
                            page = "start"
                        }
                    ) {
                        Text("Start")
                    }
                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            osmIntermediary.getWayById(id = 4305722945) {
                                when(it) {
                                    is OsmDataState.Error -> { errorMsg = it.error}
                                    is OsmDataState.Data -> {
                                        showWhat = "way"
                                        osmWay = it.data
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Get way (Peters way)")
                    }

                    Row() {
                        OutlinedTextField(
                            value = outlinedText,
                            onValueChange = {
                                outlinedText = it
                            }
                        )
                        Button(
                            onClick = {
                                showWhat = ""
                                errorMsg = ""
                                osmIntermediary.getWayById(id = outlinedText.toLong()) {
                                    when(it) {
                                        is OsmDataState.Error -> { errorMsg = it.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "way"
                                            osmWay = it.data
                                        }
                                    }
                                }
                            }
                        ) {
                            Text("Get")
                        }
                    }

                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            val localWay = osmWay
                            if(localWay == null ) {
                                errorMsg = "First load way"
                            } else {
                                val tags = localWay.tags.setTagValue(key = "note", value = "at ${Date()}")
                                osmIntermediary.updateWay(
                                    token = token,
                                    elementId = localWay.id,
                                    displayName = "Jochumzen",
                                    version = localWay.version,
                                    tags = tags.asListOfOsmTags(),
                                    nodes = localWay.nodes,
                                ) {
                                    when(it) {
                                        is OsmDataState.Error -> { errorMsg = it.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "update_success"
                                            newId = it.data
                                        }
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Update way (Peters way)")
                    }


                    if(errorMsg != "") Text("Error: $errorMsg") else {
                        when(showWhat) {
                            "way" -> {
                                Text("Way ID: ${osmWay!!.id}, Version: ${osmWay!!.version}")

                                LazyColumn(
                                    state = rememberLazyListState()
                                ) {
                                    items(osmWay!!.tags.tags.map { "${it.key}: ${it.value}"}) {
                                        Text(it)
                                    }

                                }
                                Text("Nodes:")
                                LazyColumn(
                                    state = rememberLazyListState()
                                ) {
                                    items(osmWay!!.nodes) {
                                        Text(it.toString())
                                    }

                                }
                            }
                            "update_success" -> {
                                Text("Way updated successfully. New version: $newId")
                            }
                        }
                    }
                }
                "relations" -> {
                    Text("Relations")
                    Button(
                        onClick = {
                            page = "start"
                        }
                    ) {
                        Text("Start")
                    }
                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            osmIntermediary.getRelationById(id = 4304892753) {
                                when(it) {
                                    is OsmDataState.Error -> { errorMsg = it.error}
                                    is OsmDataState.Data -> {
                                        showWhat = "relation"
                                        osmRelation = it.data
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Get relation (Peters butchery)")
                    }

                    Row() {
                        OutlinedTextField(
                            value = outlinedText,
                            onValueChange = {
                                outlinedText = it
                            }
                        )
                        Button(
                            onClick = {
                                showWhat = ""
                                errorMsg = ""
                                osmIntermediary.getRelationById(id = outlinedText.toLong()) {
                                    when(it) {
                                        is OsmDataState.Error -> { errorMsg = it.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "way"
                                            osmRelation = it.data
                                        }
                                    }
                                }
                            }
                        ) {
                            Text("Get")
                        }
                    }

                    Button(
                        onClick = {
                            showWhat = ""
                            errorMsg = ""
                            val localRelation = osmRelation
                            if(localRelation == null ) {
                                errorMsg = "First load relation"
                            } else {
                                val tags = localRelation.tags.setTagValue(key = "note", value = "at ${Date()}")
                                osmIntermediary.updateRelation(
                                    token = token,
                                    elementId = localRelation.id,
                                    displayName = "Jochumzen",
                                    version = localRelation.version,
                                    tags = tags.asListOfOsmTags(),
                                    members = localRelation.members,
                                ) {
                                    when(it) {
                                        is OsmDataState.Error -> { errorMsg = it.error}
                                        is OsmDataState.Data -> {
                                            showWhat = "update_success"
                                            newId = it.data
                                        }
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Update relation (Peters way)")
                    }


                    if(errorMsg != "") Text("Error: $errorMsg") else {
                        when(showWhat) {
                            "relation" -> {
                                Text("Relation ID: ${osmRelation!!.id}, Version: ${osmRelation!!.version}")

                                LazyColumn(
                                    state = rememberLazyListState()
                                ) {
                                    items(osmRelation!!.tags.tags.map { "${it.key}: ${it.value}"}) {
                                        Text(it)
                                    }

                                }
                                Text("Members:")
                                LazyColumn(
                                    state = rememberLazyListState()
                                ) {
                                    items(osmRelation!!.members) {
                                        Text("${it.type}, ${it.ref}, ${it.role}")
                                    }

                                }
                            }
                            "update_success" -> {
                                Text("Relation updated successfully. New version: $newId")
                            }
                        }
                    }
                }
            }
        }

    }
}

/*object TestData{

    val emptyChangeSet = ChangesetsDto(
        version = "",
        generator = "",
        copyright = "",
        attribution = "",
        license = "",
        changesets = listOf(),
    )
}*/


/*Row() {

                // Number of changesets
                Button(
                    onClick = {
                        showWhat = ""
                        error = false
                        val getChangesets = OsmInteractors.build().getChangesets
                        getChangesets.execute(open = false).onEach { dataState ->

                            when(dataState) {
                                is DataState.Response -> {error = true}
                                is DataState.Data -> {
                                    showWhat = "numCS"
                                    changesets = dataState.data?: TestData.emptyChangeSet
                                }
                            }
                        }.launchIn(CoroutineScope(Main))
                    }
                ) {
                    Text("#CS")
                }

                // Number of open changesets
                Button(
                    onClick = {
                        showWhat = ""
                        error = false
                        val getChangesets = OsmInteractors.build().getChangesets
                        getChangesets.execute(open = true).onEach { dataState ->

                            when(dataState) {
                                is DataState.Response -> {error = true}
                                is DataState.Data -> {
                                    showWhat = "numOCS"
                                    changesets = dataState.data?: TestData.emptyChangeSet
                                    val num = changesets.changesets.size
                                    if(num == 1) {
                                        openChangesetId = changesets.changesets[0].id
                                    }
                                }
                            }
                        }.launchIn(CoroutineScope(Main))
                    }
                ) {
                    Text("#OCS")
                }

                // Create changeset
                Button(
                    onClick = {
                        showWhat = ""
                        error = false
                        val createChangeset = OsmInteractors.build().createChangeset
                        createChangeset.execute().onEach { dataState ->
                            when(dataState) {
                                is DataState.Response -> {error = true}
                                is DataState.Data -> {
                                    showWhat = "newCS"
                                    createdChangesetId = dataState.data?: ""
                                }
                            }
                        }.launchIn(CoroutineScope(Main))
                    }
                ) {
                    Text("CrCS")
                }

                // close changeset
                Button(
                    onClick = {
                        showWhat = ""
                        error = false
                        if(openChangesetId == 0L) {
                            error = true
                        } else {
                            val closeChangeset = OsmInteractors.build().closeChangeset
                            closeChangeset.execute(openChangesetId.toString()).onEach { dataState ->
                                when(dataState) {
                                    is DataState.Response -> {error = true}
                                    is DataState.Data -> {
                                        showWhat = ""
                                        openChangesetId = 0L
                                    }
                                }
                            }.launchIn(CoroutineScope(Main))

                        }
                    }
                ) {
                    Text("ClCS")
                }

                // Node
                Button(
                    onClick = {
                        showWhat = ""
                        error = false
                        val getNode = OsmInteractors.build().getNodeById
                        getNode.execute().onEach { dataState ->
                            when(dataState) {
                                is DataState.Response -> {error = true}
                                is DataState.Data -> {
                                    val z = dataState.data
                                    val y = 1
                                }
                            }
                        }.launchIn(CoroutineScope(Main))
                    }
                ) {
                    Text("Nd")
                }
            }*/

/*            if(error) Text("Error") else {
                when(showWhat) {
                    "numCS", "numOCS" -> {
                        val num = changesets.changesets.size
                        val outStr = if(openChangesetId == 0L) "Number: $num" else "Number: $num, ID = $openChangesetId"
                        Text(outStr)
                    }
                    "newCS" -> Text("New changeset created. ID: $createdChangesetId")
                    else -> {}
                }
            }*/

