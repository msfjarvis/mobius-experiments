package dev.msfjarvis.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginUpdateTest {

    private val initialModel = LoginModel.default()
    private val updateSpec = UpdateSpec(LoginUpdate())

    @Test
    fun `clicking login button sets off login started effect`() {
        updateSpec
            .given(initialModel)
            .whenEvent(LoginEvent.LoginButtonClicked)
            .then(assertThatNext(
                hasModel(initialModel.loginInProgress()),
                hasEffects(LoginEffects.LoginStarted),
            ))
    }
}
