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
}
