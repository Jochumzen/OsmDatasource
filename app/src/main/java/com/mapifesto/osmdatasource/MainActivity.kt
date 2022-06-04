package com.mapifesto.osmdatasource

import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.mapifesto.domain.UserDetails
import com.mapifesto.osm_datasource.changeset.ChangesetsDto
import com.mapifesto.osm_datasource.OsmInteractors
import com.mapifesto.osm_datasource.OsmIntermediary
import com.mapifesto.osm_datasource.changeset.ChangesetsData
import com.mapifesto.osmdatasource.ui.theme.OsmDatasourceTheme
import com.mapifesto.overpass_datasource.OsmDataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    var showWhat by remember {mutableStateOf("")}
    var errorMsg by remember {mutableStateOf("")}
    var changesetsData by remember { mutableStateOf<ChangesetsData?>(null)}
    var userData by remember { mutableStateOf<UserDetails?>(null)}
    var createdChangesetId by remember { mutableStateOf("")}
    //var openChangesetId by remember { mutableStateOf(0L)}
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
                            val getChangesets = OsmInteractors.build().getChangesetsData
                            getChangesets.execute(displayName = "Jochumzen").onEach { dataState ->

                                when(dataState) {
                                    is OsmDataState.Error -> { errorMsg = dataState.error}
                                    is OsmDataState.Data -> {
                                        showWhat = "numberOfChangesets"
                                        changesetsData = dataState.data
                                    }
                                }
                            }.launchIn(CoroutineScope(Main))
                        }
                    ) {
                        Text("Get number of changesets")
                    }
                    if(errorMsg != "") Text("Error: $errorMsg") else {
                        when(showWhat) {
                            "numberOfChangesets", "numOCS" -> {
                                val outStr = when(changesetsData!!.numberOfOpenChangesets) {
                                    0 -> "No open changesets"
                                    1 -> "One open changeset with id = ${changesetsData!!.iDOfOpenChangeSet}"
                                    else -> "There are several open changesets"
                                }
                                Text(outStr)
                            }
                            "newCS" -> Text("New changeset created. ID: $createdChangesetId")
                            else -> {}
                        }
                    }
                }
                "user_details" -> {
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

