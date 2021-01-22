package dev.msfjarvis.login

interface LoginUi {
    fun showProgressView()

    fun hideLoginButton()

    fun showUsernameError()

    fun hideProgressView()

    fun showLoginButton()

    fun showPasswordError()
}
