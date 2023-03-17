package com.example.basearchitectureproject.di

import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.person.usecases.PersonsUseCase
import com.example.basearchitectureproject.person.usecases.SyncRemoteDataUseCase
import com.example.basearchitectureproject.person.usecases.UploadPersonUseCase
import com.example.basearchitectureproject.user_details.usecase.GetAllUserDetailsUsesCase
import com.example.basearchitectureproject.user_details.usecase.UploadUserDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepository
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @ViewModelScoped
    @Provides
    fun providePersonsUseCase(
        personRepository: PersonRepository
    ): PersonsUseCase {
        return PersonsUseCase(
            personRepository,
            Dispatchers.IO
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUploadUserDetailsUseCase(
        uploadUserDetailsRepository: UploadUserDetailsRepository,
    ): UploadUserDetailsUseCase {
        return UploadUserDetailsUseCase(
            uploadUserDetailsRepository = uploadUserDetailsRepository
        )
    }


    @ViewModelScoped
    @Provides
    fun provideGetAllUserDetailsUsesCase(
        uploadUserDetailsRepository: UploadUserDetailsRepository,
    ): GetAllUserDetailsUsesCase {
        return GetAllUserDetailsUsesCase(
            uploadUserDetailsRepository = uploadUserDetailsRepository
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUploadPersonUseCase(
        personRepository: PersonRepository,
    ): UploadPersonUseCase {
        return UploadPersonUseCase(
            personRepository = personRepository
        )
    }

    @ViewModelScoped
    @Provides
    fun provideSyncRemoteDataUseCase(
        personRepository: PersonRepository,
    ): SyncRemoteDataUseCase {
        return SyncRemoteDataUseCase(
            personRepository = personRepository
        )
    }
}