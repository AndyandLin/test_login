package com.example.test_login.data.repository


import com.example.test_login.data.dao.BrainHealthDao
import com.example.test_login.data.model.BrainHealthEntity
import com.example.test_login.network.api.ApiService
import com.example.test_login.network.model.response.BrainHealthRequest
import com.example.test_login.network.model.response.BrainHealthResponse
import retrofit2.Response
import javax.inject.Inject

class BrainHealthRepository @Inject constructor(
    private val brainHealthDao: BrainHealthDao,
    private val apiService: ApiService
) {
    suspend fun submitBrainHealth(request: BrainHealthRequest): Result<BrainHealthResponse> {
        return try {
            val response: Response<BrainHealthResponse> = apiService.submitBrainHealth(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveBrainHealth(entity: BrainHealthEntity) {
        brainHealthDao.insert(entity)
    }

    suspend fun getAllBrainHealth(): List<BrainHealthEntity> {
        return brainHealthDao.getAll()
    }
}