package dev.msfjarvis.login

class LoginViewRenderer(
    private val loginUi: LoginUi,
) {
    fun render(model: LoginModel) {
        if (model.loginInProgress) {
            loginUi.hideLoginButton()
            loginUi.showProgressView()
        } else {
            loginUi.showLoginButton()
            loginUi.hideProgressView()
        }
        // We clear errors pre-emptively and then set them again if they are present
        loginUi.clearUsernameError()
        model.validationErrors.forEach {
            when (it) {
                is ValidationError.InvalidUsername -> {
                    loginUi.showUsernameError()
                }
                is ValidationError.InvalidPassword -> {
                    loginUi.showPasswordError()
                }
            }
        }
    }
}
