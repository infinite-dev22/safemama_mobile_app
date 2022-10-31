package org.infinite.mantra;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import org.infinite.mantra.database.dao.PetographDAO;
import org.infinite.mantra.database.model.PetographModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class AddCheckupRecordActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText systolicTextInput, diastolicTextInput, pulseTextInput, lnmpTextInput;
    String systolicTxt, diastolicTxt, pulseTxt, lnmpTxt, dateRecorded, timeRecorded, name;
    View focusView = null;
    PetographDAO dao;
    Button submitBtn, cancelBtn;
    int cycleFinal;
    private Integer curValue;
    List<PetographModel> dbList;
    AlertDialog.Builder alertBuilder;
    MaterialDatePicker<Long> materialDatePicker;
    int NOTIFICATION_ID = 001;
    private static final String CHANNEL_ID = "Pre Eclampsia Warning";

    public AddCheckupRecordActivity() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checkup_record);

        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title_add_record);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        systolicTextInput = findViewById(R.id.systolic_input);
        diastolicTextInput = findViewById(R.id.diastolic_input);
        pulseTextInput = findViewById(R.id.pulse_input);
        lnmpTextInput = findViewById(R.id.lnmp_input);
        submitBtn = findViewById(R.id.submitBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        dao = new PetographDAO(getApplicationContext());
        dbList = new ArrayList<>();
        alertBuilder = new AlertDialog.Builder(this);

        // now create instance of the material date picker
        // builder make sure to add the "datePicker" which
        // is normal material date picker which is the first
        // type of the date picker in material design date
        // picker
        materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

        setData();

        submitBtn.setOnClickListener(view -> {
            saveCheckupData();
            checkForPreeclampsiaPossibility();
        });

        cancelBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        lnmpTextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(fragmentManager, "MATERIAL_DATE_PICKER");
            }
        });

        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<? super Long>) selection -> {

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            String formattedDate = format.format(calendar.getTime());
            // if the user clicks on the positive
            // button that is ok button update the
            // selected date
            lnmpTextInput.setText(formattedDate);
            // in the above statement, getHeaderText
            // is the selected date preview from the
            // dialog
        });
    }

    public boolean checkEmpty() {
        systolicTxt = Objects.requireNonNull(systolicTextInput.getText()).toString();
        diastolicTxt = Objects.requireNonNull(diastolicTextInput.getText()).toString();
        pulseTxt = Objects.requireNonNull(pulseTextInput.getText()).toString();
        lnmpTxt = Objects.requireNonNull(lnmpTextInput.getText()).toString();

        if (TextUtils.isEmpty(systolicTxt)) {
            systolicTextInput.setError(getString(R.string.error_field_required));
            focusView = systolicTextInput;
            return false;
        } else if (TextUtils.isEmpty(diastolicTxt)) {
            diastolicTextInput.setError(getString(R.string.error_field_required));
            focusView = diastolicTextInput;
            return false;
        } else if (TextUtils.isEmpty(pulseTxt)) {
            pulseTextInput.setError(getString(R.string.error_field_required));
            focusView = pulseTextInput;
            return false;
        } else if (TextUtils.isEmpty(lnmpTxt)) {
            lnmpTextInput.setError(getString(R.string.error_field_required));
            focusView = lnmpTextInput;
            return false;
        } else if (Integer.parseInt(systolicTextInput.getText().toString()) > 300) {
            systolicTextInput.setError(getString(R.string.error_value_large));
            focusView = systolicTextInput;
            return false;
        } else if (Integer.parseInt(diastolicTextInput.getText().toString()) > 160) {
            diastolicTextInput.setError(getString(R.string.error_value_large));
            focusView = diastolicTextInput;
            return false;
        } else if (Integer.parseInt(pulseTextInput.getText().toString()) > 200) {
            pulseTextInput.setError(getString(R.string.error_value_large));
            focusView = pulseTextInput;
            return false;
        } else {
            return true;
        }
    }

    public void saveCheckupData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        Date dateNow = new Date();

        systolicTxt = String.valueOf(systolicTextInput.getText());
        diastolicTxt = String.valueOf(diastolicTextInput.getText());
        pulseTxt = String.valueOf(pulseTextInput.getText());
        lnmpTxt = String.valueOf(lnmpTextInput.getText());
        dateRecorded = dateFormat.format(dateNow);
        timeRecorded = timeFormat.format(dateNow);

        if (checkEmpty()) {
            name = "Normal";
            if (Integer.parseInt(systolicTxt) > 139 && Integer.parseInt(diastolicTxt) > 89) {
                name = "High";
            } else if (Integer.parseInt(diastolicTxt) > 89) {
                name = "High";
            } else if (Integer.parseInt(systolicTxt) > 139) {
                name = "High";
            } else if (Integer.parseInt(systolicTxt) < 140 && Integer.parseInt(diastolicTxt) < 90) {
                name = "Normal";
            }

            PetographModel dbModel = new PetographModel();

            dbModel.setLnmp(lnmpTxt);
            dbModel.setDiastolic(diastolicTxt);
            dbModel.setSystolic(systolicTxt);
            dbModel.setPulseRate(pulseTxt);
            dbModel.setDateMeasured(dateRecorded);
            dbModel.setTimeMeasured(timeRecorded);

            dao.insertIntoDB(name, systolicTxt, diastolicTxt, dateRecorded, timeRecorded, pulseTxt, lnmpTxt);

            systolicTextInput.setText("");
            diastolicTextInput.setText("");
            pulseTextInput.setText("");
        }
    }

    public void checkForPreeclampsiaPossibility() {
        dbList = dao.getDataFromDB();
        if (dbList.size() != 0) {
            curValue = dbList.size() - 1;
            String systolic = dbList.get(curValue).getSystolic();
            String diastolic = dbList.get(curValue).getDiastolic();
            String lnmp = dbList.get(curValue).getLnmp();
            int pregnancy = calcWoa(lnmp);
            lnmpTextInput.setText(lnmp);

            if (pregnancy > 19 && pregnancy < 45) {
                if (Integer.parseInt(systolic) > 140 || Integer.parseInt(diastolic) > 90) {
                    showNotification();
                    showAlert();
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }

    private void setData() {
        dbList = dao.getDataFromDB();
        if (dbList.size() != 0) {
            curValue = dbList.size() - 1;
            String lnmp = dbList.get(curValue).getLnmp();
            lnmpTextInput.setText(lnmp);
        }
    }

    public int calcWoa(String lnmpDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date date = Calendar.getInstance().getTime();
        try {
            date = dateFormat.parse(lnmpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        assert date != null;
        calendar2.setTime(date);
        calendar1.setTime(date);
        calendar1.add(Calendar.DAY_OF_MONTH, 7);
        if (calendar1.get(5) > calendar1.getMaximum(Calendar.DAY_OF_MONTH)) {
            calendar1.add(Calendar.DAY_OF_MONTH, -calendar1.getMaximum(Calendar.DAY_OF_MONTH));
            calendar1.add(Calendar.LONG, 10);
        } else {
            calendar1.add(Calendar.LONG, 9);
        }
        Date now = Calendar.getInstance().getTime();
        Date lnmp2 = calendar2.getTime();
        long currentDate = now.getTime();
        long dateLastMenses = lnmp2.getTime();
        currentDate -= dateLastMenses;
        int finalResult = (int) (currentDate / 86400000);
        return (finalResult - cycleFinal) / 7;
    }

    public final void showNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle("Petograph")
                    .setContentText(getString(R.string.suspect))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "Pre Eclampsia Warning", NotificationManager.IMPORTANCE_HIGH));
        } else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle("Petograph")
                    .setContentText(getString(R.string.suspect))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
        }

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public final void showAlert() {
        alertBuilder.setMessage(R.string.suspect).setTitle(R.string.urgent);
        alertBuilder.setNegativeButton("Ok", (dialog, id) -> {
            dialog.cancel();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}