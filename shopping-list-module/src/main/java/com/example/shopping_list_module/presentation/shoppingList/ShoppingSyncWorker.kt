package com.example.shopping_list_module.presentation.shoppingList

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shopping_list_module.domain.usecases.SyncShoppingItemsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class ShoppingSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncShoppingItemsUseCase: SyncShoppingItemsUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            syncShoppingItemsUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
