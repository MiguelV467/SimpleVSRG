package com.vsrg.simple

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import kotlin.math.abs

class GameView(context: Context) : View(context) {
    private val notes = mutableListOf<Note>()
    private val notePaint = Paint().apply {
        color = Color.parseColor("#ecf0f1")
        style = Paint.Style.FILL
    }
    
    private val noteSpeed = 1000f // pixels per second
    private val noteHeight = 40f
    private val hitLineY: Float
        get() = height - 120f // Above the keys
    
    private val perfectWindow = 50f // pixels
    private val goodWindow = 100f // pixels
    
    private var lastUpdateTime = System.currentTimeMillis()
    
    fun addNote(note: Note) {
        notes.add(note)
    }
    
    fun hasNotes(): Boolean = notes.isNotEmpty()
    
    fun update() {
        val currentTime = System.currentTimeMillis()
        val deltaTime = (currentTime - lastUpdateTime) / 1000f
        lastUpdateTime = currentTime
        
        // Update note positions
        notes.forEach { note ->
            if (!note.isHit && !note.isMissed) {
                // Calculate position based on time
                val timeAlive = currentTime - note.spawnTime
                note.y = hitLineY - (timeAlive * noteSpeed / 1000f)
                
                // Check if note passed hit line (auto miss)
                if (note.y > hitLineY + goodWindow) {
                    note.isMissed = true
                }
            }
        }
        
        // Remove old notes
        notes.removeAll { it.isHit || (it.isMissed && it.y > height) }
    }
    
    fun checkHit(lane: Int): HitResult {
        // Find closest note in the lane
        val activeNotes = notes.filter { 
            it.lane == lane && !it.isHit && !it.isMissed 
        }
        
        if (activeNotes.isEmpty()) return HitResult.MISS
        
        val closestNote = activeNotes.minByOrNull { abs(it.y - hitLineY) }
            ?: return HitResult.MISS
        
        val distance = abs(closestNote.y - hitLineY)
        
        return when {
            distance <= perfectWindow -> {
                closestNote.isHit = true
                HitResult.PERFECT
            }
            distance <= goodWindow -> {
                closestNote.isHit = true
                HitResult.GOOD
            }
            else -> HitResult.MISS
        }
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val laneWidth = width / 4f
        
        // Draw notes
        notes.forEach { note ->
            if (!note.isHit && !note.isMissed) {
                val left = note.lane * laneWidth + 10f
                val right = (note.lane + 1) * laneWidth - 10f
                val top = note.y - noteHeight / 2
                val bottom = note.y + noteHeight / 2
                
                canvas.drawRect(left, top, right, bottom, notePaint)
            }
        }
    }
}
