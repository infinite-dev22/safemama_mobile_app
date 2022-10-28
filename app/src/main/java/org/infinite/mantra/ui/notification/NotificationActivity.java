package org.infinite.mantra.ui.notification;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.infinite.mantra.R;

public class NotificationActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        textView = findViewById(R.id.textView);
        //getting the notification message
        String message = getIntent().getStringExtra("message");
        textView.setText(message);
    }
}
