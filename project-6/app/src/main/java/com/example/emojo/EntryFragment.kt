package com.example.emojo

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EntryFragment : Fragment() {

    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("MMM. dd, yyyy", Locale.US)
    private var select = 3


    // Step 1.1: Define the interface.
    interface OnMoodEntryListener {
        fun onMoodEntryAdded(moodEntry: MoodEntry)  // Method to be called when a new mood is added.
    }

    // Step 1.2: Create a variable to hold a reference to the listener.
    private var listener: OnMoodEntryListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Step 2.1: Check if the activity (context) implements our interface.
        if (context is OnMoodEntryListener) {
            listener = context  // If yes, assign it to our listener variable.
        } else {
            // If not, throw an exception to alert the developer.
            throw RuntimeException("$context must implement OnMoodEntryListener")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up your UI interactions here, e.g. SeekBar listener, Button click, etc.
        var seeker = view.findViewById<SeekBar>(R.id.seeker)
        var date = view.findViewById<TextView>(R.id.tvDate)
        var emoji = view.findViewById<ImageView>(R.id.emoji)
        var ring = view.findViewById<ImageView>(R.id.ring)
        val setMood = view.findViewById<Button>(R.id.btnSet)








        setMood.setOnClickListener {

            Log.d("MY_APP_TAG", "tapped")

            // Step 3.1: Create a mood entry object from user input.
            val moodEntry = MoodEntry(select = select, date = date.text.toString())

            // Step 3.2: Use the listener to send this data to the parent activity.
            listener?.onMoodEntryAdded(moodEntry)


        }



        date.setOnClickListener{
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    // Update the TextView with the selected date
                    calendar.set(year, month, dayOfMonth)
                    date.text = formatter.format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }




        seeker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {

                sad_happy(progress, seeker, emoji)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)
            {

                return
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {

                return
            }

        })

    }





    fun sad_happy(progress: Int, seeker: SeekBar, emoji: ImageView)
    {
        if(progress == 0)
        {
            emoji.setImageResource(R.drawable.sad3)
            seeker.performHapticFeedback(4)
            select = 0


        }
        else if(progress == 1)
        {
            emoji.setImageResource(R.drawable.sad2)
            seeker.performHapticFeedback(4)
            select = 1
        }
        else if(progress == 2)
        {
            emoji.setImageResource(R.drawable.sad)
            seeker.performHapticFeedback(4)
            select = 2
        }
        else if (progress == 3)
        {
            emoji.setImageResource(R.drawable.mid)
            seeker.performHapticFeedback(4)
            select = 3
        }
        else if(progress == 4)
        {
            emoji.setImageResource(R.drawable.smile)
            seeker.performHapticFeedback(4)
            select = 4
        }
        else if(progress == 5)
        {
            emoji.setImageResource(R.drawable.smile2)
            seeker.performHapticFeedback(4)
            select = 5
        }
        else if(progress == 6)
        {
            emoji.setImageResource(R.drawable.smile3)
            seeker.performHapticFeedback(4)
            select = 6
        }
    }
}
