package com.example.basearchitectureproject.di.repositry

import com.example.basearchitectureproject.login.newtwork.LoginApi
import com.example.basearchitectureproject.login.repositry.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {

    @Provides
    fun loginRespositeryProvider(
        providerLoginApi: LoginApi
    ): UserRepository {
        return UserRepository(providerLoginApi)
    }

}