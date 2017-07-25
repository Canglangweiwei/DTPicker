package com.cyun.dtpicker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.wheel.StrericWheelAdapter;
import com.wheel.WheelView;

import java.util.Calendar;

/**
 * 2016.09.06，删除秒的选择<br/>
 * 用Activity，不用AlertDialog<br/>
 * 对话框样式的activity<br/>
 */
@SuppressWarnings("ALL")
public class TimeActivity extends Activity implements View.OnClickListener {

    private int minYear = 2015;  //最小年份
    private int fontSize = 14;     //字体大小
    private WheelView yearWheel, monthWheel, dayWheel, hourWheel, minuteWheel;
    public static String[] yearContent = null;
    public static String[] monthContent = null;
    public static String[] dayContent = null;
    public static String[] hourContent = null;
    public static String[] minuteContent = null;
    public static String[] secondContent = null;

    private TextView dtp_sure; // 确定
    private TextView dtp_cancel; // 取消

    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        intent = getIntent();

        dtp_sure = (TextView) findViewById(R.id.dtp_sure);
        dtp_cancel = (TextView) findViewById(R.id.dtp_cancel);
        dtp_sure.setOnClickListener(this);
        dtp_cancel.setOnClickListener(this);

        yearWheel = (WheelView) findViewById(R.id.yearwheel);
        monthWheel = (WheelView) findViewById(R.id.monthwheel);
        dayWheel = (WheelView) findViewById(R.id.daywheel);
        hourWheel = (WheelView) findViewById(R.id.hourwheel);
        minuteWheel = (WheelView) findViewById(R.id.minutewheel);

        initContent();

        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);

        yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
        yearWheel.setCurrentItem(curYear - minYear);
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
    }

    /**
     * 初始化时间范围
     */
    private void initContent() {
        yearContent = new String[20];
        for (int i = 0; i < 20; i++) {
            yearContent[i] = String.valueOf(i + minYear);
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dtp_sure:
                StringBuffer sb = new StringBuffer();
                sb.append(yearWheel.getCurrentItemValue()).append("-")
                        .append(monthWheel.getCurrentItemValue()).append("-")
                        .append(dayWheel.getCurrentItemValue());

                sb.append(" ");
                sb.append(hourWheel.getCurrentItemValue())
                        .append(":").append(minuteWheel.getCurrentItemValue())
                        .append(":").append("00");// secondWheel.getCurrentItemValue()

                intent.putExtra("value", sb.toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case R.id.dtp_cancel:
                finish();
                break;
        }
    }
}