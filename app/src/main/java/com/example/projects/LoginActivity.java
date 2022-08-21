package com.example.projects;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projects.server.Login;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements OnRequestLoginListener {
    EditText inLogin;
    EditText inPassword;
    Button signIn;

    String login;
    String password;

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";

    List<String> request = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        inLogin = (EditText) findViewById(R.id.login);
        inPassword = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        mSettings = getSharedPreferences("MyPref", MODE_PRIVATE);
    }

    public void Login(View view) {
        login = inLogin.getText().toString();
        password = inPassword.getText().toString();

        Cashe cashe = new Cashe();
        password = cashe.md5(password);
        System.out.println("PASSWORD: " + password);

        Login task = new Login(this, this, login, password);
        task.execute();
    }

    @Override
    public void onRequestCompleted(ArrayList<String> req) {
        request = req;
        System.out.println("REQUEST: " + request.get(0));
        if (request.get(0).equals("true")) {
            System.out.println("REQUEST: OK");
            // Запоминаем данные
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt("USER_ID", Integer.parseInt(request.get(1)));
            editor.putBoolean("MENTOR", Integer.parseInt(request.get(2)) == 1 ? true : false);
            editor.putString("NAME", request.get(3));
            editor.putString("SECOND_NAME", request.get(4));
            editor.putString("VK", request.get(5));
            editor.apply();

            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
        }
    }

    @Override
    public void onComponentsError(String error) {

    }
}
