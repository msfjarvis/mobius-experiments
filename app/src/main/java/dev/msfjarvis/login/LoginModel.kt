package dev.msfjarvis.login

data class LoginModel(
    val username: String,
    val password: String,
    val loginInProgress: Boolean,
    val validationErrors: List<ValidationError>,
) {

    fun loginInProgress(): LoginModel {
        return copy(loginInProgress = true)
    }

    fun validationFailed(validationErrors: List<ValidationError>): LoginModel {
        return copy(loginInProgress = false, validationErrors = validationErrors)
    }

    fun enteredCredentials(username: String, password: String): LoginModel {
        return copy(username = username, password = password)
    }

    fun clearUsernameError(): LoginModel {
        return copy(validationErrors = validationErrors - ValidationError.InvalidUsername)
    }

    fun clearPasswordError(): LoginModel {
        return copy(validationErrors = validationErrors - ValidationError.InvalidPassword)
    }

    fun loginCompleted(): LoginModel {
        return copy(loginInProgress = false)
    }

    companion object {
        fun default(): LoginModel {
            return LoginModel(
                username = "",
                password = "",
                loginInProgress = false,
                validationErrors = emptyList(),
            )
        }
    }
}
