package com.example.test_login.network.api

import com.example.test_login.data.model.TaskEntity
import com.example.test_login.network.model.response.BrainHealthRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.test_login.network.model.response.BrainHealthResponse

/**
 * API 服務介面
 * 定義所有網路請求端點
 */
interface ApiService {
    @POST("checkuserid/")
    suspend fun checkUserId(@Body userIdRequest: UserIdRequest): Response<CheckUserIdResponse>

    @POST("register/")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("login/login/")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("FormInitialReview/")
    suspend fun submitInitialReview(@Body reviewRequest: InitialReviewRequest): Response<ReviewResponse>

    @POST("FormPsyRef/")
    suspend fun submitPsyRef(@Body psyRefRequest: PsyRefRequest): Response<PsyRefResponse>

    @POST("FormCHM/")
    suspend fun submitCHM(@Body chmRequest: CHMRequest): Response<CHMResponse>

    @POST("FormResourceReferrals/")
    suspend fun submitResourceReferrals(@Body resourceRequest: ResourceRequest): Response<ResourceResponse>

    @POST("FormRenewalCHM/")
    suspend fun submitRenewalCHM(@Body renewalRequest: RenewalRequest): Response<RenewalResponse>

    @POST("FormHomeCareRecord/")
    suspend fun submitHomeCareRecord(@Body homeCareRequest: HomeCareRequest): Response<HomeCareResponse>

    @POST("FormHighRiskDischarged/")
    suspend fun submitHighRiskDischarged(@Body dischargedRequest: HighRiskDischargedRequest): Response<HighRiskDischargedResponse>

    @POST("FormCloseCaseCHM/")
    suspend fun submitCloseCaseCHM(@Body closeCaseRequest: CloseCaseRequest): Response<CloseCaseResponse>

    @POST("FormCloseCase/")
    suspend fun submitCloseCase(@Body closeCaseRequest: CloseCaseRequest): Response<CloseCaseResponse>

    @POST("FormBrainHealth/")
    suspend fun submitBrainHealth(@Body request: BrainHealthRequest): Response<BrainHealthResponse>
    @POST("tasks/create")
    suspend fun createTask(@Body taskEntity: TaskEntity): Response<TaskEntity>

    @POST("tasks/batch-sync")
    suspend fun syncTasks(@Body taskEntities: List<TaskEntity>): Response<List<TaskEntity>>
}

/**
 * 回應資料類別
 */
open class BaseResponse(
    open val status: String,
    open val message: String
)

data class CheckUserIdResponse(
    val isAvailable: Boolean
) : BaseResponse("", "")

data class RegisterResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class LoginResponse(
    val responseStatus: String,
    val responseMessage: String?
) : BaseResponse(responseStatus, responseMessage ?: "")

data class ReviewResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class PsyRefResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class CHMResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class ResourceResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class RenewalResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class HomeCareResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class HighRiskDischargedResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class CloseCaseResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

data class BrainHealthResponse(
    val responseStatus: String,
    val responseMessage: String
) : BaseResponse(responseStatus, responseMessage)

/**
 * 請求資料類別
 */
data class UserIdRequest(
    val userId: String
)

data class RegisterRequest(
    val userId: String,
    val email: String,
    val password: String,
    val hospitalCode: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

// 表單請求類別
data class InitialReviewRequest(
    val patientId: String,
    val reviewDate: String,
    val reviewData: Map<String, Any>
)

data class PsyRefRequest(
    val patientId: String,
    val referralDate: String,
    val referralData: Map<String, Any>
)

data class CHMRequest(
    val patientId: String,
    val chmData: Map<String, Any>
)

data class ResourceRequest(
    val patientId: String,
    val resourceData: Map<String, Any>
)

data class RenewalRequest(
    val patientId: String,
    val renewalData: Map<String, Any>
)

data class HomeCareRequest(
    val patientId: String,
    val careData: Map<String, Any>
)

data class HighRiskDischargedRequest(
    val patientId: String,
    val dischargeData: Map<String, Any>
)

data class CloseCaseRequest(
    val patientId: String,
    val closeData: Map<String, Any>
)