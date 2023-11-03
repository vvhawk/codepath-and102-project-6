package com.example.emojo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), EntryFragment.OnMoodEntryListener
{


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)

// Load default fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, EntryFragment())
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_entry -> {
                    val fragment = EntryFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
                    true
                }
                R.id.navigation_dashboard -> {
                    val fragment = DashboardFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
                    true
                }
                else -> false
            }
        }


    }


    // Step 4.1: Implement the method from the interface.
    override fun onMoodEntryAdded(moodEntry: MoodEntry) {
        // Step 4.2: Respond to the new mood. This is where you'd typically handle the mood entry,
        // for instance, save it to a database, or pass it to another fragment.



// Save the moodEntry to the database directly from here
        lifecycleScope.launch(Dispatchers.IO) {
            val database = MoodDatabase.getDatabase(this@MainActivity)
            database.moodDao().insert(moodEntry)
        }


    }




}