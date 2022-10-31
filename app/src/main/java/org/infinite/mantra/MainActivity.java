package org.infinite.mantra;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.infinite.mantra.database.dao.PetographDAO;
import org.infinite.mantra.database.model.PetographModel;
import org.infinite.mantra.services.ValueChangeListener;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;
    private final String[] tabName = {"CHECKUP", "CHARTS", "REVIEWS"};
    public static List<PetographModel> dbList;
    PetographDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();
        setDrawer();
        setTabLayout();
        startService(new Intent(this, ValueChangeListener.class));
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, ValueChangeListener.class));
    }

    public void setNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_danger_signs) {
                startActivity(new Intent(getApplicationContext(), DangerActivity.class));
            } else if (id == R.id.nav_website) {
                startActivity(new Intent(getApplicationContext(), WebActivity.class));
                new WebActivity();
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            } else if (id == R.id.nav_share) {
                startShare();
            } else if (id == R.id.nav_about) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }

            return false;
        });
    }

    public void setDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(fragmentViewPagerAdapter);

        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(this);
        viewPager2.setAdapter(fragmentViewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabName[position])).attach();
    }

    public void startShare() {
        try {
            dao = new PetographDAO(getApplicationContext());
            dbList = dao.getDataFromDB();
            int i = dbList.size() - 1;
            Intent sendIntent = new Intent();
            sendIntent.setAction("android.intent.action.SEND");
            sendIntent.putExtra("android.intent.extra.TEXT", getString(R.string.vital_signs) + "\n\t*_" + getString(R.string.blood_pressure) + ":_* " + dbList.get(i).getSystolic() + "/" + dbList.get(i).getDiastolic() + "\n\t*_" + getString(R.string.Pulse) + ":_* " + dbList.get(i).getPulseRate() + "\n\t*_" + getString(R.string.lnmp) + ":_* " + dbList.get(i).getLnmp() + "\n\t*_" + getString(R.string.date) + ":_* " + dbList.get(i).getDateMeasured() + " " + dbList.get(i).getTimeMeasured() + "\n\thttps://play.google.com/store/apps/details?id=petograph.scriptfloor.com.petograph&hl=en");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getString(R.string.share) + " " + getString(R.string.vital_signs)));
        } catch (Exception e) {
            Snackbar.make(findViewById(R.id.view_pager), R.string.no_data_error, Snackbar.LENGTH_LONG).setAction(R.string.cancel, null).show();
        }
    }
}