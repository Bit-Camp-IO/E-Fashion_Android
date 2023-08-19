package com.bitio.efashion

import android.app.Application
import com.bitio.infrastructure.auth.di.authModule
import com.bitio.ui.presentationDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class FashionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FashionApp)
            modules(authModule, presentationDiModule)
        }
    }
}