package dev.msfjarvis.login

sealed class LoginEffects {
    data class LoginUser(val username: String, val password: String) : LoginEffects()
    object ValidateCredentials : LoginEffects()
}
