package com.bitio.infrastructure.user.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bitio.usercomponent.domain.model.User
import kotlin.random.Random

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Int = Random.nextInt(),
    override val email: String?,
    override val fullName: String?,
    override val phoneNumber: String?,
    override val profileImage: String?,
) : User