package dev.msfjarvis.login

sealed class LoginEvent {
    object LoginButtonClicked : LoginEvent()
    data class ValidationFailure(val errors: List<ValidationError>) : LoginEvent()
    object ValidationSuccess : LoginEvent()
}
