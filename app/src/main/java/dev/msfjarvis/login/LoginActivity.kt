package dev.msfjarvis.login

import android.os.Bundle
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.spotify.mobius.Connection
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import dev.msfjarvis.counter.viewBinding
import dev.msfjarvis.mobius.RealSchedulerProvider
import dev.msfjarvis.mobiusdemo.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity(), LoginUiActions, LoginUi {

    private val viewRenderer = LoginViewRenderer(this)
    private val schedulerProvider = RealSchedulerProvider()
    private val loginEffectHandler =
        LoginEffectHandler(this, FakeLoginApi(), FakePreferences(), schedulerProvider).create()
    private val loopFactory = RxMobius.loop(LoginUpdate(), loginEffectHandler)
    private val controller = MobiusAndroid.controller(loopFactory, LoginModel.default())
    private val binding by viewBinding(LoginActivityBinding::inflate)
    private var passwordTextWatcher: TextWatcher? = null
    private var usernameTextWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            controller.replaceModel(savedInstanceState.getParcelable("model")!!)
        }
        controller.connect { eventConsumer ->
            object : Connection<LoginModel> {
                override fun dispose() {
                    binding.login.setOnClickListener(null)
                    binding.passwordEditText.removeTextChangedListener(passwordTextWatcher)
                    binding.usernameEditText.removeTextChangedListener(usernameTextWatcher)
                }

                override fun accept(value: LoginModel) {
                    viewRenderer.render(value)
                    binding.login.setOnClickListener {
                        eventConsumer.accept(LoginEvent.LoginButtonClicked(
                            Username(binding.usernameEditText.text?.toString().orEmpty()),
                            Password(binding.passwordEditText.text?.toString().orEmpty()),
                        ))
                    }
                    passwordTextWatcher = binding.passwordEditText.doOnTextChanged { _, _, _, _ ->
                        eventConsumer.accept(LoginEvent.PasswordEntered)
                    }
                    usernameTextWatcher = binding.usernameEditText.doOnTextChanged { _, _, _, _ ->
                        eventConsumer.accept(LoginEvent.UsernameEntered)
                    }
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("model", controller.model)
    }

    override fun showLoginErrorToastMessage() {
        Toast.makeText(this, "Failed to login", Toast.LENGTH_LONG).show()
    }

    override fun navigateToProfilePage() {
    }

    override fun showProgressView() {
        binding.progressView.isVisible = true
    }

    override fun hideLoginButton() {
        binding.login.isVisible = false
    }

    override fun showUsernameError() {
        binding.usernameInputLayout.error = "Invalid username"
    }

    override fun hideProgressView() {
        binding.progressView.isVisible = false
    }

    override fun showLoginButton() {
        binding.login.isVisible = true
    }

    override fun showPasswordError() {
        binding.passwordInputLayout.error = "Invalid password"
    }

    override fun clearUsernameError() {
        binding.usernameInputLayout.error = null
    }

    override fun clearPasswordError() {
        binding.passwordInputLayout.error = null
    }
}
