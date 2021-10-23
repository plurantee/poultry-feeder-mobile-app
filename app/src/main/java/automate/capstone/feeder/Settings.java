package automate.capstone.feeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import automate.capstone.feeder.DataRecycler.DataLog;
import automate.capstone.feeder.Utility.NumberValidator;

public class Settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ToggleButton tbNotif;
    Button btnSubmitSetting;
    EditText etMobileNumber;
    SeekBar sbNotif;
    TextView tvPercentage;
    CheckBox cbUserNotif,cbSystemNotif;

    //variables to be passed
    Boolean notif , user_notif, sys_notif;
    int container_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String toggleButton, checkboxUser, checkboxSystem;
        sbNotif = (SeekBar) findViewById(R.id.sb_notif);
        etMobileNumber = (EditText) findViewById(R.id.et_mobnum);
        //tbNotif = (ToggleButton) findViewById(R.id.toggle_notifsms);
        tvPercentage = (TextView) findViewById(R.id.tv_percentage_container);
       // cbSystemNotif = (CheckBox) findViewById(R.id.cb_sysnotif);
       // cbUserNotif = (CheckBox) findViewById(R.id.cb_usernotif);
        //String contact_num = "1asdad";
        try {
            JSONArray jsonArray = new JSONArray(Store.settings); //Store.logs when connecting to db
            container_progress = Integer.parseInt(jsonArray.getJSONObject(0).getString("container_critical").toString());
            String contact_num=jsonArray.getJSONObject(0).getString("mobile_number").toString();
            etMobileNumber.setText(contact_num);
            //etMobileNumber.setText();
              //for(int i = 0;i<jsonArray.length();i++){
                //JSONObject json_data = jsonArray.getJSONObject(i);

                //checkboxUser = json_data.getString("user_logs");
                //checkboxSystem = json_data.getString("system_logs");
                //toggleButton = json_data.getString("sms_notification"); // Default

                //container_progress = Integer.parseInt(json_data.getString("container_critical").toString());


                //tbNotif.setChecked(Boolean.parseBoolean(toggleButton));
                //cbUserNotif.setChecked(Boolean.parseBoolean(checkboxUser));
                //cbSystemNotif.setChecked(Boolean.parseBoolean(checkboxSystem));
            //}
        } catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

        tvPercentage.setText(Integer.toString(container_progress)+"0%");

        sbNotif.setProgress(container_progress);
        sbNotif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //checks the amount of container (ranging from 0-10)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("ADebugTag", Integer.toString(progress));
                container_progress = progress + 2;
                tvPercentage.setText(container_progress + "0%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


      /*  tbNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //  enable/disable notification
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notif = true;
                    Log.d("ADebugTag", notif.toString());
                } else {
                    notif = false;
                    Log.d("ADebugTag", notif.toString());
                }
            }
        });
*/
        btnSubmitSetting = (Button) findViewById(R.id.btn_submit_settings);
    }

    /*public void checkBoxNotif(View view) {
        //checks the activity between two checkboxes
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cb_sysnotif:
                if (checked){
                    sys_notif = true;
                    Log.d("ADebugTag", "System Notif = " + sys_notif);
                }
                else{
                    sys_notif = false;
                    Log.d("ADebugTag", "System Notif = " + sys_notif);
                }
                break;
            case R.id.cb_usernotif:
                if (checked){
                    user_notif = true;
                    Log.d("ADebugTag", "User Notif = " + user_notif);
                }
                else{
                    user_notif = false;
                    Log.d("ADebugTag", "User Notif = " + user_notif);
                }
                break;
        }
    }
    */
    public void saveConfig(View view) {
        //do process when submitting config to RPI
        DatabaseHelper dh = new DatabaseHelper(this);
        String mobile_number = etMobileNumber.getText().toString();
        mobile_number = NumberValidator.phoneNumberConvertFormat(mobile_number);
        if(NumberValidator.isPhoneNumber(mobile_number)){
            dh.execute("save settings", Integer.toString(container_progress),mobile_number);
            //Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Please Enter a Valid Number",Toast.LENGTH_LONG).show();
        }

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
