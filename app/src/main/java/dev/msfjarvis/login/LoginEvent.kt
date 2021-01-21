package dev.msfjarvis.login

sealed class LoginEvent {
    object LoginButtonClicked : LoginEvent()
    data class ValidationResult(val errors: List<ValidationError>) : LoginEvent()
    object UsernameEntered : LoginEvent()
    object PasswordEntered : LoginEvent()
    data class LoginSuccess(val authToken: OAuthToken) : LoginEvent()
    object LoginFailure: LoginEvent()
}
