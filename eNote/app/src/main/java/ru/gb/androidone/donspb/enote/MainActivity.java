package ru.gb.androidone.donspb.enote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ru.gb.androidone.donspb.enote.navigation.EnotesListFragment;
import ru.gb.androidone.donspb.enote.navigation.MainNavigation;
import ru.gb.androidone.donspb.enote.observe.Publisher;

public class MainActivity extends AppCompatActivity {

//    private static final String MAIN_FRAGMENT_NAME = "main";

    private MainNavigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new MainNavigation(getSupportFragmentManager());
        init();
        getNavigation().addFragment(EnotesListFragment.newInstance(), false);
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.main_menu_drawer_open,
                R.string.main_menu_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.menu_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.toolbar_menu_settings:
                getNavigation().addFragment(SettingsFragment.newInstance(), false);
                return true;
            case R.id.toolbar_menu_about:
                LayoutInflater li = getLayoutInflater();
                View v = li.inflate(R.layout.about_toast, (ViewGroup) findViewById(R.id.toast_layout));

                Toast aboutToast = new Toast(getApplicationContext());
                aboutToast.setGravity(Gravity.CENTER, 0,0);
                aboutToast.setDuration(Toast.LENGTH_LONG);
                aboutToast.setView(v);
                aboutToast.show();
                return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public MainNavigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0)
//            getSupportFragmentManager().popBackStack();
//        else super.onBackPressed();
//    }
}