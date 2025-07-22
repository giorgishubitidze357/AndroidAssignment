package com.example.androidassignment.domain.usecase

import com.example.androidassignment.domain.repository.ItemRepository
import javax.inject.Inject

class GetHierarchyUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke() = itemRepository.getHierarchy()
}