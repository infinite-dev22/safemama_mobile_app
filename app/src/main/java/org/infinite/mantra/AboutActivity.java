package org.infinite.mantra;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {
    TextView aboutTxt;

    /* access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.toolbar_title_about);
        this.aboutTxt = findViewById(R.id.aboutTxt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
