package com.bitio.ui

import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.profile.chat.ChatSupportViewModel
import com.bitio.ui.profile.location.LocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

val presentationDiModule = module {
  //  includes(productUiDi)
    viewModel { ChatSupportViewModel() }
    viewModel { LocationViewModel(get(),get(),get()) }
}

@Module
@ComponentScan("org.koin.sample")
class AnnotationsModule