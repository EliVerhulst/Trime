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
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false
    var timeFormat = SimpleDateFormat("HH:mm")       //zet het standaardformaat voor een datum
    var formate = SimpleDateFormat("YYYY-MM-dd")   //zet het standaardformaat voor tijd

    private val timePicker1: TimePicker? = null             // we maken een timepicker aan
    var datum = Date("01/01/2020")
    var uur = Date("01/01/2020")

    lateinit var context: Context
    lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val timeButton = findViewById<Button>(R.id.timeButton);

        context = this


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
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        confirm.setOnClickListener{
            //Dit deel zet steekt in een variabele "difference" het verschil tussen de 2 datums in seconden.
            val herinnering1 = Herinnering(datum, uur)
            val now = Calendar.getInstance();
            var koppel = formate.format(herinnering1.datum)
            koppel = koppel +"T" + timeFormat.format(herinnering1.uur)+":00.0000"
             val date1 = LocalDateTime.parse(koppel)

            val date2 = LocalDateTime.now()
            val difference = ChronoUnit.SECONDS.between(date2, date1)
            test1.text = difference.toInt().toString()

            val second = (difference.toString().toInt() * 1000)+1000

            val intent = Intent(context, Receiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.d("MainActivity", " Create : " + Date().toString())
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ second, pendingIntent)
        }



    }
    class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //alles dat je hier typt gebeurt of het aangeduidde moment
            Log.d("MainActivity", " Receiver : " + Date().toString())
        }
    }
    }

