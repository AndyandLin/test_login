package com.example.test_login.data

import com.example.test_login.data.model.LoggedInUserEntity
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUserEntity> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUserEntity(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}