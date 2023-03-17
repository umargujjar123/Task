package com.example.basearchitectureproject.person.usecases

import android.util.Log
import com.example.basearchitectureproject.data.UserDetailsModel
import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.util.usecase.FlowUseCase
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect


class PersonsUseCase constructor(
    private val personRepository: PersonRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, Resource<List<UserDetailsModel>>>(dispatcher) {

    override fun execute(parameters: Unit) = personRepository.loadPersonsLocal()


}