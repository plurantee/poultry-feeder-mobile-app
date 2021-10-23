package automate.capstone.feeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import automate.capstone.feeder.Adapters.AdapterSchedule;
import automate.capstone.feeder.DataRecycler.DataSchedule;

public class ViewScheduleList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AdapterSchedule adapterSchedule;
    private RecyclerView recyclerSchedule;
    List<DataSchedule> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String schedules= Store.schedules;
        data = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(schedules);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject json_data = jsonArray.getJSONObject(i);
                DataSchedule dataSched = new DataSchedule();
                dataSched.sched_name = json_data.getString("schedule_name");
                dataSched.start_date = json_data.getString("start_date");
                dataSched.end_date = json_data.getString("end_date");
                dataSched.date_added = json_data.getString("date_added");
                dataSched.feed_amount = json_data.getString("feed_amount");
                dataSched.id = json_data.getString("id_automatic");
                data.add(dataSched);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "No schedule found.", Toast.LENGTH_SHORT).show();
        }

        adapterSchedule =  new AdapterSchedule(ViewScheduleList.this,data);

        recyclerSchedule = (RecyclerView) findViewById(R.id.recycler_schedule);
        recyclerSchedule.setLayoutManager(new LinearLayoutManager(ViewScheduleList.this));
        recyclerSchedule.setAdapter(adapterSchedule);

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

}
