package com.example.androidassignment.di

import android.content.Context
import androidx.room.Room
import com.example.androidassignment.data.local.AppDatabase
import com.example.androidassignment.data.local.CachedItemDao
import com.example.androidassignment.data.remote.ItemApiService
import com.example.androidassignment.data.remote.dto.ItemDto
import com.example.androidassignment.data.remote.dto.PageDto
import com.example.androidassignment.data.remote.dto.QuestionImageDto
import com.example.androidassignment.data.remote.dto.QuestionTextDto
import com.example.androidassignment.data.remote.dto.SectionDto
import com.example.androidassignment.data.repository.ItemRepositoryImpl
import com.example.androidassignment.domain.repository.ItemRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(ItemDto::class.java, "type")
                    .withSubtype(PageDto::class.java, "page")
                    .withSubtype(SectionDto::class.java, "section")
                    .withSubtype(QuestionTextDto::class.java, "text")
                    .withSubtype(QuestionImageDto::class.java, "image")
            )
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideItemApi(moshi:Moshi): ItemApiService {
        return Retrofit.Builder()
            .baseUrl("https://mocki.io/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ItemApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCachedItemDao(db: AppDatabase): CachedItemDao = db.cachedItemDao()
}