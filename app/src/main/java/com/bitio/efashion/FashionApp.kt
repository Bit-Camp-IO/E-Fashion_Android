package com.bitio.efashion

import android.app.Application
import com.bitio.infrastructure.infrastructureKoinModule
import com.bitio.infrastructure.workManager.AuthWorkManager
import com.bitio.ui.presentationDiModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.defaultModule

class FashionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@FashionApp)
            modules(infrastructureKoinModule, presentationDiModule, defaultModule)
        }
        AuthWorkManager.enqueueWork()
    }
}
