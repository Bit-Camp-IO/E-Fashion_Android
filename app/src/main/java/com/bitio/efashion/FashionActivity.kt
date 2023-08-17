package com.bitio.efashion

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitio.productscomponent.domain.entities.products.CollectionGroup
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.theme.EFashionTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FashionActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            }
        }

    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
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

            EFashionTheme(darkTheme = isDarkTheme) {
                BottomNavigationBar(
                    favoriteViewModel = hiltViewModel(),
                    profileViewModel = hiltViewModel(),
                    authenticationViewModel = hiltViewModel(),
                )
            }
        }
    }
}

val collection = object : CollectionGroup {
    override val id: Int
        get() = 1
    override val name: String
        get() = "Summer COLLECTION"
    override val image: String
        get() =
            "https://previews.123rf.com/images/f8studio/f8studio1707/f8studio170701400/82842066-young-girl-in-stylish-clothes-posing-in-the-city-street.jpg"
    override val description: String
        get() = "For Selected collection"
    override val saleRatio: Float
        get() = 0.4f
}

