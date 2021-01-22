package dev.msfjarvis.login

sealed class LoginEffect {
    data class LoginUser(val username: Username, val password: Password) : LoginEffect()
    data class ValidateCredentials(val username: Username, val password: Password) : LoginEffect()
    data class SaveAuthToken(val authToken: OAuthToken) : LoginEffect()
    object OpenProfileScreen : LoginEffect()
    object ShowLoginError : LoginEffect()
    data class ShowCredentialErrors(val errors: List<ValidationError>) : LoginEffect()
}
