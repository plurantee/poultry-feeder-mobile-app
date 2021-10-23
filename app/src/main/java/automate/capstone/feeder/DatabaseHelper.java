package automate.capstone.feeder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by rapio on 29/01/2018.
 */

public class DatabaseHelper extends AsyncTask<String,Void,String>{
    Context context;
    public AsyncResponse delegate=null;
    AlertDialog alertDialog;
    String onSplash=null;
    private String type;
    public DatabaseHelper(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        String home_url="http://"+Store.ip_address+"/feeder";

        if(type.equals("view logs") || type.equals("test con")){
            String viewlogurl = home_url + "/home/logs";

            try {
                URL url = new URL(viewlogurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("data_name","UTF-8")+"="+URLEncoder.encode("data_name_value","UTF-8")+"&"
                        +URLEncoder.encode("data_name2","UTF-8")+"="+URLEncoder.encode("data_name_value2","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("view schedule")){
            String viewlogurl = home_url + "/home/schedules";
            try {
                URL url = new URL(viewlogurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("data_name","UTF-8")+"="+URLEncoder.encode("data_name_value","UTF-8")+"&"
                        +URLEncoder.encode("data_name2","UTF-8")+"="+URLEncoder.encode("data_name_value2","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("add schedule")){
            String log_title=params[1];
            String log_info=params[2];

            String scheduleName=params[3];
            String feed=params[4];
            String duration=params[5];
            String measure=params[6];
            String startdate=params[7];
            String time=params[8];
            String addscheduleurl = home_url + "/home/addschedule";
            try {
                URL url = new URL(addscheduleurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("log_title","UTF-8")+"="+URLEncoder.encode(log_title,"UTF-8")+"&"
                        +URLEncoder.encode("log_info","UTF-8")+"="+URLEncoder.encode(log_info,"UTF-8")+"&"
                        +URLEncoder.encode("schedulename","UTF-8")+"="+URLEncoder.encode(scheduleName,"UTF-8")+"&"
                        +URLEncoder.encode("feed","UTF-8")+"="+URLEncoder.encode(feed,"UTF-8")+"&"
                        +URLEncoder.encode("duration","UTF-8")+"="+URLEncoder.encode(duration,"UTF-8")+"&"
                        +URLEncoder.encode("measure","UTF-8")+"="+URLEncoder.encode(measure,"UTF-8")+"&"
                        +URLEncoder.encode("startdate","UTF-8")+"="+URLEncoder.encode(startdate,"UTF-8")+"&"
                        +URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(Exception e){

            }
        }
        else if(type.equals("select manual")){
            String feed_amount=params[1];

            String addscheduleurl = home_url + "/home/manualmode";
            try {
                URL url = new URL(addscheduleurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("feed_amount","UTF-8")+"="+URLEncoder.encode(feed_amount,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(Exception e){

            }
        }
        else if(type.equals("get settings")){
            //String feed_amount=params[1];

            String addscheduleurl = home_url + "/home/settings";
            try {
                URL url = new URL(addscheduleurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("feed_amount","UTF-8")+"="+URLEncoder.encode("na","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(Exception e){

            }
        }
        else if(type.equals("view schedule id")){
            //String feed_amount=params[1];

            String addscheduleurl = home_url + "/home/editschedule";
            String schedule_id = params[1];
            try {
                URL url = new URL(addscheduleurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("id_automatic","UTF-8")+"="+URLEncoder.encode(schedule_id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(Exception e){

            }
        }
        else if(type.equals("delete schedule")){
            //String feed_amount=params[1];

            String addscheduleurl = home_url + "/home/deleteschedule";
            String schedule_id = params[1];
            try {
                URL url = new URL(addscheduleurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("id_automatic","UTF-8")+"="+URLEncoder.encode(schedule_id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(Exception e){

            }
        }
        else if(type.equals("edit schedule")){
            String log_title=params[1];
            String log_info=params[2];

            String scheduleName=params[3];
            String feed=params[4];
            String duration=params[5];
            String measure=params[6];
            String startdate=params[7];
            String time=params[8];
            String id = params[9];
            String addscheduleurl = home_url + "/home/editScheduleInsert";
            try {
                URL url = new URL(addscheduleurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("log_title","UTF-8")+"="+URLEncoder.encode(log_title,"UTF-8")+"&"
                                +URLEncoder.encode("log_info","UTF-8")+"="+URLEncoder.encode(log_info,"UTF-8")+"&"
                                +URLEncoder.encode("schedulename","UTF-8")+"="+URLEncoder.encode(scheduleName,"UTF-8")+"&"
                                +URLEncoder.encode("feed","UTF-8")+"="+URLEncoder.encode(feed,"UTF-8")+"&"
                                +URLEncoder.encode("duration","UTF-8")+"="+URLEncoder.encode(duration,"UTF-8")+"&"
                                +URLEncoder.encode("measure","UTF-8")+"="+URLEncoder.encode(measure,"UTF-8")+"&"
                                +URLEncoder.encode("startdate","UTF-8")+"="+URLEncoder.encode(startdate,"UTF-8")+"&"
                                +URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                                +URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(Exception e){

            }
        }
        else if(type.equals("save settings")){
            //String feed_amount=params[1];

            String addscheduleurl = home_url + "/home/savesettings";
            String container_critical = params[1];
            String mobile_number = params[2];
            try {
                URL url = new URL(addscheduleurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("container_critical","UTF-8")+"="+URLEncoder.encode(container_critical,"UTF-8")+"&"
                        +URLEncoder.encode("mobile_number","UTF-8")+"="+URLEncoder.encode(mobile_number,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(Exception e){

            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context,"Testing Connection", Toast.LENGTH_SHORT).show();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Result");
    }

    @Override
    protected void onPostExecute(String result) {
        if (type.equals("view logs")) {
            Store.logs = result;
            Intent intent = new Intent(context, ViewLogs.class);
            context.startActivity(intent);
        }if (type.equals("test con")) {
                Store.logs = result;
        }if(type.equals("add schedule")){
            try {
                if(result.equals("true")) {
                    Toast.makeText(context, "Schedule Added Successfully", Toast.LENGTH_LONG).show();
                }
            }catch(Exception e){
                Toast.makeText(context, "Schedule Failed to Add", Toast.LENGTH_LONG).show();
            }
        }if(type.equals("select manual")){
            //Toast.makeText(context,result, Toast.LENGTH_LONG).show();
        }if(type.equals("view schedule")){
            Store.schedules = result;
            Intent intent = new Intent(context, ViewScheduleList.class);
            context.startActivity(intent);
        }if(type.equals("get settings")){
            Store.settings = result;
            Intent intent = new Intent(context, Settings.class);
            context.startActivity(intent);
        }if(type.equals("view schedule id")){
            Store.schedule = result;
            Intent intent = new Intent(context,ViewSchedule.class);
            context.startActivity(intent);
        }if(type.equals("delete schedule")){
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
        }if(type.equals("edit schedule")){
            if(result.equals("true")) {
                Toast.makeText(context, "Schedule Updated Successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "Schedule Failed to Update", Toast.LENGTH_LONG).show();
            }
        }if(type.equals("save settings")){
            //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
        }
        Store.finished=true;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


