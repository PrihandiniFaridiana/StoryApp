package bangkit.android.submission13.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import bangkit.android.submission13.data.Condition
import bangkit.android.submission13.data.dataStore.UserToken
import bangkit.android.submission13.databinding.ActivityLoginBinding
import bangkit.android.submission13.view.ViewModelFactory
import bangkit.android.submission13.view.main.MainActivity
import bangkit.android.submission13.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        startAnimation()

        btnLoginClicked()
        btnRegisterClicked()

    }

    private fun btnLoginClicked() {
        binding.btnLogin.setOnClickListener {
            val email = binding.ciLoginEmail.text.toString()
            val password = binding.ciLoginPassword.text.toString()

            if (email.isEmpty()) {
                binding.ciLoginEmail.error = "Email can't be empty!"
            }
            if (password.isEmpty()) {
                binding.ciLoginPassword.error = "Password can't be empty!"
            }
            if (password.length < 8) {
                binding.ciLoginPassword.error = "Password must contain at least 8 character"
            }
            if (password.isNotEmpty() && email.isNotEmpty()) {
                loginViewModel.login(email, password).observe(this) { condition ->
                    when(condition) {
                        is Condition.Success -> {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                            val userToken = UserToken(
                                condition.data.loginResult.token,
                                condition.data.loginResult.name.toString(),
                                condition.data.loginResult.userId.toString(),
                                true
                            )
                            loginViewModel.saveToken(userToken)
                        }
                        is Condition.Error -> {
                            Toast.makeText(this, "Login Success ${condition.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun btnRegisterClicked() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun startAnimation() {
        val tvWelcome = ObjectAnimator.ofFloat(binding.tvWelcomeLogin, View.ALPHA, 1f).setDuration(700)
        val ivLogin = ObjectAnimator.ofFloat(binding.ivLogin, View.ALPHA, 1f).setDuration(700)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvLoginEmail, View.ALPHA, 1f).setDuration(700)
        val inputEmail = ObjectAnimator.ofFloat(binding.tilLoginEmail, View.ALPHA, 1f).setDuration(700)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvLoginPassword, View.ALPHA, 1f).setDuration(700)
        val inputPassword = ObjectAnimator.ofFloat(binding.tilLoginPassword, View.ALPHA, 1f).setDuration(700)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(700)
        val tvToRegister = ObjectAnimator.ofFloat(binding.tvToRegister, View.ALPHA, 1f).setDuration(700)
        val btnRegister = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(700)
        AnimatorSet().apply {
            playSequentially(
                tvWelcome,
                ivLogin,
                AnimatorSet().apply { playTogether(tvEmail, inputEmail) },
                AnimatorSet().apply { playTogether(tvPassword, inputPassword) },
                btnLogin,
                AnimatorSet().apply { playTogether(tvToRegister, btnRegister) }
            )
            start()
        }
    }
}