package dev.msfjarvis.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update

class LoginUpdate : Update<LoginModel, LoginEvent, LoginEffects> {
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffects> {
        return when (event) {
            LoginEvent.LoginButtonClicked -> {
                next(model.loginInProgress(), setOf(LoginEffects.ValidateCredentials))
            }
            is LoginEvent.ValidationFailure -> {
                next(model.validationFailed(event.errors))
            }
            LoginEvent.ValidationSuccess -> {
                dispatch(setOf(LoginEffects.LoginUser(model.username, model.password)))
            }
            LoginEvent.UsernameEntered -> {
                next(model.clearUsernameError())
            }
            LoginEvent.PasswordEntered -> {
                next(model.clearPasswordError())
            }
            is LoginEvent.LoginSuccess -> {
                dispatch(setOf(LoginEffects.SaveAuthToken(event.authToken), LoginEffects.OpenProfileScreen))
            }
            LoginEvent.LoginFailure -> {
                next(model.loginCompleted(), setOf(LoginEffects.ShowLoginError))
            }
        }
    }
}
