package dev.msfjarvis.login

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.nhaarman.mockitokotlin2.doThrow
import dev.msfjarvis.mobius.EffectHandlerTestCase
import dev.msfjarvis.mobius.TestSchedulerProvider
import org.junit.After
import org.junit.Ignore
import org.junit.Test

class LoginEffectHandlerTest {

    private val scheduler = TestSchedulerProvider()
    private val loginUiActions = mock<LoginUiActions>()
    private val loginApi = mock<LoginApi>()
    private val preferences = mock<Preferences>()
    private val effectHandler = LoginEffectHandler(loginUiActions, loginApi, preferences, scheduler).create()
    private val effectHandlerTestCase = EffectHandlerTestCase(effectHandler)

    @After
    fun tearDown() {
        effectHandlerTestCase.dispose()
    }

    @Test
    fun `when show error effect is received, then show error`() {
        // when
        effectHandlerTestCase.dispatch(LoginEffect.ShowLoginError)

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
        effectHandlerTestCase.dispatch(LoginEffect.ValidateCredentials(username, password))

        // then
        effectHandlerTestCase.assertOutgoingEvents(LoginEvent.ValidationResult(validationErrors))
        verifyNoMoreInteractions(loginUiActions)
    }

    @Test
    fun `when credential validation fails, then show errors`() {
        // given
        val validationErrors = listOf(ValidationError.InvalidUsername, ValidationError.InvalidPassword)

        // when
        effectHandlerTestCase.dispatch(LoginEffect.ShowCredentialErrors(validationErrors))

        // then
        effectHandlerTestCase.assertNoOutgoingEvents()
        verify(loginUiActions).showCredentialErrors(validationErrors)
        verifyNoMoreInteractions(loginUiActions)
    }

    @Test
    fun `when open profile page effect is receive, then navigate to profile page`() {
        // when
        effectHandlerTestCase.dispatch(LoginEffect.OpenProfileScreen)

        // then
        effectHandlerTestCase.assertNoOutgoingEvents()
        verify(loginUiActions).navigateToProfilePage()
        verifyNoMoreInteractions(loginUiActions)
    }

    @Test
    fun `when login user effect is received, then make an API call`() {
        // given
        val username = Username("simple")
        val password = Password("simple")
        val authToken = OAuthToken("authentication-token")

        whenever(loginApi.login(username, password)).thenReturn(authToken)

        // when
        effectHandlerTestCase.dispatch(LoginEffect.LoginUser(username, password))

        // then
        effectHandlerTestCase.assertOutgoingEvents(LoginEvent.LoginSuccess(authToken))
    }

    @Test
    @Ignore("RXJava is hard")
    fun `when login failure event is received, then show error`() {
        // given
        val username = Username("simple")
        val password = Password("simple")

        whenever(loginApi.login(username, password)).doThrow(Throwable("login failure"))

        // when
        effectHandlerTestCase.dispatch(LoginEffect.LoginUser(username, password))

        // then
        effectHandlerTestCase.assertOutgoingEvents(LoginEvent.LoginFailure)
    }

    @Test
    fun `when save auth token effect is received, then persist token to storage`() {
        // given
        val authToken = OAuthToken("authentication-token")

        // when
        effectHandlerTestCase.dispatch(LoginEffect.SaveAuthToken(authToken))

        // then
        effectHandlerTestCase.assertNoOutgoingEvents()
        verify(preferences).putString("oauth_token", authToken.token)
        verifyNoMoreInteractions(preferences)
    }
}
