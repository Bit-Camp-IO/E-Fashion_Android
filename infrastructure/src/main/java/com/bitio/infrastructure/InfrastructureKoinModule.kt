package com.bitio.infrastructure

import androidx.room.Room
import com.bitio.infrastructure.auth.di.authKoinModule
import com.bitio.infrastructure.notification.di.notificationKoinModule
import com.bitio.infrastructure.product.di.productKoinModule
import com.bitio.infrastructure.roomConfiguration.AppDatabase
import com.bitio.infrastructure.user.di.userKoinModule
import org.koin.dsl.module

val infrastructureKoinModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "app-database"
        ).build()
    }
    includes(authKoinModule, productKoinModule,userKoinModule, notificationKoinModule)
}