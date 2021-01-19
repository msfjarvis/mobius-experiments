package dev.msfjarvis.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginUpdateTest {

    private val initialModel = LoginModel.default()
    private val updateSpec = UpdateSpec(LoginUpdate())

    @Test
    fun `when login button is clicked, then validate credentials`() {
        updateSpec
            .given(initialModel)
            .whenEvent(LoginEvent.LoginButtonClicked)
            .then(assertThatNext(
                hasModel(initialModel.loginInProgress()),
                hasEffects(LoginEffects.ValidateCredentials),
            ))
    }

    @Test
    fun `when credential validation fails, then show errors`() {
        val validationErrors = listOf(ValidationError.InvalidPassword)
        val model = initialModel
            .enteredCredentials(username = "simple", password = "")
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
            .enteredCredentials(username = "simple", password = "simple")
            .loginInProgress()

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.ValidationSuccess)
            .then(assertThatNext(
                hasNoModel(),
                hasEffects(LoginEffects.LoginUser(username = "simple", password = "simple"))
            ))
    }

    @Test
    fun `when username is entered, then username error is cleared`() {
        val model = initialModel
            .validationFailed(listOf(ValidationError.InvalidPassword, ValidationError.InvalidUsername))
            .enteredCredentials(username = "simple", password = "")

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
            .enteredCredentials(username = "", password = "simple")

        updateSpec
            .given(model)
            .whenEvent(LoginEvent.PasswordEntered)
            .then(assertThatNext(
                hasModel(model.clearPasswordError()),
                hasNoEffects(),
            ))
    }
}
