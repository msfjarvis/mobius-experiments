package dev.msfjarvis.login

sealed class ValidationError {
    object InvalidUsername : ValidationError()
    object InvalidPassword : ValidationError()
}
