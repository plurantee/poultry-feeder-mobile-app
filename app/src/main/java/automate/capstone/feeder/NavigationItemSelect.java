package automate.capstone.feeder;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Donnald on 2/9/2018.
 */

public class NavigationItemSelect {
    public NavigationItemSelect (Context context, int id) {

        if (id == R.id.nav_auto) {
            Intent intent = new Intent(context, AutomaticMode.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_man) {
            Intent intent = new Intent(context, ManualMode.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_logs) {
            DatabaseHelper dh = new DatabaseHelper(context);
            String type="view logs";
            dh.execute(type);
            //Intent intent = new Intent(context, ViewLogs.class);
            //context.startActivity(intent);
        } else if (id == R.id.nav_schedule) {
            DatabaseHelper dh = new DatabaseHelper(context);
            String type="view schedule";
            dh.execute(type);
            //Intent intent = new Intent(context, ViewScheduleList.class);
            //context.startActivity(intent);
        } else if (id == R.id.nav_settings) {
            String type = "get settings";
            DatabaseHelper dh = new DatabaseHelper(context);
            dh.execute(type);
            //Intent intent = new Intent(context, Settings.class);
            //context.startActivity(intent);
        }
    }
}
