package org.infinite.mantra;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class DangerActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    @Override
    // android.support.v4.app.BaseFragmentActivityGingerbread, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger);
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.toolbar_title_danger_signs);

        final String[] dangerItems = {getString(R.string.headache), getString(R.string.dizziness), getString(R.string.vision), getString(R.string.visionLoss), getString(R.string.conscious), getString(R.string.heartbeat), getString(R.string.breath), getString(R.string.pain), getString(R.string.vomiting), getString(R.string.diarrhoea), getString(R.string.bodyswelling), getString(R.string.urine)};
        ((ListView) findViewById(R.id.listDanger)).setAdapter(new ArrayAdapter<>(this, R.layout.list_item, R.id.danger_list_item, dangerItems));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
