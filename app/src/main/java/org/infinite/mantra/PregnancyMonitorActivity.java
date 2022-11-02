package org.infinite.mantra;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PregnancyMonitorActivity extends AppCompatActivity {

    CalendarView pregnancy_age_calendar;
    TextView calendar_lnmp, calendar_pregnancy_age, date_of_delivery;
    private long dateNow, dateLNMP;
    private int finalResult;
    int cycleFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_monitor);

        setActivityToolbar();
        initViews();
        clickActionEvents();
    }

    private void setActivityToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.toolbar_title_pregnancy_age_calculator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        pregnancy_age_calendar = findViewById(R.id.pregnancy_age_calendar);
        calendar_lnmp = findViewById(R.id.calendar_lnmp);
        calendar_pregnancy_age = findViewById(R.id.calendar_pregnancy_age);
        date_of_delivery = findViewById(R.id.date_of_delivery);
    }

    private void clickActionEvents() {
        pregnancy_age_calendar.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            calendar1.set(year, month, dayOfMonth);

            calendar2.set(year, month, dayOfMonth);
            calendar2.add(5, 7);

            if (calendar2.get(5) > calendar2.getMaximum(5)) {
                calendar2.add(5, -calendar2.getMaximum(5));
                calendar2.add(2, 10);
            } else {
                calendar2.add(2, 9);
            }

            Date now = Calendar.getInstance().getTime();
            Date lnmp = calendar1.getTime();

            dateNow = now.getTime();
            dateLNMP = lnmp.getTime();

            dateNow -= dateLNMP;
            finalResult = (int) (dateNow / 86400000);

            if (finalResult > 30) {
                if (finalResult > cycleFinal && finalResult < 290) {
                    int weeks = (finalResult - cycleFinal) / 7;
                    int days = (finalResult - cycleFinal) % 7;
                    calendar_lnmp.setText(dateFormat.format(new Date(dateLNMP)));
                    calendar_pregnancy_age.setText(weeks + " " + getString(R.string.weeks) + ", " + days + " " + getString(R.string.days));
                    date_of_delivery.setText(dateFormat.format(calendar2.getTime()));
                } else {
                    date_of_delivery.setText(getString(R.string.not_pregnant));
                    calendar_pregnancy_age.setText(getString(R.string.try_next));
                    calendar_lnmp.setText(dateFormat.format(calendar1.getTime()));
                }
            } else if (dateNow < 0) {
                date_of_delivery.setText(getString(R.string.yet_to_come));
                calendar_pregnancy_age.setText(getString(R.string.enter_date));
                calendar_lnmp.setText(dateFormat.format(calendar1.getTime()));
            } else {
                date_of_delivery.setText("");
                calendar_pregnancy_age.setText(getString(R.string.not_yet_pregnant));
                calendar_lnmp.setText(dateFormat.format(calendar1.getTime()));
            }

            calendarView.setDate(calendar1.getTimeInMillis(), true, true);
        });
    }
}