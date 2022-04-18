package com.mobiledevelopmentfoodapp

import com.mobiledevelopmentfoodapp.service.IRestaurantService
import com.mobiledevelopmentfoodapp.service.RestaurantService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule= module {
    viewModel { MainViewModel(get()) }
    single<IRestaurantService>{ RestaurantService()}
}