package com.vsrg.simple

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var scoreText: TextView
    private lateinit var comboText: TextView
    private lateinit var gameArea: FrameLayout
    private lateinit var gameView: GameView
    private lateinit var keys: List<Button>
    
    private var score = 0
    private var combo = 0
    private var gameRunning = false
    
    private val handler = Handler(Looper.getMainLooper())
    private val gameUpdateRate = 16L // ~60 FPS
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupGame()
        startGame()
    }
    
    private fun initViews() {
        scoreText = findViewById(R.id.scoreText)
        comboText = findViewById(R.id.comboText)
        gameArea = findViewById(R.id.gameArea)
        
        keys = listOf(
            findViewById(R.id.key1),
            findViewById(R.id.key2),
            findViewById(R.id.key3),
            findViewById(R.id.key4)
        )
        
        // Setup key listeners
        keys.forEachIndexed { index, button ->
            button.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> handleKeyPress(index)
                    MotionEvent.ACTION_UP -> handleKeyRelease(index)
                }
                true
            }
        }
    }
    
    private fun setupGame() {
        gameView = GameView(this)
        gameArea.addView(gameView)
    }
    
    private fun startGame() {
        gameRunning = true
        score = 0
        combo = 0
        updateUI()
        
        // Generate test level (30 seconds)
        generateTestLevel()
        
        // Start game loop
        handler.post(gameLoop)
        
        // End game after 30 seconds
        handler.postDelayed({
            endGame()
        }, 30000)
    }
    
    private fun generateTestLevel() {
        val bpm = 120
        val beatInterval = 60000L / bpm // milliseconds per beat
        val totalBeats = (30000 / beatInterval).toInt() // 30 seconds
        
        // Generate simple pattern
        for (i in 0 until totalBeats) {
            val lane = when {
                i % 8 == 0 -> 0
                i % 8 == 2 -> 1
                i % 8 == 4 -> 2
                i % 8 == 6 -> 3
                else -> continue
            }
            
            val spawnTime = System.currentTimeMillis() + (i * beatInterval)
            gameView.addNote(Note(lane, spawnTime))
        }
    }
    
    private val gameLoop = object : Runnable {
        override fun run() {
            if (gameRunning) {
                gameView.update()
                gameView.invalidate()
                handler.postDelayed(this, gameUpdateRate)
            }
        }
    }
    
    private fun handleKeyPress(lane: Int) {
        val hitResult = gameView.checkHit(lane)
        
        when (hitResult) {
            HitResult.PERFECT -> {
                score += 100
                combo++
                keys[lane].alpha = 1.0f
            }
            HitResult.GOOD -> {
                score += 50
                combo++
                keys[lane].alpha = 1.0f
            }
            HitResult.MISS -> {
                combo = 0
                keys[lane].alpha = 0.5f
            }
        }
        
        updateUI()
    }
    
    private fun handleKeyRelease(lane: Int) {
        keys[lane].alpha = 1.0f
    }
    
    private fun updateUI() {
        scoreText.text = getString(R.string.score, score)
        comboText.text = getString(R.string.combo, combo)
    }
    
    private fun endGame() {
        gameRunning = false
        // Show final score
        android.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_over))
            .setMessage(getString(R.string.final_score, score))
            .setPositiveButton(getString(R.string.restart)) { _, _ ->
                recreate()
            }
            .setCancelable(false)
            .show()
    }
    
    override fun onPause() {
        super.onPause()
        gameRunning = false
    }
    
    override fun onResume() {
        super.onResume()
        if (gameView.hasNotes() && !gameRunning) {
            gameRunning = true
            handler.post(gameLoop)
        }
    }
}
