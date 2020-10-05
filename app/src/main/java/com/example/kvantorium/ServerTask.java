package com.example.kvantorium;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerTask extends AsyncTask<URL, Integer, ArrayList<Component>> {

    FragmentTwo parent;

    private OnComponentsListener mListener;
    ArrayList<Component> components = new ArrayList<Component>();

    public ServerTask(OnComponentsListener listener)
    {
        mListener = listener;
    }

    @Override
    protected ArrayList<Component> doInBackground(URL... urls) {
        HashMap<String, String> params = new HashMap<>();
        params.put("REQUEST","getAllComponents");

        String data = null;

        StringBuilder sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), "windows-1251"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        try {

            //String url = "http://192.168.1.69/PythonProject/server_test.py";
            String url = "http://192.168.243.90/PythonProject/server_test.py";
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

/*
            try {
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset", "UTF-8");

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                String paramsString = sbParams.toString();
              //  String paramsString = "a=test";

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

*/


            try {
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                //conn.setRequestProperty("Accept-Charset", "windows-1251");
             //   conn.setRequestProperty("Content-Type", "text");

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                String paramsString = sbParams.toString();
                //  String paramsString = "a=test";

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(in));
                StringBuilder result1 = new StringBuilder();
                String line1;

                result1.append(line1 = reader1.readLine());
           //     System.out.println(result1.toString());
                Component c = new Component();
                c.setNameComponent(result1.toString());
                result1.append(line1 = reader1.readLine());
                c.setNumber(Integer.valueOf(result1.toString()));
                //Log.d("test", "result from server: " + result1.toString());
              //  result1.append(line1 = reader1.readLine());
             //   result1.append(line1 = reader1.readLine());
             //   result1.append(line1 = reader1.readLine());
             //   result1.append(line1 = reader1.readLine());
            //    result1.append(line1 = reader1.readLine());
           //     result1 = new StringBuilder();
             //   result1.append(line1 = reader1.readLine());
            //    result1.append(line1 = reader1.readLine());
           //     Log.d("test", "result from server: " + result1.toString());
          //      Component c = new Component();
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<================================================================= ЗДЕСЬ ЧТО ТО НЕ ТАК!!!!!!!!!!!!!!!!!!!!!!!!!!

                /*
                result1 = new StringBuilder();
                result1.append(line1 = reader1.readLine());
                data = result1.toString();
                c.setNameComponent(data);
           //     Log.d("test", "result from server: " + data);
                result1 = new StringBuilder();
                result1.append(line1 = reader1.readLine());
                result1 = new StringBuilder();
                result1.append(line1 = reader1.readLine());
                data = result1.toString();
                c.setNumber(Integer.valueOf(data));
         //       Log.d("test", "result from server: " + data);
                result1 = new StringBuilder();
                result1.append(line1 = reader1.readLine());
                components.add(c);
                */

                /*
                boolean name = true;
                while ((line1 = reader1.readLine()) != null) {
                    result1 = new StringBuilder();
                    result1.append(line1);

                    result1.delete(0,2);

                    if (name) {
                        c = new Component();
                        c.setNameComponent(result1.toString());
                        Log.d("test", "result from server: " + result1.toString());
                        name = false;
                    }
                    else {
                        c.setNumber(Integer.parseInt(result1.toString()));
                        components.add(c);
                        name = true;
                        Log.d("test", "result from server: " + result1.toString());
                    }
                    result1.append(line1);
                }
*/
                //while ((line1 = reader1.readLine()) != null) {
                //    result1.append(line1);
                //}

                //StringBuilder data_str = result1;



       //         data = result1.toString();
//                Log.d("test", "result from server: " + data);

            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }catch (Exception e) {

        }
            return components;
    }
/////m<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<=====================================================
    @Override
    protected void onPostExecute(ArrayList<Component> components) {
  //      super.onPostExecute(components);
        //do stuff
        //how to return a value to the calling method?
//        parent.updat(components);
        if (mListener != null) {
            mListener.onComponentsCompleted(components);
        }
    }

    // <<<<<<<<<<<<<<<<<<<<<<+=========================================== Типо функция
}
