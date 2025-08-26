package com.example.moodmuseplus.meditation

import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import com.google.android.material.appbar.MaterialToolbar

class MeditationActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null
    private var isRunning = false
    private var remainingMs = 60_000L
    private var inhalePhase = true
    private lateinit var pulseAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meditation)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val tvTimer = findViewById<TextView>(R.id.tv_timer)
        val tvPhase = findViewById<TextView>(R.id.tv_phase)
        val btnStartPause = findViewById<Button>(R.id.btn_start_pause)
        val btnReset = findViewById<Button>(R.id.btn_reset)

        // Load breathing pulse animation (XML)
        pulseAnim = AnimationUtils.loadAnimation(this, R.anim.breathe_pulse).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    inhalePhase = true
                    tvPhase.text = getString(R.string.meditation_phase_inhale)
                }
                override fun onAnimationRepeat(animation: Animation?) {
                    inhalePhase = !inhalePhase
                    tvPhase.text = if (inhalePhase)
                        getString(R.string.meditation_phase_inhale)
                    else
                        getString(R.string.meditation_phase_exhale)
                }
                override fun onAnimationEnd(animation: Animation?) { /* no-op */ }
            })
        }

        fun updateTimerText(ms: Long) {
            val totalSec = (ms / 1000).toInt()
            val m = totalSec / 60
            val s = totalSec % 60
            tvTimer.text = String.format("%02d:%02d", m, s)
        }
        updateTimerText(remainingMs)

        fun start() {
            if (isRunning) return
            isRunning = true
            btnStartPause.text = getString(R.string.pause)
            findViewById<TextView>(R.id.breathing_ball).startAnimation(pulseAnim)

            timer = object : CountDownTimer(remainingMs, 1000) {
                override fun onTick(msLeft: Long) {
                    remainingMs = msLeft
                    updateTimerText(msLeft)
                }
                override fun onFinish() {
                    isRunning = false
                    remainingMs = 0L
                    updateTimerText(0L)
                    btnStartPause.text = getString(R.string.start)
                    findViewById<TextView>(R.id.breathing_ball).clearAnimation()
                }
            }.start()
        }

        fun pause() {
            if (!isRunning) return
            isRunning = false
            btnStartPause.text = getString(R.string.start)
            timer?.cancel()
            findViewById<TextView>(R.id.breathing_ball).clearAnimation()
        }

        fun reset() {
            pause()
            remainingMs = 60_000L
            updateTimerText(remainingMs)
            inhalePhase = true
            tvPhase.text = getString(R.string.meditation_phase_inhale)
        }

        btnStartPause.setOnClickListener { if (isRunning) pause() else start() }
        btnReset.setOnClickListener { reset() }
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
