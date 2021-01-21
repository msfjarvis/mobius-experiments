package dev.msfjarvis.login

import com.spotify.mobius.rx2.RxMobius
import dev.msfjarvis.mobius.SchedulerProvider
import io.reactivex.ObservableTransformer
import kotlin.math.log

class LoginEffectHandler(
    private val uiActions: LoginUiActions,
    private val loginApi: LoginApi,
    private val schedulerProvider: SchedulerProvider,
) {

    fun create(): ObservableTransformer<LoginEffects, LoginEvent> {
        return RxMobius.subtypeEffectHandler<LoginEffects, LoginEvent>()
            .addAction(
                LoginEffects.ShowLoginError::class.java,
                uiActions::showLoginErrorToastMessage
            )
            .addTransformer(LoginEffects.ValidateCredentials::class.java, validateCredentials())
            .addConsumer(LoginEffects.ShowCredentialErrors::class.java, {
                uiActions.showCredentialErrors(it.errors)
            }, schedulerProvider.main)
            .addAction(
                LoginEffects.OpenProfileScreen::class.java,
                uiActions::navigateToProfilePage,
                schedulerProvider.main,
            )
            .addTransformer(LoginEffects.LoginUser::class.java, loginUser())
            .build()
    }

    private fun loginUser(): ObservableTransformer<LoginEffects.LoginUser, LoginEvent> {
        return ObservableTransformer { effects ->
            effects
                .map { loginApi.login(it.username, it.password) }
                .map { LoginEvent.LoginSuccess(it) }
        }
    }

    private fun validateCredentials(): ObservableTransformer<LoginEffects.ValidateCredentials, LoginEvent> {
        return ObservableTransformer { effect ->
            effect
                .map { it.username.validate() + it.password.validate() }
                .map { LoginEvent.ValidationResult(it) }
        }
    }
}
