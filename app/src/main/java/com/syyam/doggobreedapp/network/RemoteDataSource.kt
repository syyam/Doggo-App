package com.syyam.doggobreedapp.network

import com.syyam.doggobreedapp.model.breeds.Breeds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: MainActivityApi) {

    fun favoritesSortOrder() = flow {

        val breeds = Breeds()
        while (true) {
            val list = breeds.message.map { it.capitalize() }.shuffled().subList(0, 50)
            emit(list)
            delay(3000)
        }
    }
}
