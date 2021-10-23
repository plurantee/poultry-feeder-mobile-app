package automate.capstone.feeder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import automate.capstone.feeder.Adapters.AdapterLog;
import automate.capstone.feeder.DataRecycler.DataLog;

public class ViewLogs extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse {

    DatabaseHelper dh = new DatabaseHelper(this);
    TextView tv_log_title,tv_log_info;
    ListView listView;
    ProgressDialog pDialog;
    private RecyclerView recyclerLog;
    private AdapterLog adapterLog;
    LinearLayoutManager layoutManager;
    Button btn_logs;
    List<DataLog> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        //Make call to AsyncTask
        data = new ArrayList<>();
        //String asd = "[{\"id_logs\":\"1\",\"log_type\":\"test\",\"log_info\":\"test loginfo\",\"date_add\":\"2018-01-30 07:16:34\"},{\"id_logs\":\"2\",\"log_type\":\"log test 2\",\"log_info\":\"log test description 2\",\"date_add\":\"2018-01-30 09:34:35\"}]";
        try {
            JSONArray jsonArray = new JSONArray(Store.logs); //Store.logs when connecting to db
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject json_data = jsonArray.getJSONObject(i);
                DataLog dataLog = new DataLog();
                dataLog.logtype= json_data.getString("log_info");
                dataLog.loginfo= json_data.getString("log_type");
                data.add(dataLog);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "No logs found.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        recyclerLog = (RecyclerView) findViewById(R.id.recyclerlog);
        adapterLog = new AdapterLog(ViewLogs.this, data);

        recyclerLog.setLayoutManager(new LinearLayoutManager(ViewLogs.this));
        recyclerLog.setAdapter(adapterLog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public void processFinish(String output) {

    }
}
