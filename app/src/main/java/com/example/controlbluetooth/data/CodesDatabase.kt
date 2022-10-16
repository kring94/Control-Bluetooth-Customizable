package com.example.controlbluetooth.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.controlbluetooth.model.Codes

@Database(entities = [Codes::class], version = 4, exportSchema = false)
abstract class CodesDatabase: RoomDatabase() {
    abstract fun codesDao(): CodesDao

    companion object {
        @Volatile
        private var INSTANCE: CodesDatabase? = null
        fun getDatabase(context: Context): CodesDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CodesDatabase::class.java,
                    "Codes_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}