package com.example.kvantorium;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.kvantorium.server.GetAllComponents;

import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends Fragment implements OnComponentsListener, android.widget.SearchView.OnCloseListener, android.widget.SearchView.OnQueryTextListener {
    public static final String LOG_TAG = "SOCKET";

    public static final String SERVER_ADDRESS = "http://192.168.1.69/PythonProject/server_test.py";


    RecyclerView recyclerView;
    SQLiteDatabase database;
    RVAdapterComponents adapter;
    DBHelper dbHelper;
    List<Component> allComponent = new ArrayList<Component>();
    android.widget.SearchView searchView;
    ProgressBar progressBar;

    String data = null;

    //String HOST = "91.219.102.45";
    //String HOST = "192.168.1.10";
    String HOST = "10.0.2.2";
    int PORT = 8080;

    Test test;

    public FragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_all_components, container,
                false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_allComponents);
        LinearLayoutManager llm = new LinearLayoutManager(test.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        searchView = (android.widget.SearchView) view.findViewById(R.id.searchComponents);
        searchView.setOnQueryTextListener(this);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarAllComponents);
        progressBar.setProgress(0);
        System.out.println("Message sent to the server : ");
        GetAllComponents testTask = new GetAllComponents(this, progressBar);
        testTask.execute();

        //      new ServerTask(this).execute();

        // adapter = new RVAdapterAllComponents(test, allComponent);
        // recyclerView.setAdapter(adapter);

        /*
        TestTask testTask = new TestTask();
        testTask.execute();
        try {
            allComponent = testTask.get();
    //        System.out.println("Message received from the server : " + sd);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
/* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< КАКАЯ ТО ДИЧЬ!!!!!!!!!!!!!
        ServerTask serverTask = new ServerTask(this);

        serverTask.execute();
        try {
            allComponent = serverTask.get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
*/

//        URL url = new URL("http://192.168.1.32/PythonProject/server_test.py");


        //InputStream inputStream =
        /*
        try {

            URL url = new URL("http://192.168.1.32/PythonProject/server_test.py");

            String sParameters = "a=1&b=2&c=3"; // POST data

            HttpsURLConnection urlConnection = null;

            BufferedReader input = null;

            StringBuilder returnString = new StringBuilder();


            try {

                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(sParameters.getBytes().length));

                urlConnection.setRequestProperty("Content-Language", "en-US");

                urlConnection.setRequestProperty("Accept", "application/json");

                urlConnection.setUseCaches(false);

                urlConnection.setDoInput(true);

                urlConnection.setDoOutput(true);


                // Send request

                DataOutputStream output = new DataOutputStream(urlConnection.getOutputStream());

                output.writeBytes(sParameters);

                output.flush();

                output.close();


                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"), 8);

                String line = null;

                while ((line = reader.readLine()) != null) {

                    returnString.append(line);

                }

            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                if (urlConnection != null) {

                    urlConnection.disconnect();

                }

            }
        } catch (Exception e) {

        }
        */
/*
        HttpURLConnection conn;
        try {

            //URL url = new URL(SERVER_ADDRESS);
            //conn = (HttpURLConnection) url.openConnection();
            //conn.setConnectTimeout(10000); // ждем 10сек
            // conn.setRequestMethod("POST");
            // conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            // conn.connect();
            data = SERVER_ADDRESS.toString();
            System.out.println(data);

        } catch (Exception e) {

        }

*/
        /*
        try {
            Socket socket = new Socket("192.168.1.10", 8080);

            //Send message to server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String number = "tset";

            String sendMessage = number + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Message sent to the server : " + sendMessage);
            Thread.sleep(10000);
            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("Message received from the server : " + message);
        } catch (Exception e){

        }
        */

/*
        if (Internet.isOnline(test)) {


            Server server = new Server(HOST, PORT);

            try {

                server.openConnection();
                Toast toast = Toast.makeText(test,
                        "Пора покормить кота!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                String request = "getAllComponents";
                server.sendData(request);
                int components = Integer.valueOf(server.getData());
                int i = 0;
                while (i < components) {
                    Component c = new Component();
                    String n = server.getData();
                    c.setNameComponent(server.getData());
                    int all = Integer.valueOf(server.getData());
                    int use = Integer.valueOf(server.getData());
                    c.setNumber(all - use);
                    allComponent.add(c);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Ошибка при закрытии сокета :"
                        + e.getMessage());
                server = null;
            }
        } else {

            dbHelper = new DBHelper(test);
            database = dbHelper.getWritableDatabase();
            allComponent = dbHelper.getAllComponents(database);

        }

*/


//        allComponent = dbHelper.getAllComponents(database);

        //     dbHelper = new DBHelper(test);
        //      database = dbHelper.getWritableDatabase();
        //      allComponent = dbHelper.getAllComponents(database);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Test) {
            this.test = (Test) context;
        }
    }

    public void updat(ArrayList<Component> components) {
        adapter = new RVAdapterComponents(test, allComponent);
        recyclerView.setAdapter(adapter);
    }

    void updateComp(String data) {
        String[] str = data.split("");

        boolean k = false;
        int j = 1;
        String data_ = null;
        for (int i = 0; i < data.length(); i++) {
            if (str[i] == "?") {
                k = true;
            }
            if (k) {
                data_ += str[i];

                if (str[i] == "?") {
                    if (j == 1) {
                        Component c = new Component();
                        c.setNameComponent(data_);
                        j = 2;
                    }
                    if (j == 2) {
                        Component c = new Component();
                        c.setNumber(Integer.parseInt(data_));
                        allComponent.add(c);
                        j = 1;
                    }
                }
            }
        }
    }

    @Override
    public void onComponentsCompleted(ArrayList<Component> components) {
        allComponent = components;
        System.out.println("Components size " + components.size());
        adapter = new RVAdapterComponents(test, allComponent);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onComponentsError(String error) {

    }

    @Override
    public void onComponentChangeNumberCompleted(int number, int index, Component c) {

    }

    @Override
    public void onComponentChangeNumberError(String error) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        final List<Component> filteredModelList = filter(allComponent, s);
        adapter.setComponents(filteredModelList);
        return true;
    }

    private static List<Component> filter(List<Component> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Component> filteredModelList = new ArrayList<>();
        for (Component model : models) {
            final String text = model.getNameComponent().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onClose() {
        adapter.setComponents(allComponent);
        return true;
    }
}
