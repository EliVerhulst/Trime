package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private int dateyear;
    private int datemonth;
    private int dateday;
    private int datehrs;
    private int dateminutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();



        //timepicker
        findViewById(R.id.choose_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        //datepicker
        findViewById(R.id.choose_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //Knop om het alarm te bevestigen
        Button set_alarm = findViewById(R.id.set_alarm);
        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Alarm set!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,Herinnering.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);

                AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);

                Calendar targetcalendar = Calendar.getInstance();
                targetcalendar.set (dateyear,datemonth,dateday,datehrs,dateminutes,00);
                Date finals = targetcalendar.getTime();

                Date datenow = new Date(System.currentTimeMillis());
                long currentTime = System.currentTimeMillis();

                long differenceInMillis = finals.getTime() - datenow.getTime();


                alarmManager.set(AlarmManager.RTC_WAKEUP,currentTime + differenceInMillis, pendingIntent);
            }
        });
    }
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),true

        );
        timePickerDialog.show();
    }

    private void createNotificationChannel(){
        //Hier wordt het channel aangemaakt voor de notificatie, deze wordt gelinkt met Herrinering.java via het ID "Notify"
        CharSequence name = "NotifyChannel";
        String description =  "Channel for notify";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("Notify", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "day/month/year: " + dayOfMonth + "/" + (month +1 )+ "/"+ year;

        dateyear = year;
        datemonth = (month);
        dateday = dayOfMonth;

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = "Hour:minutes: " + hourOfDay + ":" + minute;

        datehrs = hourOfDay;
        dateminutes = minute;

    }

}
