package com.bitio.ui

import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.product.productUiDi
import com.bitio.ui.profile.ProfileViewModel
import com.bitio.ui.profile.chat.ChatViewModel
import com.bitio.ui.profile.location.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

val presentationDiModule = module {
    includes(productUiDi)
    viewModel { AuthenticationViewModel(get(), get(), get()) }
    viewModel { ChatViewModel() }
    viewModel { FavoriteViewModel() }
    viewModel { MapViewModel() }
    viewModel { ProfileViewModel() }
}
@Module
@ComponentScan("org.koin.sample")
class AnnotationsModule