package com.mapifesto.osm_datasource

import com.mapifesto.domain.UserDetails
import com.mapifesto.osm_datasource.user.UserDto

object Mapper {

    fun createUserDetails(userDto: UserDto.UserDetailsDto): UserDetails {
        return UserDetails(
            id = userDto.id,
            displayName = userDto.displayName,
            imgUrl = userDto.img.href,
            changesets = userDto.changesets.count
        )
    }
}