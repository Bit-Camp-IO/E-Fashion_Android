package com.bitio.efashion

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.bitio.infrastructure.roomConfiguration.AppDatabase
import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.repository.ProductRepository
import com.bitio.ui.authentication.AuthenticationScreen
import com.bitio.ui.theme.EFashionTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class FashionActivity : ComponentActivity() {


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

        }
    }

    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this, ACCESS_FINE_LOCATION
        ) -> {
            // TODO
        }

        else -> {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()


        setContent {
            val isDarkTheme by remember {
                mutableStateOf(false)
            }
            EFashionTheme(
                darkTheme = isDarkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    BottomNavigationBar(checkIfLogin = true)
                }
            }
        }
        // val productRepo by inject<ProductRoomDao>()
        val roomDb = Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = "app-database"
        ).build()
        // val productRepo by inject<AppDatabase>()
        val productApi by inject<ProductRepository>()
        GlobalScope.launch {
            // val catgories = productApi.get
            // Log.d("xxxx", catgories.toString())

            //roomDb.productRoomDao()
            //val products = productRepo.getProductsByCategoryAndBrand(null, null, 1, 20)
            //  Log.d("xxxx", products.toString())
        }
    }
}

