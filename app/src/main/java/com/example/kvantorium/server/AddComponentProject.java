package com.example.kvantorium.server;

import android.os.AsyncTask;

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

public class AddComponentProject extends AsyncTask<URL, Integer, ArrayList<String>> {

    ArrayList<String> request = new ArrayList<String>();
    int id_project;
    ArrayList<Integer> id_component;
    ArrayList<Integer> count;
    public AddComponentProject (int id_project, ArrayList<Integer> id_component, ArrayList<Integer> count) {
        this.id_project = id_project;
        this.id_component= id_component;
        this.count = count;
    }

    @Override
    protected ArrayList<String> doInBackground(URL... urls) {
        String id_component_;
        StringBuilder idComp = new StringBuilder();
        for (int i = 0; i < id_component.size(); i++) {
            if (i == 0) {
                idComp.append(id_component.get(i));
            }
            else {
                idComp.append("," + id_component.get(i));
            }
        }
        id_component_ = idComp.toString();

        String count_;
        StringBuilder countSb = new StringBuilder();
        for (int i = 0; i < count.size(); i++) {
            if (i == 0) {
                countSb.append(count.get(i));
            }
            else {
                countSb.append("," + count.get(i));
            }
        }
        count_ = countSb.toString();


        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "addComponentProject");
        params.put("ID", String.valueOf(id_project));
        params.put("ID_2", id_component_);
        params.put("COUNT", count_);

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
            String url = "http://192.168.1.14/PythonProject/server_test.py";
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
                String req = JObject.getString("request");
                System.out.println("From server: " + req);

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
}
