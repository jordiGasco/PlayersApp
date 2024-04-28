package ie.setu.playersapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ie.setu.playersapp.databinding.ActivitySplashScreenBinding




@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        // Showing splash screen for 5 seconds (5000ms), afterwards, it will start the welcome Activity.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, PlayerlistActivity::class.java) //crea la nueva pagina (o va  la otra mejor dicho)
            startActivity(intent)
            finish()
        }, 5000)


    }
}