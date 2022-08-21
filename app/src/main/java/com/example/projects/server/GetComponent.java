package com.example.projects.server;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.projects.Component;
import com.example.projects.OnComponentsListener;
import com.example.projects.R;

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

public class GetComponent extends AsyncTask<URL, Integer, ArrayList<Component>> {
    Context mContext;
    private OnComponentsListener mListener;
    ArrayList<Component> components = new ArrayList<Component>();
    int id;

    public GetComponent (Context mContext, OnComponentsListener mListener, int id) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.id = id;
    }

    @Override
    protected ArrayList<Component> doInBackground(URL... urls) {
        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "getComponent");
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

                int id = JObject.getInt("id");
                String name = JObject.getString("name");
                int count = JObject.getInt("count");
                String image = JObject.getString("image");
                String description = JObject.getString("description");
                String characteristics = JObject.getString("characteristics");
                String documentation = JObject.getString("documentation");
                Component c = new Component();
                c.setId(id);
                c.setNameComponent(name);
                c.setNumber(count);
                c.setImage(image);
                c.setDescription(description);
                c.setCharacteristics(characteristics);
                c.setDocumentation(documentation);
                components.add(c);

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
    protected void onPostExecute(ArrayList<Component> components) {
        if (mListener != null) {
            mListener.onComponentsCompleted(components);
        }
    }
}
