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
        model.validationErrors.forEach {
            when (it) {
                is ValidationError.InvalidUsername -> {
                    loginUi.showUsernameError()
                }
            }
        }
    }
}
