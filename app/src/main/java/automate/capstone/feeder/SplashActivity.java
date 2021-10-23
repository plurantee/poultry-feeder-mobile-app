package automate.capstone.feeder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class SplashActivity extends AppCompatActivity implements AsyncResponse {
    private static int SPLASH_TIME_OUT = 1000; //4 seconds
    DatabaseHelper dh = new DatabaseHelper(this);
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*preferences = getSharedPreferences("preferences.xml", Context.MODE_PRIVATE);

        if(preferences.getString("IP_Address","none").equals("none"))
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("thereIsIP","false");
            editor.commit();
        }>*/


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
       // preferences = getSharedPreferences("preferences.xml", Context.MODE_PRIVATE);
        //Store.ip_address = preferences.getString("IP_Address","No IP Address Found");
        //Store.ip_address = preferences.getString("IP_Address","No IP Address Found");
        //dh.execute("test con","true",preferences.getString("IP_Address","No IP Address Found"));


    }

    @Override
    public void processFinish(String output) {
        Store.logs = output;
    }
}
