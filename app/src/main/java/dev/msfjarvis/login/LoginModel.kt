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
