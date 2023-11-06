package com.bitio.infrastructure.user.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "user")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = Random.nextInt(),
    val email: String?,
    val fullName: String?,
    val phoneNumber: String?,
    val profileImage: String?,
)