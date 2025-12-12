package co.cristian.weatherbold

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import co.cristian.weatherbold.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash Screen with Lottie animation
 * Shows a welcome animation before navigating to MainActivity
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Ensure dark mode follows system settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLottieAnimation()
        navigateToMainAfterDelay()
    }

    private fun setupLottieAnimation() {
        binding.lottieAnimation.apply {
            // Animation is already configured in XML with lottie_rawRes and lottie_autoPlay
            // Just add listeners for debugging
            addLottieOnCompositionLoadedListener { composition ->
                Log.d("SplashActivity", "Lottie animation loaded successfully: ${composition.duration}ms")
            }

            setFailureListener { throwable ->
                Log.e("SplashActivity", "Lottie animation failed to load", throwable)
            }
        }
    }

    /**
     * Fallback: Try loading from URL if local animation fails
     */

    private fun navigateToMainAfterDelay() {
        lifecycleScope.launch {
            // Wait for most of the animation (2.3 seconds of 2.5 total)
            delay(ANIMATION_DURATION)
            
            // Fade out the entire splash screen
            binding.root.animate()
                .alpha(0f)
                .setDuration(FADE_OUT_DURATION)
                .withEndAction {
                    // Navigate to MainActivity after fade out completes
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    
                    // Smooth transition (using overrideActivityTransition for API 34+)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        overrideActivityTransition(
                            OVERRIDE_TRANSITION_OPEN,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
                .start()
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 2300L // Wait 2.3 seconds for Lottie animation
        private const val FADE_OUT_DURATION = 400L   // 400ms fade out effect
    }
}
