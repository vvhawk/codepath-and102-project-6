package com.example.emojo



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var select: Int,
    var date: String
)



