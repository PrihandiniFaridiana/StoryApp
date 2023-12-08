package bangkit.android.submission13.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import bangkit.android.submission13.data.Condition
import bangkit.android.submission13.databinding.ActivityRegisterBinding
import bangkit.android.submission13.view.ViewModelFactory
import bangkit.android.submission13.view.login.LoginActivity


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


            startAnimation()


        btnRegisterClicked()

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun btnRegisterClicked() {
        binding.btnRegister.setOnClickListener {
            val name = binding.ciRegisterName.text.toString()
            val email = binding.ciRegisterEmail.text.toString()
            val password = binding.ciRegisterPassword.text.toString()
            if (name.isEmpty()) {
                binding.ciRegisterName.error = "Name can't be empty!"
            }
            if (email.isEmpty()) {
                binding.ciRegisterEmail.error = "Email can't be empty!"
            }
            if (name.isEmpty()) {
                binding.ciRegisterPassword.error = "Password can't be empty!"
            }
            if (password.length < 8) {
                binding.ciRegisterPassword.error = "Password must contain at least 8 character"
            }
            if (password.isNotEmpty() && email.isNotEmpty() && name.isNotEmpty()) {
               registerViewModel.register(name, email, password).observe(this) { condition ->
                   when(condition) {
                       is Condition.Success -> {
                           Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
                           startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                       }
                       is Condition.Error -> {
                           Toast.makeText(this, "Registration Failed: ${condition.error}", Toast.LENGTH_SHORT).show()
                       }
                   }
               }
            }
        }
    }

    private fun startAnimation() {
        val tvWelcome = ObjectAnimator.ofFloat(binding.tvWelcomeRegister, View.ALPHA, 1f).setDuration(700)
        val ivRegister = ObjectAnimator.ofFloat(binding.ivRegister, View.ALPHA, 1f).setDuration(700)
        val tvName = ObjectAnimator.ofFloat(binding.tvRegisterName, View.ALPHA, 1f).setDuration(700)
        val inputName = ObjectAnimator.ofFloat(binding.tilRegisterName, View.ALPHA, 1f).setDuration(700)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvRegisterEmail, View.ALPHA, 1f).setDuration(700)
        val inputEmail = ObjectAnimator.ofFloat(binding.tilRegisterEmail, View.ALPHA, 1f).setDuration(700)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvRegisterPassword, View.ALPHA, 1f).setDuration(700)
        val inputPassword = ObjectAnimator.ofFloat(binding.tilRegisterPassword, View.ALPHA, 1f).setDuration(700)
        val tvToLogin = ObjectAnimator.ofFloat(binding.tvToLogin, View.ALPHA, 1f).setDuration(700)
        val btnLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(700)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(700)
        AnimatorSet().apply {
            playSequentially(
                tvWelcome,
                ivRegister,
                AnimatorSet().apply { playTogether(tvName, inputName) },
                AnimatorSet().apply { playTogether(tvEmail, inputEmail) },
                AnimatorSet().apply { playTogether(tvPassword, inputPassword) },
                btnRegister,
                AnimatorSet().apply { playTogether(tvToLogin, btnLogin) },
            )
            start()
        }
    }
}