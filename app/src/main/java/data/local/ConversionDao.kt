package com.example.converta.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ConversionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversion(conversion: ConversionEntity)

    @Query("SELECT * FROM conversions ORDER BY id DESC")
    suspend fun getAllConversions(): List<ConversionEntity>
}
