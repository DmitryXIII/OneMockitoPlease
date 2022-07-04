package com.ineedyourcode.onemockitoplease.di

import com.ineedyourcode.onemockitoplease.data.remote.GitHubApi
import com.ineedyourcode.onemockitoplease.data.remote.Repository
import com.ineedyourcode.onemockitoplease.data.remote.dto.UserMapper
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserUsecase
import com.ineedyourcode.onemockitoplease.ui.PresenterContract
import com.ineedyourcode.onemockitoplease.ui.UserDetailsPresenter
import com.ineedyourcode.onemockitoplease.ui.utils.UserProfileChecker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_GIT_HUB_API_URL = "https://api.github.com/"

val appModule = module {
    single<PresenterContract> {
        UserDetailsPresenter(
            repository = get(),
            userProfileChecker = get())
    }
    single<GetUserUsecase> { Repository(remoteDataSource = get(), mapper = get()) }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_GIT_HUB_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build())
            .build()
            .create(GitHubApi::class.java)
    }
    factory { UserMapper() }
    factory { UserProfileChecker() }
}