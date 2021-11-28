package com.example.mp_team;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private TextView tv_outPut;
    private Vector<Double> temp = new Vector<Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_outPut = (TextView) findViewById(R.id.tv_outPut);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfh = new SimpleDateFormat("hhmm");
        String getDate = sdf.format(date);
        String getTime = sdfh.format(date);

        String service_key = "hr23IstJO5YHz0t55U3QL7JAB8OfUmvWdz9PdDZxe7dUKnbdTGcK1RhBgrh8b5QV3LqYMRMYFMD3IkKvnwjFcg%3D%3D";
        String num_of_rosws = "100";
        String page_no = "1";
        String data_type = "JSON";
        String base_date = getDate;
        String base_time = getTime;
        String nx = "55";
        String ny = "127";

        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?" +
                "serviceKey=" + service_key +
                "&numOfRows=" + num_of_rosws +
                "&pageNo=" + page_no +
                "&dataType=" + data_type +
                "&base_date=" + base_date +
                "&base_time=" + base_time +
                "&nx=" + nx +
                "&ny=" + ny;

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
    }

    public class NetworkTask extends AsyncTask<Void, Void, String>{
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params){
            String result;
            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            JSONObject mainObject = null;
            try {
                mainObject = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray itemArray = null;
            try {
                itemArray = mainObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0; i<itemArray.length(); i++) {
                JSONObject item = null;
                try {
                    item = itemArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String fcstTime = null;
                try {
                    fcstTime = item.getString("fcstTime");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String category = null;
                try {
                    category = item.getString("category");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String value = null;
                try {
                    value = item.getString("fcstValue");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (category.equals("T1H")) {
                    tv_outPut.append(fcstTime + " " + category + "  " + value + "\n"); }
            }
            Log.d("onPostEx", "출력값 : " + s);
        }
    }

}

