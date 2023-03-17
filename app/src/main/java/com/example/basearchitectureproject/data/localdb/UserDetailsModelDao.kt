package com.example.basearchitectureproject.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basearchitectureproject.data.UserDetailsModel
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailsModelDao {
    @Query("SELECT * FROM user_table ORDER BY email DESC")
    fun getPersons(): Flowable<List<UserDetailsModel>>

    @Query("SELECT * FROM user_table ORDER BY email DESC")
    fun getAllPersons(): Flow<List<UserDetailsModel>>

    @Query("SELECT * FROM user_table WHERE email == :email LIMIT 1")
    fun getPerson(email: Long): Flow<UserDetailsModel>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetailsModel: UserDetailsModel)


//    fun insertPerson(userDetailsModel: UserDetailsModel){
//        insert(userDetailsModel)
//    }
//
    suspend fun insertPersonAsync(person: UserDetailsModel){
        insert(person)
    }
}