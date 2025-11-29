package com.vsrg.simple

data class Note(
    val lane: Int,           // 0-3 for 4 lanes
    val spawnTime: Long,     // When the note was spawned
    var y: Float = 0f,       // Current Y position on screen
    var isHit: Boolean = false,
    var isMissed: Boolean = false
)

enum class HitResult {
    PERFECT,
    GOOD,
    MISS
}
