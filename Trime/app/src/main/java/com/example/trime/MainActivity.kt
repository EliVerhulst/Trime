package com.example.trime

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import android.view.View;
import android.widget.TimePicker;
import android.app.TimePickerDialog
import android.app.DatePickerDialog
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val timePicker1: TimePicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val timeButton = findViewById<Button>(R.id.timeButton);
         val testText = findViewById<TextView>(R.id.testText);
        timeButton.setOnClickListener{
            val now = Calendar.getInstance();

            val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = (selectedDate.time)
                Toast.makeText(this,"date : " + date ,Toast.LENGTH_SHORT).show()
            },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))


            datepicker.show();

        }
    }
}
