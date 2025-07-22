package com.example.androidassignment.data.repository

import android.util.Log
import com.example.androidassignment.data.local.CachedItemDao
import com.example.androidassignment.data.local.buildItemTreeByEntityId
import com.example.androidassignment.data.local.flattenWithParent
import com.example.androidassignment.data.remote.ItemApiService
import com.example.androidassignment.data.remote.dto.toDomain
import com.example.androidassignment.domain.model.Item
import com.example.androidassignment.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val itemApiService: ItemApiService,
    private val cachedItemDao: CachedItemDao
) : ItemRepository {

    override suspend fun getHierarchy(): Result<List<Item>> = withContext(Dispatchers.IO) {
        try {
            val remoteDto = itemApiService.fetchItem()

            val domainItem = remoteDto.toDomain()

            val domainItemPage = domainItem as? Item.Page

            if (domainItemPage != null) {
                cachedItemDao.clearAllItems()
                val entities = domainItemPage.items.flatMap { it.flattenWithParent(null) }
                cachedItemDao.insertItems(entities)
                Log.d("RepoResult", "RepoResult success: ${domainItemPage.items}")
                Result.success(domainItemPage.items)
            } else {
                Log.d("RepoResult", "RepoResult failed: ${domainItemPage}")
                Result.failure(IllegalStateException("API root item could not be mapped to a domain Page object, or it was not a Page."))
            }
        } catch (e: IOException) {
            val cached = cachedItemDao.getAllItems()
            return@withContext if (cached.isNotEmpty()) {
                val tree = buildItemTreeByEntityId(cached)
                Result.success(tree)
            } else {
                Result.failure(IOException("Network error AND no cache available: ${e.message}", e))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Failed to fetch or parse hierarchy: ${e.message}", e))
        }
    }
}