package dev.msfjarvis.login

sealed class LoginEffects {
    object ValidateCredentials : LoginEffects()
}
