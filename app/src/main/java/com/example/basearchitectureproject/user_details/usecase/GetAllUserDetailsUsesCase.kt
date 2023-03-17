package com.example.basearchitectureproject.user_details.usecase

import com.example.basearchitectureproject.data.UserDetailsModel
import com.example.basearchitectureproject.util.usecase.FlowUseCase
import g5.consultency.cuitalibilam.base.util.Resource
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class GetAllUserDetailsUsesCase constructor(
    private val uploadUserDetailsRepository: UploadUserDetailsRepository
) : FlowUseCase<String, Resource<UserDetailsModel?>>(Dispatchers.IO) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(parameters: String) = callbackFlow {
        if (parameters != "") {
            uploadUserDetailsRepository.getUserDetails().collect {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        return@collect
//                    }
//                    Resource.Status.SUCCESS -> {
//                        SharedHelper.putKey(
//                            context = context,
//                            AppConstants.USER_NAME,
//                            it.data?.userName ?: ""
//                        )
//                        SharedHelper.putKey(
//                            context = context,
//                            AppConstants.USER_PROFILE_IMAGE_URL,
//                            it.data?.userImageURL ?: ""
//                        )
//                    }
//                }
                send(it)
            }
        } else {
            send(Resource.error(message = "Server error, Place an bug fix request", data = null))
        }
        awaitClose { }
    }
}