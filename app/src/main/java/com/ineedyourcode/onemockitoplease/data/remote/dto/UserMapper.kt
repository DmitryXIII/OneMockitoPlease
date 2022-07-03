package com.ineedyourcode.onemockitoplease.data.remote.dto

import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile

class UserMapper {
    fun mapUserProfileDto(userProfileDto: UserProfileDto): UserProfile {
        return UserProfile(
            id = userProfileDto.id.toString(),
            login = userProfileDto.login,
            name = userProfileDto.name,
            avatar = userProfileDto.avatarUrl
        )
    }
}