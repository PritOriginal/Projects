package com.example.kvantorium.server;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.example.kvantorium.Component;
import com.example.kvantorium.OnComponentsListener;

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

public class GetComponentsProject extends AsyncTask<URL, Integer, ArrayList<Component>> {
    private OnComponentsListener mListener;
    ProgressBar progressBar;
    ArrayList<Component> components = new ArrayList<Component>();
    int id;

    public GetComponentsProject(OnComponentsListener listener, int id, ProgressBar progressBar) {
        mListener = listener;
        this.id = id;
        this.progressBar = progressBar;
    }

    @Override
    protected ArrayList<Component> doInBackground(URL... urls) {
        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "getComponentsProject");
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
            String url = "http://192.168.1.14/PythonProject/server_test.py";
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
                JSONArray jArray = JObject.getJSONArray("components");
                for (i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    int id = jObject.getInt("id");
                    String name = jObject.getString("name");
                    int count = jObject.getInt("count");
                    String image = jObject.getString("image");
                    Component c = new Component();
                    c.setId(id);
                    c.setNameComponent(name);
                    c.setNumber(count);
                    c.setImage(image);
                    components.add(c);
                }

            } finally{
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return components;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.progressBar != null) {
            progressBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Component> components) {
        if (mListener != null) {
            mListener.onComponentsCompleted(components);
        }
    }
}
