package com.example.controlbluetooth.data

import androidx.room.*
import com.example.controlbluetooth.model.Codes
import kotlinx.coroutines.flow.Flow


@Dao
interface CodesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(codes: Codes)

    @Update
    suspend fun update(codes: Codes)

    @Delete
    suspend fun delete(codes: Codes)

    @Query("SELECT * FROM codes_database")
    fun getCodes(): Flow<List<Codes>>

    @Query("SELECT * FROM codes_database WHERE id = :id")
    fun getCode(id: Int): Flow<Codes>

}