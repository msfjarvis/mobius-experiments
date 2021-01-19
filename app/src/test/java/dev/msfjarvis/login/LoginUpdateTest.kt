package dev.msfjarvis.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginUpdateTest {

    private val initialModel = LoginModel.default()
    private val updateSpec = UpdateSpec(LoginUpdate())
    private val validUsername = Username("simple")
    private val validPassword = Password("simple")

    @Test
    fun `when login button is clicked, then validate credentials`() {
        updateSpec
            .given(initialModel)
            .whenEvent(LoginEvent.LoginButtonClicked)
            .then(assertThatNext(
                hasModel(initialModel.loginInProgress()),
                hasEffects(LoginEffects.ValidateCredentials(initialModel.username, initialModel.password)),
            ))
    }

    @Test
    fun `when credential validation fails, then show errors`() {
        val validationErrors = listOf(ValidationError.InvalidPassword)
        val model = initialModel
            .enteredCredentials(username = validUsername, password = Password.BLANK)
            .loginInProgress()

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.ValidationFailure(validationErrors))
            .then(assertThatNext(
                hasModel(model.validationFailed(validationErrors)),
                hasNoEffects(),
            ))
    }

    @Test
    fun `when credential validation succeeds, then call login API`() {
        val model = initialModel
            .enteredCredentials(username = validUsername, password = validPassword)
            .loginInProgress()

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.ValidationSuccess)
            .then(assertThatNext(
                hasNoModel(),
                hasEffects(LoginEffects.LoginUser(username = validUsername, password = validPassword))
            ))
    }

    @Test
    fun `when username is entered, then username error is cleared`() {
        val model = initialModel
            .validationFailed(listOf(ValidationError.InvalidPassword, ValidationError.InvalidUsername))
            .enteredCredentials(username = validUsername, password = Password.BLANK)

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.UsernameEntered)
            .then(assertThatNext(
                hasModel(model.clearUsernameError()),
                hasNoEffects(),
            ))
    }

    @Test
    fun `when password is entered, then password error is cleared`() {
        val model = initialModel
            .validationFailed(listOf(ValidationError.InvalidPassword, ValidationError.InvalidUsername))
            .enteredCredentials(username = Username.BLANK, password = validPassword)

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.PasswordEntered)
            .then(assertThatNext(
                hasModel(model.clearPasswordError()),
                hasNoEffects(),
            ))
    }

    @Test
    fun `when login succeeds, then save auth token and open profile screen`() {
        val model = initialModel
            .enteredCredentials(username = validUsername, password = validPassword)
            .loginInProgress()
        val authToken = "auth_token"

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.LoginSuccess(authToken))
            .then(assertThatNext(
                hasNoModel(),
                hasEffects(LoginEffects.SaveAuthToken(authToken), LoginEffects.OpenProfileScreen)
            ))
    }

    @Test
    fun `when login fails, then show errors`() {
        val model = initialModel
            .enteredCredentials(username = validUsername, password = validPassword)
            .loginInProgress()

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.LoginFailure)
            .then(assertThatNext(
                hasModel(model.loginCompleted()),
                hasEffects(LoginEffects.ShowLoginError)
            ))
    }
}
