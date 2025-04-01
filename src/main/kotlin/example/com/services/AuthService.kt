package example.com.services

import example.com.repositories.AuthRepository

class AuthService (private val authRepository: AuthRepository) {
    suspend fun signUpUser(emailAddress: String, passwordKey: String) {
        authRepository.signUpUser(emailAddress, passwordKey)
    }

    suspend fun signInUser(email: String, password: String) {
        authRepository.signInUser(email, password)
    }

    fun getUserId(): String? {
        return authRepository.userId
    }
}