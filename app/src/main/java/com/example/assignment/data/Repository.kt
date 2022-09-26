package com.example.assignment.data

import com.example.assignment.api.Service
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class Repository @Inject constructor(
    private val service: Service,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getList(): List<Item> {
        return withContext(ioDispatcher) {
            service.getListItems()
        }
    }
}