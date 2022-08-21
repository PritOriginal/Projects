package com.example.projects.server;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.example.projects.OnTestsListener;
import com.example.projects.R;
import com.example.projects.Test;

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

public class GetTestsUser extends AsyncTask<URL, Integer, ArrayList<Test>> {
    Context mContext;
    private OnTestsListener mListener;
    ProgressBar progressBar;
    ArrayList<Test> tests = new ArrayList<Test>();
    int id;
    public GetTestsUser(Context mContext, OnTestsListener mListener, int id, ProgressBar progressBar) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.id = id;
        this.progressBar = progressBar;
    }

    @Override
    protected ArrayList<Test> doInBackground(URL... urls) {
        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "getTestsUser");
        params.put("ID", String.valueOf(id));

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
            Resources res = mContext.getResources();
            String url = res.getString(R.string.url);
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

            try {
                byte[]postDataBytes = sbParams.toString().getBytes("UTF-8");
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
                JSONArray jArray = JObject.getJSONArray("tests");
                for (i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    int id = jObject.getInt("id");
                    String name = jObject.getString("name");
                    boolean completed = jObject.getInt("completed") == 1 ? true : false;
                    int progress = jObject.getInt("progress");
                    Test t = new Test(id, name, completed, progress);
                    tests.add(t);
                }

            } finally{
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tests;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.progressBar != null) {
            progressBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Test> tests) {
        if (mListener != null) {
            if (tests.size() != 0) {
                mListener.onTestsCompleted(tests);
            }
            else {
                mListener.onTestsError("Тестов нема");
            }
        }
    }
}
