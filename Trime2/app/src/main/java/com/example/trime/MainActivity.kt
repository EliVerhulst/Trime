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

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*


class MainActivity : AppCompatActivity() {
    var timeFormat = SimpleDateFormat("HH:mm")       //zet het standaardformaat voor een datum
    var formate = SimpleDateFormat("YYYY-MM-dd")   //zet het standaardformaat voor tijd

    private val timePicker1: TimePicker? = null             // we maken een timepicker aan
    var datum = Date("01/01/2020")
    var uur = Date("01/01/2020")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val timeButton = findViewById<Button>(R.id.timeButton);
        val confirm = findViewById<Button>(R.id.confirm);
        val test1 = findViewById<TextView>(R.id.Test1);
        val test2 = findViewById<TextView>(R.id.Test2);
        timeButton.setOnClickListener{              //onclicklistener initieren: iets doen wanneer er op de knop gedrukt wordt
            val now = Calendar.getInstance();       // steken de huidige tijd in een variabele

            val timepicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                val time = (selectedTime.time)
                uur = time

            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true)
            timepicker.show()
            val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = (selectedDate.time)
                datum = selectedDate.time
                //de datepicker opent, de gekozen tijd wordt in een variabele gestoken: selectedDate. In date wordt er een datum van gemaakt.

            },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datepicker.show();
            try {
                if(timeButton.text != "Kies Alarm") {
                    val date = timeFormat.parse(timeButton.text.toString())

                    now.time = date
                }
            }catch (e:Exception){
                e.printStackTrace()}


        }

        confirm.setOnClickListener{

            val herinnering1 = Herinnering(datum, uur)
            test1.text = formate.format(herinnering1.datum)
            test2.text = timeFormat.format(herinnering1.uur)
            val now = Calendar.getInstance();
            var koppel = formate.format(herinnering1.datum)
            koppel = koppel +"T" + timeFormat.format(herinnering1.uur)+":00.0000"
           val date1 = LocalDateTime.parse(koppel)
            test1.text = koppel
            val date2 = LocalDateTime.now()
            val difference = ChronoUnit.SECONDS.between(date2, date1)
            test2.text = difference.toString()
        }

    }
}
