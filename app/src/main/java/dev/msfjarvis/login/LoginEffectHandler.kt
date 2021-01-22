package dev.msfjarvis.login

import com.spotify.mobius.rx2.RxMobius
import dev.msfjarvis.mobius.SchedulerProvider
import io.reactivex.ObservableTransformer

class LoginEffectHandler(
    private val uiActions: LoginUiActions,
    private val loginApi: LoginApi,
    private val preferences: Preferences,
    private val schedulerProvider: SchedulerProvider,
) {

    fun create(): ObservableTransformer<LoginEffect, LoginEvent> {
        return RxMobius.subtypeEffectHandler<LoginEffect, LoginEvent>()
            .addAction(
                LoginEffect.ShowLoginError::class.java,
                uiActions::showLoginErrorToastMessage
            )
            .addTransformer(LoginEffect.ValidateCredentials::class.java, validateCredentials())
            .addConsumer(LoginEffect.ShowCredentialErrors::class.java, {
                uiActions.showCredentialErrors(it.errors)
            }, schedulerProvider.main)
            .addAction(
                LoginEffect.OpenProfileScreen::class.java,
                uiActions::navigateToProfilePage,
                schedulerProvider.main,
            )
            .addTransformer(LoginEffect.LoginUser::class.java, loginUser())
            .addConsumer(LoginEffect.SaveAuthToken::class.java, {
                preferences.putString("oauth_token", it.authToken.token)
            }, schedulerProvider.io)
            .build()
    }

    private fun loginUser(): ObservableTransformer<LoginEffect.LoginUser, LoginEvent> {
        return ObservableTransformer { effects ->
            effects
                .map { loginApi.login(it.username, it.password) }
                .map { LoginEvent.LoginSuccess(it) }
        }
    }

    private fun validateCredentials(): ObservableTransformer<LoginEffect.ValidateCredentials, LoginEvent> {
        return ObservableTransformer { effect ->
            effect
                .map { it.username.validate() + it.password.validate() }
                .map { LoginEvent.ValidationResult(it) }
        }
    }
}
