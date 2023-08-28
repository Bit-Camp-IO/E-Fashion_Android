package com.bitio.ui

import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.product.productUiDi
import com.bitio.ui.profile.ProfileViewModel
import com.bitio.ui.profile.chat.ChatSupportViewModel
import com.bitio.ui.profile.location.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationDiModule = module {

    includes(productUiDi)
    viewModel { AuthenticationViewModel(get(), get(), get()) }
    viewModel { ChatSupportViewModel() }
    viewModel { FavoriteViewModel() }
    viewModel { MapViewModel() }
    viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }

}