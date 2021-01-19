package dev.msfjarvis.login

sealed class LoginEffects {
    data class LoginUser(val username: String, val password: String) : LoginEffects()
    data class ValidateCredentials(val username: String, val password: String) : LoginEffects()
    data class SaveAuthToken(val authToken: String) : LoginEffects()
    object OpenProfileScreen : LoginEffects()
    object ShowLoginError : LoginEffects()
}
