package dev.msfjarvis.login

interface LoginUiActions {
    fun showLoginErrorToastMessage()

    fun navigateToProfilePage()

    fun clearUsernameError()

    fun clearPasswordError()
}
