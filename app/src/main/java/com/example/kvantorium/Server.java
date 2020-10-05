package com.example.kvantorium;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final String SERVER_ADDRESS = "http://localhost/PythonProject/server_test.py";

    public static String connect() {
        String data = null;

        HttpURLConnection conn;
        try {
            //URL url = new URL(SERVER_ADDRESS);
            //conn = (HttpURLConnection) url.openConnection();
           //conn.setConnectTimeout(10000); // ждем 10сек
           // conn.setRequestMethod("POST");
           // conn.setRequestProperty("User-Agent", "Mozilla/5.0");
           // conn.connect();
            data = SERVER_ADDRESS.toString();
        } catch (Exception e) {

        }
        return data;
    }





    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<========================================== ЧТО-ТО НЕ РАБОЧЕЕ
    public static final String LOG_TAG = "SOCKET";


    String host = null;
    int port = 0;
    // String myURL = "https://server/server.py";
    String myURL = "https://192.168.1.199";
    SQLiteDatabase db;
    Socket socket = null;
    public Server (final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    public void openConnection() throws Exception {
        // Если сокет уже открыт, то он закрывается
        closeConnection();
 //       try {
            // Создание сокета
  //         SocketAddress socketAddress = new InetSocketAddress(host,port);
  //          socket = new Socket();
  //          socket.connect(socketAddress);

            try {
                socket = new Socket(host, port);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.out.println("Unknown host...");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to connect...");
            }


//        } catch (IOException e) {
//            throw new Exception("Невозможно создать сокет: "
 //                   + e.getMessage());
 //       }
    }

    public void closeConnection()
    {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Ошибка при закрытии сокета :"
                        + e.getMessage());
            } finally {
                socket = null;
            }
        }
        socket = null;
    }

    public void sendData(String data) throws Exception {
        // Проверка открытия сокета
        if (socket == null || socket.isClosed()) {
            throw new Exception("Ошибка отправки данных. " +
                    "Сокет не создан или закрыт");
        }
        // Отправка данных
        try {
            socket.getOutputStream().write(data.getBytes());
            socket.getOutputStream().flush();
        } catch (IOException e) {
            throw new Exception("Ошибка отправки данных : "
                    + e.getMessage());
        }
    }

    public String getData() throws Exception {
        // Проверка открытия сокета
        if (socket == null || socket.isClosed()) {
            throw new Exception("Ошибка получинея данных. " +
                    "Сокет не создан или закрыт");
        }
        // Получение данных
        String data = null;
        try {
            data = socket.getInputStream().toString();
         //   socket.getOutputStream().write(data.getBytes());
         //   socket.getOutputStream().flush();
        } catch (IOException e) {
            throw new Exception("Ошибка получения данных : "
                    + e.getMessage());
        }
        return data;
    }

    public List<Component> getAllComponents() throws Exception {
        List<Component> allComponent = new ArrayList<Component>();
        Integer data = null;
        // Проверка открытия сокета
        if (socket == null || socket.isClosed()) {
            throw new Exception("Ошибка отправки данных. " +
                    "Сокет не создан или закрыт");
        }
        try {
            socket.getOutputStream().write("getAllComponents".getBytes());
            socket.getOutputStream().flush();
            data = Integer.valueOf(String.valueOf(socket.getInputStream()));
        //    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<================================================================================= ДОПИСАТЬ!!

        } catch (IOException e) {

        }

        return allComponent;
    }

    void getProject() throws IOException {
        byte[] data = null;

        //`Socket socket = new Socket("192.168.1.199", 0);


        URL url = new URL(myURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);

        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        //conn.setRequestProperty("Connection", "Keep-Alive");
        //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        //conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.getBytes().length));
        //conn.setDoOutput(true);
        //conn.setDoInput(true);

        OutputStream os = conn.getOutputStream();

        // Нужно сначало конвентировать
        os.write(data);

    }
 //   void getAllComponents() throws IOException{
 //      byte[] data = null;
 //
 //       URL url = new URL(myURL);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
 //   }

}
