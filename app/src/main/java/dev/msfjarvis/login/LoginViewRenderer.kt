package dev.msfjarvis.login

class LoginViewRenderer(
    private val loginUi: LoginUi,
) {
    fun render(model: LoginModel) {
        loginUi.hideLoginButton()
        loginUi.showProgressView()
    }
}
