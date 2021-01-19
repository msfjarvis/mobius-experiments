package dev.msfjarvis.login

data class LoginModel(
    val username: String,
    val password: String,
    val loginInProgress: Boolean,
) {

    fun loginInProgress(): LoginModel {
        return copy(loginInProgress = true)
    }

    companion object {
        fun default(): LoginModel {
            return LoginModel("", "", false)
        }
    }
}
