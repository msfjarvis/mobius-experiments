package dev.msfjarvis.login

interface LoginUiActions {
    fun showLoginErrorToastMessage()

    fun showCredentialErrors(errors: List<ValidationError>)

    fun navigateToProfilePage()
}
