package com.example.projects.server;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.projects.Answer;
import com.example.projects.OnTestsListener;
import com.example.projects.Question;
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

public class GetTest extends AsyncTask<URL, Integer, ArrayList<Test>> {
    Context mContext;
    private OnTestsListener mListener;
    ArrayList<Test> tests = new ArrayList<Test>();
    int id;
    public GetTest(Context mContext, OnTestsListener mListener,int id) {
        this.mContext = mContext;
        this.id = id;
        this.mListener = mListener;
    }
    @Override
    protected ArrayList<Test> doInBackground(URL... urls) {
        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST", "getTest");
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
                    ArrayList<Question> questions = new ArrayList<Question>();
                    JSONArray jsonArrayQuestion = jObject.getJSONArray("questions");
                    for(int j = 0; j < jsonArrayQuestion.length(); j++) {
                        JSONObject jObjectQuestion = jsonArrayQuestion.getJSONObject(j);
                        int id_question = jObjectQuestion.getInt("id");
                        String question = jObjectQuestion.getString("question");
                        ArrayList<Answer> answers = new ArrayList<Answer>();
                        JSONArray jsonArrayAnswer = jObjectQuestion.getJSONArray("answers");
                        for (int k = 0; k < jsonArrayAnswer.length(); k++) {
                            JSONObject jObjectAnswer = jsonArrayAnswer.getJSONObject(k);
                            int id_answer = jObjectAnswer.getInt("id");
                            String answer = jObjectAnswer.getString("answer");
                            boolean correct = jObjectAnswer.getInt("correct") == 1 ? true : false;
                            Answer a = new Answer(id_answer, answer, correct);
                            answers.add(a);
                        }
                        Question q = new Question(id_question, question, answers);
                        questions.add(q);
                    }
                    Test test = new Test(id, name, questions);
                    tests.add(test);
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
    protected void onPostExecute(ArrayList<Test> tests) {
        if (mListener != null) {
            if (tests.size() != 0) {
                mListener.onTestsCompleted(tests);
            }
            else {
                mListener.onTestsError("Теста нема");
            }
        }
    }
}
