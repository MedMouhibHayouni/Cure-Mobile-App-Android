package com.example.cure;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.iwgang.countdownview.CountdownView;

public class RappelActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i = 0;
    EditText  date_time_in;
    RelativeLayout rellayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel);

        rellayout=findViewById(R.id.reltemmedic);
        date_time_in=findViewById(R.id.date_time_input);


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        String Newligne = System.getProperty("line.separator");
        TextView textViewDate = findViewById(R.id.clocktime);
        textViewDate.setText(currentDate + Newligne + currentTime);


        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mCountDownTimer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                i++;
                mProgressBar.setProgress((int) i * 100 / (5000 / 1000));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
            }
        };

        mCountDownTimer.start();

        RelativeLayout mrelativebtn = findViewById(R.id.relativbtn);


        mrelativebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerDialog= new DatePickerFragment();
                datePickerDialog.show(getSupportFragmentManager(),"date picker");

            }
        });

        rellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date_time_in);
            }
        });
        date_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date_time_in);
            }
        });
    }


    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(RappelActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(RappelActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    @Override
    public void onDateSet(DatePicker  view, int year, int month, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);





        String pickerDateString=DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String pickerTimeString=DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        TextView tvDatePicker=findViewById(R.id.textViewContent);
        CountdownView myCountDownView= findViewById(R.id.countdownview);

        try {
            tvDatePicker.setText("Date: "+pickerDateString+"  Temps: "+pickerTimeString);
            Date now = new Date();

            long currentDate=now.getTime();
            long pickerDate= calendar.getTimeInMillis();
            long countDownToPickerDate= pickerDate - currentDate;
            myCountDownView.start(countDownToPickerDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
