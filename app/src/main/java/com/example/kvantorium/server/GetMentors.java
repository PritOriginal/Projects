package com.example.kvantorium.server;

import android.os.AsyncTask;

import com.example.kvantorium.OnMentorsListener;
import com.example.kvantorium.Teammate;
import com.example.kvantorium.User;

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

public class GetMentors extends AsyncTask<URL, Integer, ArrayList<User>> {
    private OnMentorsListener mListener;
    ArrayList<User> mentors = new ArrayList<User>();

    public GetMentors(OnMentorsListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected ArrayList<User> doInBackground(URL... urls) {
        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "getMentors");

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
                JSONArray jArray = JObject.getJSONArray("mentors");
                for (i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    int id = jObject.getInt("id");
                    String firstName = jObject.getString("patronymic");
                    String secondName = jObject.getString("name");
                    String role = "Наставник " + jObject.getString("role");
                    User mentor = new User(id, firstName, secondName, role);
                    mentors.add(mentor);
                }
            } finally{
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mentors;
    }
    @Override
    protected void onPostExecute(ArrayList<User> mentors) {
        if (mListener != null) {
            mListener.onMentorsCompleted(mentors);
        }
    }
}
