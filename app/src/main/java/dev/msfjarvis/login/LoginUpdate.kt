package dev.msfjarvis.login

import com.spotify.mobius.Effects
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update

class LoginUpdate : Update<LoginModel, LoginEvent, LoginEffects> {
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffects> {
        return next(model.loginInProgress(), Effects.effects(LoginEffects.ValidateCredentials))
    }
}
