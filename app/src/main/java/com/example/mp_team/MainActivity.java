package com.example.mp_team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NetPermission;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    private TextView tv_outPut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_outPut = (TextView) findViewById(R.id.tv_outPut);

        String service_key = "hr23IstJO5YHz0t55U3QL7JAB8OfUmvWdz9PdDZxe7dUKnbdTGcK1RhBgrh8b5QV3LqYMRMYFMD3IkKvnwjFcg%3D%3D";
        String num_of_rosws = "10";
        String page_no = "1";
        String data_type = "JSON";
        String base_date = "20211108";
        String base_time = "0600";
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

            tv_outPut.setText(s);
            Log.d("onPostEx", "출력값 : " + s);
        }
    }
}

