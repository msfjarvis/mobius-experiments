package dev.msfjarvis.login

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

class LoginViewRendererTest {
    private val loginUi = mock<LoginUi>()
    private val viewRenderer = LoginViewRenderer(loginUi)

    @Test
    fun `when login in progress, then show progress view`() {
        // given
        val model = LoginModel.default().loginInProgress()

        // when
        viewRenderer.render(model)

        // then
        verify(loginUi).hideLoginButton()
        verify(loginUi).showProgressView()
        verifyNoMoreInteractions(loginUi)
    }

    @Test
    fun `when username validation fails, then show error message`() {
        // given
        val validationErrors = listOf(ValidationError.InvalidUsername)
        val model = LoginModel.default().validationFailed(validationErrors)

        // when
        viewRenderer.render(model)

        // then
        verify(loginUi).hideProgressView()
        verify(loginUi).showLoginButton()
        verify(loginUi).showUsernameError()
        verifyNoMoreInteractions(loginUi)
    }

    @Test
    fun `when password validation fails, then show error message`() {
        // given
        val validationErrors = listOf(ValidationError.InvalidPassword)
        val model = LoginModel.default().validationFailed(validationErrors)

        // when
        viewRenderer.render(model)

        // then
        verify(loginUi).hideProgressView()
        verify(loginUi).showLoginButton()
        verify(loginUi).showPasswordError()
        verifyNoMoreInteractions(loginUi)
    }

    @Test
    fun `when username and password validation fail, then show error message`() {
        // given
        val validationErrors = listOf(ValidationError.InvalidPassword, ValidationError.InvalidUsername)
        val model = LoginModel.default().validationFailed(validationErrors)

        // when
        viewRenderer.render(model)

        // then
        verify(loginUi).hideProgressView()
        verify(loginUi).showLoginButton()
        verify(loginUi).showPasswordError()
        verify(loginUi).showUsernameError()
        verifyNoMoreInteractions(loginUi)
    }
}
