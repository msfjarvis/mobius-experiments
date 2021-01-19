package dev.msfjarvis.login

sealed class LoginEvent {
    object LoginButtonClicked : LoginEvent()
    data class ValidationFailure(val errors: List<ValidationError>) : LoginEvent()
    object ValidationSuccess : LoginEvent()
    object UsernameEntered : LoginEvent()
    object PasswordEntered : LoginEvent()
    data class LoginSuccess(val authToken: String) : LoginEvent()
    object LoginFailure: LoginEvent()
}
