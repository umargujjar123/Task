package g5.consultency.cuitalibilam.user_details.repository

import com.example.basearchitectureproject.data.UserDetailsModel
import com.google.firebase.firestore.FirebaseFirestore
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch


interface UploadUserDetailsRepository {
//    suspend fun uploadUserDetails(uploadUserDetailsModel: UploadUserDetailsModel): Flow<Resource<UploadUserDetailsModel>>

    suspend fun getUserDetails(): Flow<Resource<UserDetailsModel?>>

}

class UploadUserDetailsRepositoryImp : UploadUserDetailsRepository {
    private var resultReceived: Boolean = false
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

//    override suspend fun uploadUserDetails(uploadUserDetailsModel: UploadUserDetailsModel):
//            Flow<Resource<UploadUserDetailsModel>> = callbackFlow {
//        send(Resource.loading(null))
//        launch(Dispatchers.IO) {
//            delay(5000)
//            if (!resultReceived)
//                send(Resource.error("Login timeout", data = null))
//        }
//
//
//        db.collection( "UserDetails")
//            .document("1")
//            .set(uploadUserDetailsModel.userDetails)
//            .addOnSuccessListener {
//                resultReceived = true
//                launch(Dispatchers.IO) {
//                    send(
//                        Resource.success(
//                            data = uploadUserDetailsModel,
//                            message = "User Details Uploaded Successfully."
//                        )
//                    )
//                }
//            }.addOnFailureListener {
//                resultReceived = true
//                launch(Dispatchers.IO) {
//                    send(Resource.failed(data = null, message = "User Details Upload Failed."))
//                }
//            }
//        awaitClose { }
//
//    }

    override suspend fun getUserDetails(): Flow<Resource<UserDetailsModel?>> =
        callbackFlow {
            db.collection("UserDetails").document("1").get()
                .addOnSuccessListener {
                    launch {
                        send(
                            Resource.success(
                                data = it.toObject(UserDetailsModel::class.java),
                                message = "Data Restored Successfully"
                            )
                        )
                    }
                }.addOnFailureListener {
                    Resource.error(
                        data = null, message = "Failed to Restore data "
                    )
                }

            awaitClose { }
        }


}