package dev.msfjarvis.login

sealed class LoginEvent {
    data class LoginButtonClicked(val username: Username, val password: Password) : LoginEvent()
    data class ValidationResult(val errors: List<ValidationError>) : LoginEvent()
    object UsernameEntered : LoginEvent()
    object PasswordEntered : LoginEvent()
    data class LoginSuccess(val authToken: OAuthToken) : LoginEvent()
    object LoginFailure: LoginEvent()
}
