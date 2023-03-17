package com.example.basearchitectureproject.di.usecase


import com.example.basearchitectureproject.login.repositry.UserRepository
import com.example.basearchitectureproject.login.usecase.LoginUseCase
import com.example.basearchitectureproject.person.usecases.UploadPersonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseProvider {

    @ViewModelScoped
    @Provides
    fun loginUseCaseProvider(
        repository: UserRepository

    ): LoginUseCase {
        return LoginUseCase(
            repository,
            Dispatchers.IO
        )
    }
}
