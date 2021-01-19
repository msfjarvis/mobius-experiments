package dev.msfjarvis.login

sealed class LoginEffects {
    data class LoginUser(val username: Username, val password: Password) : LoginEffects()
    data class ValidateCredentials(val username: Username, val password: Password) : LoginEffects()
    data class SaveAuthToken(val authToken: String) : LoginEffects()
    object OpenProfileScreen : LoginEffects()
    object ShowLoginError : LoginEffects()
    data class ShowCredentialErrors(val errors: List<ValidationError>) : LoginEffects()
}
