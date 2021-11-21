package com.example.mp_team;

import android.content.ContentValues;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RequestHttpConnection {

    public String request(String _url, ContentValues _params){
        HttpURLConnection urlConn = null;
        StringBuffer sbParams = new StringBuffer();

        if(_params == null)
            sbParams.append("");
        else{
            boolean isAnd = false;
            String key;
            String value;

            for(Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getKey().toString();

                if(isAnd)
                    sbParams.append("&");

                sbParams.append(key).append("=").append(value);

                if(!isAnd)
                    if(_params.size() >= 2)
                        isAnd = true;
            }

        }

        try{
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setDoOutput(false);

            if(urlConn.getResponseCode() != HttpURLConnection.HTTP_OK){
                Log.d("HTTP_OK","연결 요청 실패");
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            String line;
            String page= "";

            while ((line = reader.readLine()) != null){
                page += line;
            }

            return page;
        } catch (MalformedURLException e){
            Log.d("MalformedUrlException", String.valueOf(e));
        } catch (IOException e){
            Log.d("IOException", String.valueOf(e));
        } finally{
            if(urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }
}
