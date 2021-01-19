package dev.msfjarvis.login

sealed class LoginEvent {
    object LoginButtonClicked : LoginEvent()
}
