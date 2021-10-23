package automate.capstone.feeder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import automate.capstone.feeder.Adapters.AdapterAutomaticMode;
import automate.capstone.feeder.Adapters.AdapterEditSchedule;
import automate.capstone.feeder.Adapters.AdapterSchedule;
import automate.capstone.feeder.DataRecycler.DataAutomaticRecycler;
import automate.capstone.feeder.DataRecycler.DataEditScheduleRecycler;
import automate.capstone.feeder.DataRecycler.DataLog;
import automate.capstone.feeder.DataRecycler.DataSchedule;
import automate.capstone.feeder.Fragments.DatePickerFragment;
import automate.capstone.feeder.Fragments.TimePickerFragment;

public class ViewSchedule extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private AdapterEditSchedule adapterEditSchedule;
    private RecyclerView recyclerEditSchedule;
    List<DataSchedule> data = new ArrayList<>();
    List<String> timesArray = new ArrayList();
    TextView tvEditDate;
    EditText etEditFeed,etEditName;
    private RecyclerView recyclerSchedule;
    Button btnEditTime, btnEditDate;
    DataSchedule dataView;
    Spinner spnrDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvEditDate = (TextView) findViewById(R.id.tv_edit_date_placeholder);
        etEditFeed= (EditText) findViewById(R.id.et_edit_feed_amount);
        etEditName= (EditText) findViewById(R.id.et_schedule_name_placeholder);
        spnrDuration = (Spinner) findViewById(R.id.edit_spnr_unit);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,
                R.array.measure_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnEditDate = (Button) findViewById(R.id.btn_edit_date);
        btnEditTime = (Button) findViewById(R.id.btn_edit_time);
        btnEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        btnEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        JSONObject obj = null;
        try {
            obj = new JSONObject(Store.schedule);
            JSONArray jsonSchedule= obj.getJSONArray("schedule");
            JSONArray jsonTimes= obj.getJSONArray("times");
            for(int i = 0;i<jsonSchedule.length();i++){
                JSONObject json_data = jsonSchedule.getJSONObject(i);

                //Toast.makeText(this,json_data.getString("schedule_name")+json_data.getString("feed_amount"),Toast.LENGTH_LONG).show();
                //Toast.makeText(this,json_data.getString("log_info"),Toast.LENGTH_LONG).show();
                etEditName.setText(json_data.getString("schedule_name"));
                tvEditDate.setText(json_data.getString("start_date"));
                etEditFeed.setText(json_data.getString("feed_amount"));
                for(int j = 0;j<jsonTimes.length();j++){
                    JSONObject json_data_time = jsonTimes.getJSONObject(j);
                    if(!timesArray.contains(json_data_time.getString("time"))){
                        timesArray.add(json_data_time.getString("time"));
                    }
                }
                //data.add(dataLog);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(Store.schedule); //Store.logs when connecting to db
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject json_data = jsonArray.getJSONObject(i);
                dataView = new DataSchedule();
                dataView.start_date= json_data.getString("start_date");
                data.add(dataView);
            }

        } catch (JSONException e) {
            Toast.makeText(this, "No logs found.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        recyclerEditSchedule = (RecyclerView) findViewById(R.id.recycler_edit_schedule);
        adapterEditSchedule =  new AdapterEditSchedule(ViewSchedule.this,timesArray);
        recyclerEditSchedule.setAdapter(adapterEditSchedule);
        recyclerEditSchedule.setLayoutManager(new LinearLayoutManager(ViewSchedule.this));

    }

    ///////////////////////////////////////////////////////////////////////
    public void editAccept(View view) {
        //dito lagay edit

        EditText et_schedule_name = (EditText)findViewById(R.id.et_schedule_name_placeholder);
        EditText et_feed_amount = (EditText) findViewById(R.id.et_edit_feed_amount);
        String duration = spnrDuration.getSelectedItem().toString();
        String schedule_name = et_schedule_name.getText().toString();
        String feed_amount = et_feed_amount.getText().toString();
        String startdate = tvEditDate.getText().toString();
        String time = "";
        String title = "Schedule has been edited "+schedule_name;
        String log = feed_amount+"g";

        for(String element: timesArray){
            DataAutomaticRecycler dataTime = new DataAutomaticRecycler();
            time+=element.toString()+",";
        }
        DatabaseHelper dh = new DatabaseHelper(this);
        dh.execute("edit schedule",title,log,schedule_name,feed_amount,duration,"g",startdate,time,Store.schedules_id);
    }

    public void editCancel(View view) {
        Intent intent = new Intent(this, ViewScheduleList.class);
        startActivity(intent);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {
            DataEditScheduleRecycler dataTime = new DataEditScheduleRecycler();
            adapterEditSchedule =  new AdapterEditSchedule(ViewSchedule.this,timesArray);
            recyclerSchedule = (RecyclerView) findViewById(R.id.recycler_edit_schedule);
            recyclerSchedule.setAdapter(adapterEditSchedule);
            recyclerSchedule.setLayoutManager(new LinearLayoutManager(ViewSchedule.this));
            dataTime.setTime(String.format("%02d:%02d", hourOfDay, minute));


            if (timesArray.contains(dataTime.getTime())) {
                Toast.makeText(this, "You cannot enter more than two same time.", Toast.LENGTH_SHORT).show();
            }
            else{
                timesArray.add(dataTime.getTime());
                adapterEditSchedule.notifyDataSetChanged();
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

        tvEditDate.setText(currentDateString);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

}
