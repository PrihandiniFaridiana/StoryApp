package bangkit.android.submission13.view.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import bangkit.android.submission13.databinding.ActivityWelcomeBinding
import bangkit.android.submission13.view.ViewModelFactory
import bangkit.android.submission13.view.login.LoginActivity
import bangkit.android.submission13.view.main.MainActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val welcomeViewModel by viewModels<WelcomeViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val delay: Long = 300
        Handler(Looper.getMainLooper()).postDelayed({
            welcomeViewModel.getToken().observe(this) {token ->
                if (!token.isLogin) {
                    startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                } else {
                    startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                }
            }
            finish()
        }, delay)
    }
}