package com.example.emojo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {


    private val entries: MutableList<MoodEntry> = mutableListOf()

    private lateinit var moodAdapter: MoodRecyclerViewAdapter  // Declare here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up your RecyclerView and any other UI interactions here.


        val recyclerView: RecyclerView = view.findViewById(R.id.rvMoods)
        moodAdapter = MoodRecyclerViewAdapter(entries)
        recyclerView.adapter = moodAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())  // This sets a vertical linear layout for your RecyclerView





        lifecycleScope.launch(Dispatchers.IO) {
            // Retrieve all mood entries
            MoodDatabase.getDatabase(requireContext()).moodDao().getAllEntries().collect { allEntriesFromDB ->
                // Update the entries list on the main thread
                withContext(Dispatchers.Main) {
                    entries.clear()
                    entries.addAll(allEntriesFromDB)
                    moodAdapter.notifyDataSetChanged()
                }
            }
        }


    }


    fun addMoodEntry(moodEntry: MoodEntry) {
        // Step 5.1: Update the fragment with the new mood entry.
        // Here, add the moodEntry to your list and then update the RecyclerView to display the new entry.

        // Launch a coroutine to insert the new mood entry and then retrieve all entries
        lifecycleScope.launch(Dispatchers.IO) {
            // Insert the new mood entry

            MoodDatabase.getDatabase(requireContext()).moodDao().insert(moodEntry)

            // Retrieve all mood entries
            MoodDatabase.getDatabase(requireContext()).moodDao().getAllEntries()
                .collect { allEntries ->
                    // Update the entries list on the main thread
                    withContext(Dispatchers.Main) {
                        entries.clear()
                        entries.addAll(allEntries)
                        moodAdapter.notifyDataSetChanged()
                    }
                    moodAdapter.notifyDataSetChanged()
                }
        }
    }

}
