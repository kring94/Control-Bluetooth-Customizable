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

    @Query("DELETE FROM codes_database")
    suspend fun deleteAll()

    @Query("SElECT button_enabled FROM codes_database WHERE code_image = :code_image")
    fun getEnableState(code_image: Int): Flow<Boolean>

    @Query("SELECT code_button FROM codes_database")
    fun getCodeLetters(): Flow<List<String>>

    @Query("SELECT code_image FROM codes_database")
    fun getCodeImages(): Flow<List<Int>>

    @Query("SELECT * FROM codes_database")
    fun getCodes(): Flow<List<Codes>>

    @Query("SELECT * FROM codes_database WHERE code_image = :code_image")
    fun getCode(code_image: Int): Flow<Codes>

}