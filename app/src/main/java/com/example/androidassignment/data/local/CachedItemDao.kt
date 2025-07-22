package com.example.androidassignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<CachedItemEntity>)

    @Query("SELECT * FROM cached_item")
    suspend fun getAllItems(): List<CachedItemEntity>

    @Query("SELECT * FROM cached_item WHERE parent_entity_id IS NULL")
    suspend fun getRootItems(): List<CachedItemEntity>

    @Query("DELETE FROM cached_item")
    suspend fun clearAllItems()
}
