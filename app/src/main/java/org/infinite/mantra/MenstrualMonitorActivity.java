package org.infinite.mantra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MenstrualMonitorActivity extends AppCompatActivity {
    CalendarView menstrual_calendar;
    TextInputEditText menstrual_duration;
    Button calc_men_cycle_btn;
    TextView safe_period1, safe_period2, un_safe_period, next_period_starts;
    int lnmp_cycle_day;
    Calendar safeDaysCalendarStart, safeDays1CalendarEnd, safeDays2CalendarEnd, unsafeDaysCalendarStart, unsafeDaysCalendarEnd, nextPeriodStartCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menstrual_monitor);

        setActivityToolbar();
        initViews();
        clickActionEvent();
    }

    private void setActivityToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.toolbar_title_menstrual_cycle_monitor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        menstrual_calendar = findViewById(R.id.menstrual_calendar);
        menstrual_duration = findViewById(R.id.menstrual_duration);
        calc_men_cycle_btn = findViewById(R.id.calc_men_cycle_btn);

        safe_period1 = findViewById(R.id.safe_period1);
        safe_period2 = findViewById(R.id.safe_period2);
        un_safe_period = findViewById(R.id.un_safe_period);
        next_period_starts = findViewById(R.id.next_period_starts);
    }

    private void clickActionEvent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        menstrual_calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            safeDaysCalendarStart = Calendar.getInstance();
            safeDays1CalendarEnd = Calendar.getInstance();
            safeDays2CalendarEnd = Calendar.getInstance();
            unsafeDaysCalendarStart = Calendar.getInstance();
            unsafeDaysCalendarEnd = Calendar.getInstance();
            nextPeriodStartCalendar = Calendar.getInstance();

            safeDaysCalendarStart.set(year, month, dayOfMonth);
            safeDays1CalendarEnd.set(year, month, dayOfMonth);
            safeDays2CalendarEnd.set(year, month, dayOfMonth);
            unsafeDaysCalendarStart.set(year, month, dayOfMonth);
            unsafeDaysCalendarEnd.set(year, month, dayOfMonth);
            nextPeriodStartCalendar.set(year, month, dayOfMonth);

            lnmp_cycle_day = dayOfMonth;
            System.out.println(lnmp_cycle_day);
        });

        calc_men_cycle_btn.setOnClickListener(v -> {
            int cycleDuration = Integer.parseInt(Objects.requireNonNull(menstrual_duration.getText()).toString());

            int unsafeDaysStart = cycleDuration - 18;
            int unsafeDaysEnd = cycleDuration - 11;

//            int unsafeDaysCount = unsafeDaysStart - unsafeDaysEnd;

            int safeDays1 = unsafeDaysStart - 3;
            int unsafeDays = unsafeDaysEnd + 3;

            safeDays1CalendarEnd.add(Calendar.DAY_OF_MONTH, safeDays1);
            safeDays2CalendarEnd.add(Calendar.DAY_OF_MONTH, cycleDuration-1);
            unsafeDaysCalendarStart.add(Calendar.DAY_OF_MONTH, safeDays1);
            unsafeDaysCalendarEnd.add(Calendar.DAY_OF_MONTH, unsafeDays);
            nextPeriodStartCalendar.add(Calendar.DAY_OF_MONTH, cycleDuration-1);

            safe_period1.setText(dateFormat.format(safeDaysCalendarStart.getTime())+ " to " +dateFormat.format(safeDays1CalendarEnd.getTime()));
            safe_period2.setText(dateFormat.format(unsafeDaysCalendarEnd.getTime())+ " to " +dateFormat.format(safeDays2CalendarEnd.getTime()));
            un_safe_period.setText(dateFormat.format(unsafeDaysCalendarStart.getTime())+ " to " +dateFormat.format(unsafeDaysCalendarEnd.getTime()));
            next_period_starts.setText(dateFormat.format(nextPeriodStartCalendar.getTime()));

        });
    }
}