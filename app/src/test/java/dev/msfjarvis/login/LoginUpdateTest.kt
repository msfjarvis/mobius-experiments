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
}
