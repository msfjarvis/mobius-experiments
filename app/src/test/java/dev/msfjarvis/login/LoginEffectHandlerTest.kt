package dev.msfjarvis.login

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import dev.msfjarvis.mobius.EffectHandlerTestCase
import dev.msfjarvis.mobius.TestSchedulerProvider
import org.junit.Test

class LoginEffectHandlerTest {

    private val scheduler = TestSchedulerProvider()
    private val loginUiActions = mock<LoginUiActions>()
    private val effectHandler = LoginEffectHandler(loginUiActions, scheduler).create()
    private val effectHandlerTestCase = EffectHandlerTestCase(effectHandler)

    @Test
    fun `when show error effect is received, then show error`() {
        // when
        effectHandlerTestCase.dispatch(LoginEffects.ShowLoginError)

        // then
        effectHandlerTestCase.assertNoOutgoingEvents()
        verify(loginUiActions).showLoginErrorToastMessage()
        verifyNoMoreInteractions(loginUiActions)
    }

    @Test
    fun `when credential validation effect is received, then emit validation result`() {
        // given
        val username = Username.BLANK
        val password = Password.BLANK
        val validationErrors = listOf(ValidationError.InvalidUsername, ValidationError.InvalidPassword)

        // when
        effectHandlerTestCase.dispatch(LoginEffects.ValidateCredentials(username, password))

        // then
        effectHandlerTestCase.assertOutgoingEvents(LoginEvent.ValidationResult(validationErrors))
        verifyNoMoreInteractions(loginUiActions)
    }

    @Test
    fun `when credential validation fails, then show errors`() {
        // given
        val validationErrors = listOf(ValidationError.InvalidUsername, ValidationError.InvalidPassword)

        // when
        effectHandlerTestCase.dispatch(LoginEffects.ShowCredentialErrors(validationErrors))

        // then
        effectHandlerTestCase.assertNoOutgoingEvents()
        verify(loginUiActions).showCredentialErrors(validationErrors)
        verifyNoMoreInteractions(loginUiActions)
    }

    @Test
    fun `when open profile page effect is receive, then navigate to profile page`() {
        // when
        effectHandlerTestCase.dispatch(LoginEffects.OpenProfileScreen)

        // then
        effectHandlerTestCase.assertNoOutgoingEvents()
        verify(loginUiActions).navigateToProfilePage()
        verifyNoMoreInteractions(loginUiActions)
    }
}
