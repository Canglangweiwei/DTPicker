package com.cyun.dtpicker;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.cyun.dialog.DateType;
import com.cyun.dialog.TimePickerDialog;
import com.wheel.StrericWheelAdapter;
import com.wheel.WheelView;

import java.util.Calendar;

/**
 * 2016.09.06，删除秒的选择
 */
public class MainActivity extends Activity {

    private WheelView yearWheel, monthWheel, dayWheel, hourWheel, minuteWheel; // , secondWheel;
    public static String[] yearContent = null;
    public static String[] monthContent = null;
    public static String[] dayContent = null;
    public static String[] hourContent = null;
    public static String[] minuteContent = null;
    public static String[] secondContent = null;

    private TextView time_tv;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        time_tv = (TextView) findViewById(R.id.time_tv);
        initContent();
    }

    public void initContent() {
        yearContent = new String[10];
        for (int i = 0; i < 10; i++)
            yearContent[i] = String.valueOf(i + 2013);

        monthContent = new String[12];
        for (int i = 0; i < 12; i++) {
            monthContent[i] = String.valueOf(i + 1);
            if (monthContent[i].length() < 2) {
                monthContent[i] = "0" + monthContent[i];
            }
        }

        dayContent = new String[31];
        for (int i = 0; i < 31; i++) {
            dayContent[i] = String.valueOf(i + 1);
            if (dayContent[i].length() < 2) {
                dayContent[i] = "0" + dayContent[i];
            }
        }
        hourContent = new String[24];
        for (int i = 0; i < 24; i++) {
            hourContent[i] = String.valueOf(i);
            if (hourContent[i].length() < 2) {
                hourContent[i] = "0" + hourContent[i];
            }
        }

        minuteContent = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteContent[i] = String.valueOf(i);
            if (minuteContent[i].length() < 2) {
                minuteContent[i] = "0" + minuteContent[i];
            }
        }
        secondContent = new String[60];
        for (int i = 0; i < 60; i++) {
            secondContent[i] = String.valueOf(i);
            if (secondContent[i].length() < 2) {
                secondContent[i] = "0" + secondContent[i];
            }
        }
    }

    /**
     * 按钮点击事件
     *
     * @param v 视图
     */
    public void buttonClick1(View v) {
        int id = v.getId();
        if (id == R.id.pick_bt1) {
            View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);

            Calendar calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR);
            int curMonth = calendar.get(Calendar.MONTH) + 1;
            int curDay = calendar.get(Calendar.DAY_OF_MONTH);
            int curHour = calendar.get(Calendar.HOUR_OF_DAY);
            int curMinute = calendar.get(Calendar.MINUTE);

            yearWheel = (WheelView) view.findViewById(R.id.yearwheel);
            monthWheel = (WheelView) view.findViewById(R.id.monthwheel);
            dayWheel = (WheelView) view.findViewById(R.id.daywheel);
            hourWheel = (WheelView) view.findViewById(R.id.hourwheel);
            minuteWheel = (WheelView) view.findViewById(R.id.minutewheel);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            builder.setCancelable(false);

            yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
            yearWheel.setCurrentItem(curYear - 2013);
            yearWheel.setCyclic(true);
            yearWheel.setInterpolator(new AnticipateOvershootInterpolator());


            monthWheel.setAdapter(new StrericWheelAdapter(monthContent));
            monthWheel.setCurrentItem(curMonth - 1);
            monthWheel.setCyclic(true);
            monthWheel.setInterpolator(new AnticipateOvershootInterpolator());

            dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
            dayWheel.setCurrentItem(curDay - 1);
            dayWheel.setCyclic(true);
            dayWheel.setInterpolator(new AnticipateOvershootInterpolator());

            hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
            hourWheel.setCurrentItem(curHour);
            hourWheel.setCyclic(true);
            hourWheel.setInterpolator(new AnticipateOvershootInterpolator());

            minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
            minuteWheel.setCurrentItem(curMinute);
            minuteWheel.setCyclic(true);
            minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());
            builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(yearWheel.getCurrentItemValue()).append("-")
                            .append(monthWheel.getCurrentItemValue()).append("-")
                            .append(dayWheel.getCurrentItemValue());

                    sb.append(" ");
                    sb.append(hourWheel.getCurrentItemValue())
                            .append(":").append(minuteWheel.getCurrentItemValue())
                            .append(":").append("00");
                    time_tv.setText(sb);
                    dialog.cancel();
                }
            });
            if (!isFinishing())
                builder.show();
        }
    }

    /**
     * 按钮点击事件
     *
     * @param v 视图
     */
    public void buttonClick2(View v) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this);
        timePickerDialog.setDateType(DateType.TIME);
        timePickerDialog.setListener(new TimePickerDialog.OnTimePickerDialogListener() {
            @Override
            public void onTimeSelect(String timeSelect) {
                time_tv.setText(timeSelect);
            }

            @Override
            public void onErrorDate(String errorDate) {
                Toast.makeText(MainActivity.this, errorDate + "是无效日期", Toast.LENGTH_SHORT).show();
            }
        });
        if (!isFinishing())
            timePickerDialog.show();
    }

    public void buttonClick3(View view) {
        TimePickerDialog pickerDialog = new TimePickerDialog(this);
        pickerDialog.setDateType(DateType.DATE);
        pickerDialog.setListener(new TimePickerDialog.OnTimePickerDialogListener() {
            @Override
            public void onTimeSelect(String timeSelect) {
                time_tv.setText(timeSelect);
            }

            @Override
            public void onErrorDate(String errorDate) {
                Toast.makeText(MainActivity.this, errorDate + "是无效日期", Toast.LENGTH_SHORT).show();
            }
        });
        if (!isFinishing())
            pickerDialog.show();
    }
}