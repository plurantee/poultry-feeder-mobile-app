package automate.capstone.feeder;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import automate.capstone.feeder.Utility.NumberValidator;

public class ManualMode extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Spinner spnrMeasure;
    EditText et_manual_feed;
    Button btn_manual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<CharSequence> adapter;

        spnrMeasure = (Spinner) findViewById(R.id.spnr_manual_unit);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.measure_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrMeasure.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        new NavigationItemSelect(this,id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSubmitManual(View view) {
        DatabaseHelper dh = new DatabaseHelper(this);
        et_manual_feed = (EditText) findViewById(R.id.et_manual_feed);
        btn_manual = (Button) findViewById(R.id.btn_drop_feeds);
        String feed = et_manual_feed.getText().toString();
        if(NumberValidator.isValidFeedInput(feed)) {
            btn_manual.setEnabled(false);
            Toast.makeText(this, feed + " grams of pellets will be dropped.", Toast.LENGTH_LONG).show();

            dh.execute("select manual", feed);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_manual.setEnabled(true);
                    Toast.makeText(ManualMode.this, "You can now drop pellets again.", Toast.LENGTH_SHORT).show();
                }
            }, 60000);

        } else {
            Toast.makeText(this, "Please Enter Valid Input of Pellet Amount", Toast.LENGTH_LONG).show();
        }
    }
}
