package com.coderankit.temp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String url = "https://ankit5125.github.io/Json-API/data.json";

    Button button ;
    TextView fname,lname,age,gender,username,password;
    ImageView avatar;
    Random ran;
    JsonObjectRequest objectRequest;

    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setUpIds();
        setUpJsonObject();

        button.setOnClickListener(v -> {
            if (jsonArray != null) {
                int index = ran.nextInt(jsonArray.length());
                try {
                    JSONObject object = jsonArray.getJSONObject(index);
                    fname.setText(object.getString("firstName"));
                    lname.setText(object.getString("lastName"));
                    age.setText(String.valueOf(object.getInt("age")));
                    gender.setText(object.getString("gender"));
                    username.setText(object.getString("username"));
                    password.setText(object.getString("password"));

                    Glide.with(MainActivity.this).load(object.getString("image")).into(avatar);
                }
                catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error Occurred While Setting names...", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Data not loaded yet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpJsonObject() {
        objectRequest = new JsonObjectRequest(url, response -> {

            try {
                jsonArray = response.getJSONArray("users");
                Toast.makeText(MainActivity.this, "Set Array Successfully", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Error While Setting Json Array", Toast.LENGTH_SHORT).show();
            }

        }, error -> Toast.makeText(MainActivity.this, "Error Occurred : " + error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(objectRequest);
        Toast.makeText(this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
    }

    private void setUpIds() {
        button = findViewById(R.id.button);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        avatar = findViewById(R.id.avatar);

        ran = new Random();
    }
}