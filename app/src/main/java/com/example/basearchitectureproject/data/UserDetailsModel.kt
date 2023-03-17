package com.example.basearchitectureproject.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.auto.value.AutoValue
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_table", primaryKeys = ["email"])
data class UserDetailsModel @JvmOverloads constructor(
    @ColumnInfo var email: String = "",
    @ColumnInfo var userName: String? = "",
    @ColumnInfo var userMobileNumber: String? = "",
): Serializable

