package automate.capstone.feeder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import org.json.JSONException;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import automate.capstone.feeder.Adapters.AdapterAutomaticMode;
import automate.capstone.feeder.DataRecycler.DataAutomaticRecycler;
import automate.capstone.feeder.Fragments.DatePickerFragment;
import automate.capstone.feeder.Fragments.TimePickerFragment;
import automate.capstone.feeder.Utility.NumberValidator;

public class AutomaticMode extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,AsyncResponse {
    Spinner spnrDuration, spnrMeasure;
    Button btnStartDate, btnTime;
    TextView tvStartDate, tv_schedule_name, tv_feed;
    EditText et_schedule_name, et_feeds;
    private AdapterAutomaticMode adapterAutomaticMode;
    private RecyclerView recyclerSchedule;
    List<String> data = new ArrayList<>();
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_mode);
        dh = new DatabaseHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        et_feeds = (EditText)findViewById(R.id.et_feeds);
        et_schedule_name = (EditText)findViewById(R.id.et_schedule_name);
        tvStartDate = (TextView) findViewById(R.id.tv_start_date_desc);
        tv_schedule_name = (TextView) findViewById(R.id.tv_schedule_name);
        tv_feed = (TextView) findViewById(R.id.tv_feed);
        btnTime = (Button) findViewById(R.id.btn_time);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        btnStartDate = (Button) findViewById(R.id.btn_start_date);
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        ArrayAdapter<CharSequence> adapter;

        spnrMeasure = (Spinner) findViewById(R.id.spnr_unit);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.measure_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrMeasure.setAdapter(adapter);

        spnrDuration = (Spinner) findViewById(R.id.spnr_duration);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.duration_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrDuration.setAdapter(adapter);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {
            DataAutomaticRecycler dataTime = new DataAutomaticRecycler();

            adapterAutomaticMode =  new AdapterAutomaticMode(AutomaticMode.this,data);

            recyclerSchedule = (RecyclerView) findViewById(R.id.recycler_automatic_mode);
            recyclerSchedule.setAdapter(adapterAutomaticMode);
            recyclerSchedule.setLayoutManager(new LinearLayoutManager(AutomaticMode.this));
            dataTime.setTime(String.format("%02d:%02d", hourOfDay, minute));

            if (data.contains(dataTime.getTime())) {
                Toast.makeText(this, "You cannot enter more than two same time.", Toast.LENGTH_SHORT).show();
            }
            else{
                data.add(dataTime.getTime());
                adapterAutomaticMode.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        tvStartDate = (TextView) findViewById(R.id.tv_start_date_desc);
        tvStartDate.setText(currentDateString);
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
    public void saveLog(View view) throws SQLException, JSONException, ExecutionException, InterruptedException {
        String scheduleName = et_schedule_name.getText().toString();
        String feed = et_feeds.getText().toString();
        String duration = spnrDuration.getSelectedItem().toString();
        String measure = spnrMeasure.getSelectedItem().toString();
        String startdate = tvStartDate.getText().toString();
        String time="";
        String title = scheduleName+" has been created \n(Automatic Schedule)";
        String log = feed+" "+measure;
        //String log = "This schedule will serve "+feed+" "+measure+" every meal";


        if(NumberValidator.isValidFeedInput(feed)) {
            for (String element : data) {
                DataAutomaticRecycler dataTime = new DataAutomaticRecycler();
                time += element.toString() + ",";
            }
            DatabaseHelper dh = new DatabaseHelper(this);
            dh.execute("add schedule", title, log, scheduleName, feed, duration, measure, startdate, time);
            //dh.delegate = AutomaticMode.this;
            //dh.test();
        } else {
            Toast.makeText(this, "Please enter Valid Feeds Input", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(String output) {

    }
}
