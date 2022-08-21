package com.example.projects.server;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.projects.Question;
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

public class SendTest extends AsyncTask<URL, Integer, ArrayList<String>> {
    Context mContext;
    ArrayList<String> request = new ArrayList<String>();
    int id_user;
    int id_test;
    ArrayList<Question> questions;
    public SendTest(Context mContext, int id_user, int id_test, ArrayList<Question> questions) {
        this.mContext = mContext;
        this.id_user = id_user;
        this.id_test = id_test;
        this.questions = questions;
    }
    @Override
    protected ArrayList<String> doInBackground(URL... urls) {
        String id_answers;
        StringBuilder idAnswers = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            if (i == 0) {
                idAnswers.append(questions.get(i).getSelected());
            }
            else {
                idAnswers.append("," + questions.get(i).getSelected());
            }
        }
        id_answers = idAnswers.toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "sendTest");
        params.put("ID", String.valueOf(id_user));
        params.put("ID_2",String.valueOf(id_test));
        params.put("DATA", id_answers);

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
