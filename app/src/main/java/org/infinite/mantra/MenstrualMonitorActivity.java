package org.infinite.mantra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.shuhart.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MenstrualMonitorActivity extends AppCompatActivity {
        CalendarView menstrual_calendar;
//    MaterialCalendarView menstrual_calendar;
    TextInputEditText menstrual_duration;
    Button calc_men_cycle_btn;
    TextView safe_period1, safe_period2, un_safe_period, next_period_starts;
    int lnmp_cycle_day, lnmp_cycle_month, lnmp_cycle_year;
    Calendar safeDays1CalendarStart, safeDays1CalendarEnd, safeDays2CalendarStart, safeDays2CalendarEnd, unsafeDaysCalendarStart, unsafeDaysCalendarEnd, nextPeriodStartCalendar;


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
        menstrual_calendar.animate();

        menstrual_duration = findViewById(R.id.menstrual_duration);
        calc_men_cycle_btn = findViewById(R.id.calc_men_cycle_btn);

        safe_period1 = findViewById(R.id.safe_period1);
        safe_period2 = findViewById(R.id.safe_period2);
        un_safe_period = findViewById(R.id.un_safe_period);
        next_period_starts = findViewById(R.id.next_period_starts);
    }

    private void clickActionEvent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

//        CALENDAR-VIEW
        menstrual_calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            lnmp_cycle_day = dayOfMonth;
            lnmp_cycle_month = month;
            lnmp_cycle_year = year;
        });

        calc_men_cycle_btn.setOnClickListener(v -> {
            safeDays1CalendarStart = Calendar.getInstance();
            safeDays1CalendarEnd = Calendar.getInstance();
            safeDays2CalendarStart = Calendar.getInstance();
            safeDays2CalendarEnd = Calendar.getInstance();
            unsafeDaysCalendarStart = Calendar.getInstance();
            unsafeDaysCalendarEnd = Calendar.getInstance();
            nextPeriodStartCalendar = Calendar.getInstance();

            safeDays1CalendarStart.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
            safeDays1CalendarEnd.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
            safeDays2CalendarStart.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
            safeDays2CalendarEnd.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
            unsafeDaysCalendarStart.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
            unsafeDaysCalendarEnd.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
            nextPeriodStartCalendar.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);

            try {
                int cycleDuration = Integer.parseInt(Objects.requireNonNull(menstrual_duration.getText()).toString());
                if (cycleDuration >= 21 && cycleDuration <= 35) {
                    int safeDays1End = (cycleDuration - 15) - 3; // ((X-15)-2)-1
                    int unSafeDaysStart = (cycleDuration - 16) -1 ; // (X-16)-1
                    int unSafeDaysEnd = (cycleDuration - 12) - 1; // (X-12)-1
                    int safeDays2Start = (cycleDuration - 11) - 1; // (X-11)-1
                    int safeDays2End = cycleDuration - 1; // X-1

                    // int startOfNextCycle = cycleDuration + 1; // (X+1)-1 = X hence cycle duration

                    safeDays1CalendarEnd.add(Calendar.DAY_OF_MONTH, safeDays1End);
                    unsafeDaysCalendarStart.add(Calendar.DAY_OF_MONTH, unSafeDaysStart);
                    unsafeDaysCalendarEnd.add(Calendar.DAY_OF_MONTH, unSafeDaysEnd);
                    safeDays2CalendarStart.add(Calendar.DAY_OF_MONTH, safeDays2Start);
                    safeDays2CalendarEnd.add(Calendar.DAY_OF_MONTH, safeDays2End);
                    nextPeriodStartCalendar.add(Calendar.DAY_OF_MONTH, cycleDuration);

                    String safeDays1 = dateFormat.format(safeDays1CalendarStart.getTime())
                            + " to " + dateFormat.format(safeDays1CalendarEnd.getTime());
                    String unSafeDays = dateFormat.format(unsafeDaysCalendarStart.getTime())
                            + " to " + dateFormat.format(unsafeDaysCalendarEnd.getTime());
                    String safeDays2 = dateFormat.format(safeDays2CalendarStart.getTime())
                            + " to " + dateFormat.format(safeDays2CalendarEnd.getTime());

                    safe_period1.setText(safeDays1);
                    un_safe_period.setText(unSafeDays);
                    safe_period2.setText(safeDays2);
                    next_period_starts.setText(dateFormat
                            .format(nextPeriodStartCalendar.getTime()));
                } else {
                    Toast.makeText(this, "Abnormal cycle length, please try again"
                            , Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException err) {
                Toast.makeText(this, "\"Duration of Menstrual cycle\" can't be empty"
                        , Toast.LENGTH_LONG).show();
            }
        });

//        MATERIAL-CALENDAR-VIEW
//        menstrual_calendar.addOnDateChangedListener((materialCalendarView, calendarDay, b) -> {
//            lnmp_cycle_day = calendarDay.getDay();
//            lnmp_cycle_month = calendarDay.getMonth();
//            lnmp_cycle_year = calendarDay.getYear();
//        });
//
//        calc_men_cycle_btn.setOnClickListener(v -> {
//            safeDays1CalendarStart = Calendar.getInstance();
//            safeDays1CalendarEnd = Calendar.getInstance();
//            safeDays2CalendarStart = Calendar.getInstance();
//            safeDays2CalendarEnd = Calendar.getInstance();
//            unsafeDaysCalendarStart = Calendar.getInstance();
//            unsafeDaysCalendarEnd = Calendar.getInstance();
//            nextPeriodStartCalendar = Calendar.getInstance();
//
//            safeDays1CalendarStart.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
//            safeDays1CalendarEnd.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
//            safeDays2CalendarStart.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
//            safeDays2CalendarEnd.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
//            unsafeDaysCalendarStart.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
//            unsafeDaysCalendarEnd.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
//            nextPeriodStartCalendar.set(lnmp_cycle_year, lnmp_cycle_month, lnmp_cycle_day);
//
//            try {
//                int cycleDuration = Integer.parseInt(Objects.requireNonNull(menstrual_duration.getText()).toString());
//                if (cycleDuration >= 21 && cycleDuration <= 35) {
//                    // SUBTRACT 1 TO CATER FOR DAY 1 OF THE PERIOD START.
//                    int safeDays1End = (cycleDuration - 15) - 3; // ((X-15)-2)-1
//                    int unSafeDaysStart = (cycleDuration - 16) -1 ; // (X-16)-1
//                    int unSafeDaysEnd = (cycleDuration - 12) - 1; // (X-12)-1
//                    int safeDays2Start = (cycleDuration - 11) - 1; // (X-11)-1
//                    int safeDays2End = cycleDuration - 1; // X-1
//
//                    // int startOfNextCycle = cycleDuration + 1; // (X+1)-1 = X hence cycle duration
//
//                    safeDays1CalendarEnd.add(Calendar.DAY_OF_MONTH, safeDays1End);
//                    unsafeDaysCalendarStart.add(Calendar.DAY_OF_MONTH, unSafeDaysStart);
//                    unsafeDaysCalendarEnd.add(Calendar.DAY_OF_MONTH, unSafeDaysEnd);
//                    safeDays2CalendarStart.add(Calendar.DAY_OF_MONTH, safeDays2Start);
//                    safeDays2CalendarEnd.add(Calendar.DAY_OF_MONTH, safeDays2End);
//                    nextPeriodStartCalendar.add(Calendar.DAY_OF_MONTH, cycleDuration);
//
//                    String safeDays1 = dateFormat.format(safeDays1CalendarStart.getTime()) + " to " + dateFormat.format(safeDays1CalendarEnd.getTime());
//                    String unSafeDays = dateFormat.format(unsafeDaysCalendarStart.getTime()) + " to " + dateFormat.format(unsafeDaysCalendarEnd.getTime());
//                    String safeDays2 = dateFormat.format(safeDays2CalendarStart.getTime()) + " to " + dateFormat.format(safeDays2CalendarEnd.getTime());
//
//                    safe_period1.setText(safeDays1);
//                    un_safe_period.setText(unSafeDays);
//                    safe_period2.setText(safeDays2);
//                    next_period_starts.setText(dateFormat.format(nextPeriodStartCalendar.getTime()));
//
//                    menstrual_calendar.setSelectedDate(safeDays1CalendarEnd.getTime());
//                    menstrual_calendar.setSelectedDate(safeDays2CalendarEnd.getTime());
//                } else {
//                    Toast.makeText(this, "Abnormal cycle length, please try again", Toast.LENGTH_LONG).show();
//                }
//            } catch (NumberFormatException err) {
//                Toast.makeText(this, "\"Duration of Menstrual cycle\" can't be empty", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}