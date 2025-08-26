package com.example.moodmuseplus.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.moodmuseplus.R
import com.example.moodmuseplus.onboarding.Onboarding1Activity
// or: import com.example.moodmuseplus.auth.AuthActivity
// or: import com.example.moodmuseplus.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val motion = findViewById<MotionLayout>(R.id.motion_root)

        // Safety: if autoTransition doesn’t fire for any reason, trigger it.
        motion.post { if (motion.progress == 0f) motion.transitionToEnd() }

        motion.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(layout: MotionLayout, currentId: Int) {
                if (currentId == R.id.end) {
                    // Decide where to go: onboarding/auth/home
                    startActivity(Intent(this@SplashActivity, Onboarding1Activity::class.java))
                    finish()
                }
            }
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }
}
