package com.mapifesto.osm_datasource

import com.mapifesto.domain.*
import com.mapifesto.domain.OsmWay
import com.mapifesto.osm_datasource.changeset.ChangesetsDto
import com.mapifesto.osm_datasource.node.NodeDto
import com.mapifesto.osm_datasource.relation.RelationDto
import com.mapifesto.osm_datasource.user.UserDto
import com.mapifesto.osm_datasource.way.WayDto

object Mapper {

    fun createUserDetails(userDto: UserDto.UserDetailsDto): UserDetails {
        return UserDetails(
            id = userDto.id,
            displayName = userDto.displayName,
            imgUrl = userDto.img.href,
            changesets = userDto.changesets.count
        )
    }

    fun createOpenChangesetsData(changesetsDto: ChangesetsDto): OpenChangesetsData {
        val iDOfOpenChangeSet = if (changesetsDto.changesets.isNotEmpty())
            changesetsDto.changesets[0].id
        else 0

        return OpenChangesetsData(
            numberOfOpenChangesets = changesetsDto.changesets.size,
            iDOfOpenChangeSet = iDOfOpenChangeSet,
        )
    }

    fun createNode(nodeElementDto: NodeDto.ElementDto): OsmNode {
        return OsmNode(
            id = nodeElementDto.id,
            location = LatLon(lat = nodeElementDto.lat, lon = nodeElementDto.lon),
            version = nodeElementDto.version,
            tags = OsmTags( tags = nodeElementDto.tags)
        )
    }

    fun createWay(wayElementDto: WayDto.ElementDto): OsmWay {
        return OsmWay(
            id = wayElementDto.id,
            version = wayElementDto.version,
            tags = OsmTags( tags = wayElementDto.tags),
            nodes = wayElementDto.nodes
        )
    }

    fun createRelation(relationElementDto: RelationDto.ElementDto): OsmRelation {
        return OsmRelation(
            id = relationElementDto.id,
            version = relationElementDto.version,
            tags = OsmTags( tags = relationElementDto.tags),
            members = relationElementDto.members.map{ OsmRelationMember(type = it.type, ref = it.ref, role = it.role)}
        )
    }


}