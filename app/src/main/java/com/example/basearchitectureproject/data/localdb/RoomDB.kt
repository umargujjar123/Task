package com.example.basearchitectureproject.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.basearchitectureproject.data.UserDetailsModel
import com.example.basearchitectureproject.util.DATABASE_VERSION

@Database(
    entities = [UserDetailsModel::class],
    version = DATABASE_VERSION, exportSchema = false
)

abstract class RoomDB : RoomDatabase() {
    abstract fun personDao(): UserDetailsModelDao

    companion object {
        // to void duplicate instances of DB
        @Volatile
        private var instance: RoomDB? = null
        fun init(context: Context, useInMemoryDb: Boolean = false): RoomDB {
            if (instance != null && !useInMemoryDb) {
                return instance!!
            }
            synchronized(this) {
                instance = if (useInMemoryDb) {
                    Room.inMemoryDatabaseBuilder(context, RoomDB::class.java)
                } else {
                    Room.databaseBuilder(context, RoomDB::class.java,"base_architecture_database.db")
                }.fallbackToDestructiveMigration()
                    .build()
                return instance!!
            }
        }
    }
}