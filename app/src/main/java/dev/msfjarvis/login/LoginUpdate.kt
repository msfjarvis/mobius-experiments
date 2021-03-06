package dev.msfjarvis.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update

class LoginUpdate : Update<LoginModel, LoginEvent, LoginEffect> {
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return when (event) {
            is LoginEvent.LoginButtonClicked -> {
                next(model.loginInProgress(), setOf(LoginEffect.ValidateCredentials(event.username, event.password)))
            }
            is LoginEvent.ValidationResult -> {
                if (event.errors.isEmpty())
                    dispatch(setOf(LoginEffect.LoginUser(model.username, model.password)))
                else
                    next(model.validationFailed(event.errors))
            }
            is LoginEvent.UsernameEntered -> {
                next(model.clearUsernameError(), setOf(LoginEffect.ClearUsernameError))
            }
            is LoginEvent.PasswordEntered -> {
                next(model.clearPasswordError(), setOf(LoginEffect.ClearPasswordError))
            }
            is LoginEvent.LoginSuccess -> {
                dispatch(setOf(LoginEffect.SaveAuthToken(event.authToken), LoginEffect.OpenProfileScreen))
            }
            LoginEvent.LoginFailure -> {
                next(model.loginCompleted(), setOf(LoginEffect.ShowLoginError))
            }
        }
    }
}
