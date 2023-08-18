package com.bitio.ui

import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.chat.ChatViewModel
import com.bitio.ui.favorite.FavoriteViewModel
import com.bitio.ui.location.MapViewModel
import com.bitio.ui.product.productUiDi
import com.bitio.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationDiModule = module {

    includes(productUiDi)
    viewModel { AuthenticationViewModel() }
    viewModel { ChatViewModel() }
    viewModel { FavoriteViewModel() }
    viewModel { MapViewModel() }
    viewModel { ProfileViewModel() }

}