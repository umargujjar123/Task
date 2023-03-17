package com.example.basearchitectureproject.di

import com.example.basearchitectureproject.data.localdb.UserDetailsModelDao
import com.example.basearchitectureproject.person.repository.DefaultPersonRepository
import com.example.basearchitectureproject.person.repository.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepository
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepositoryImp

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providePersonRepository(userDetailsModelDao: UserDetailsModelDao) : PersonRepository {
        return DefaultPersonRepository(userDetailsModelDao)
    }

    @ViewModelScoped
    @Provides
    fun provideUploadUserDetailsRepository(): UploadUserDetailsRepository {
        return UploadUserDetailsRepositoryImp()
    }
}