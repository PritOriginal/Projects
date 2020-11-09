package com.example.kvantorium.server;

import android.os.AsyncTask;

import com.example.kvantorium.Component;
import com.example.kvantorium.OnComponentsListener;
import com.example.kvantorium.OnRequestLoginListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AsyncTask<URL, Integer, ArrayList<String>> {

    private OnRequestLoginListener mListener;

    ArrayList<String> request = new ArrayList<String>();
    String login;
    String password;

    public Login (OnRequestLoginListener mListener,String login, String password) {
        this.mListener = mListener;
        this.login = login;
        this.password =  password;
    }

    @Override
    protected ArrayList<String> doInBackground(URL... urls) {
        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "login");
        params.put("LOGIN", login);
        params.put("PASSWORD", password);
        String data = null;

        StringBuilder sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), "UTF-8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }


        try {
//            String params = "REQUEST=js";
            String url = "http://192.168.1.69/PythonProject/server_test.py";
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

            try {
                byte[]postDataBytes = sbParams.toString().getBytes("UTF-8");
                //conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/text");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);

                conn.connect();

                /*
                String paramsString = sbParams.toString();
                //  String paramsString = "a=test";

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();
                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(in));
                StringBuilder result1 = new StringBuilder();
                String line;
                while ((line = reader1.readLine()) != null) {
                    result1.append(line + "\n");
                }

                in.close();

                String result = result1.toString();
                System.out.println("From server: " + result);

                JSONObject JObject = new JSONObject(result);
                String request_ = JObject.getString("request");
                int id = JObject.getInt("id");
                int mentor = JObject.getInt("mentor");
                String name = JObject.getString("name");
                String secondname = JObject.getString("secondname");
                request.add(request_);
                request.add(String.valueOf(id));
                request.add(String.valueOf(mentor));
                request.add(name);
                request.add(secondname);
            } finally{
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
    @Override
    protected void onPostExecute(ArrayList<String> request) {
        //do stuff
        if (mListener != null) {
            mListener.onRequestCompleted(request);
        }
    }
}
