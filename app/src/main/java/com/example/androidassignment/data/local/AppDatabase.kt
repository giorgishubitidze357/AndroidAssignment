package com.example.androidassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CachedItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cachedItemDao(): CachedItemDao
}
