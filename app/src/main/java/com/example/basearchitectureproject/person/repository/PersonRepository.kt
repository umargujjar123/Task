package com.example.basearchitectureproject.person.repository

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.basearchitectureproject.base.BaseApplication.Companion.context
import com.example.basearchitectureproject.data.UserDetailsModel
import com.example.basearchitectureproject.data.localdb.UserDetailsModelDao
import com.google.firebase.firestore.FirebaseFirestore
import g5.consultency.cuitalibilam.base.util.Resource
import g5.consultency.cuitalibilam.helper.AppConstants
import g5.consultency.cuitalibilam.helper.SharedHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface PersonRepository {
    fun loadPersons(): Flow<List<UserDetailsModel>>
    suspend fun loadPersonsRemote(): Flow<Resource<Int?>>
    fun loadPersonsLocal(): Flow<Resource<List<UserDetailsModel>>>
    suspend fun addPerson(person: UserDetailsModel): Flow<Resource<Int?>>
}

class DefaultPersonRepository @Inject constructor(
    private val userDetailsModelDao: UserDetailsModelDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PersonRepository {
    private var personListCache: List<UserDetailsModel>? = null
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun loadPersons() = userDetailsModelDao.getAllPersons()

    override suspend fun loadPersonsRemote(): Flow<Resource<Int?>> = callbackFlow {
        send(
            Resource.loading(
                data = null,
            )
        )
        db.collection("UserDetails").get()
            .addOnSuccessListener {
                launch {
                    SharedHelper.putKey(
                        context = context,
                        AppConstants.IS_DATA_SYNCED, "YES"
                    )
                    val userList = it.toObjects(UserDetailsModel::class.java)
                    userList.forEach { userDetail ->
                        userDetailsModelDao.insertPersonAsync(userDetail)
                    }
                    send(
                        Resource.success(
                            data = null,
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

    override fun loadPersonsLocal() = callbackFlow {
        Log.e("TAG", "loadPersonsLocal: called")
        send(Resource.loading(null))
        personListCache?.let { personList ->
            Log.e("TAG", "loadPersonsLocal: $personList")
            send(Resource.success(personList, message = "Data Restored Successfully"))
        }

        delay(2000)

        withContext(dispatcher) {
            loadPersons().collect {
                Log.e("TAG", "loadPersonsLocal: $it")

                personListCache = it
                send(Resource.success(it, message = "Data Restored Successfully"))
            }
        }

        awaitClose { }
    }

    override suspend fun addPerson(userDetailsModel: UserDetailsModel):
            Flow<Resource<Int?>> = callbackFlow {
        send(Resource.loading(null))
        launch(Dispatchers.IO) {
            userDetailsModelDao.insertPersonAsync(userDetailsModel)

//                delay(5000)
//                if (!resultReceived)
//                    send(Resource.error("Login timeout", data = null))
        }


        db.collection("UserDetails")
            .document()
            .set(userDetailsModel)
            .addOnSuccessListener {
                launch(Dispatchers.IO) {
                    send(
                        Resource.success(
                            data = null,
                            message = "User Details Uploaded Successfully."
                        )
                    )
                }
            }.addOnFailureListener {
                launch(Dispatchers.IO) {
                    send(Resource.failed(data = null, message = "User Details Upload Failed."))
                }
            }
        awaitClose { }

    }
}