package com.example.basearchitectureproject.person.usecases

import com.example.basearchitectureproject.data.UserDetailsModel
import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.util.usecase.FlowUseCase
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class UploadPersonUseCase constructor(
    private val personRepository: PersonRepository,
) : FlowUseCase<UserDetailsModel, Resource<Int?>>(Dispatchers.IO) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(parameters: UserDetailsModel) = callbackFlow {
        personRepository.addPerson(parameters)
            .collect { uploadPersonResponse ->
                send(uploadPersonResponse)
            }
        awaitClose { }
    }
}