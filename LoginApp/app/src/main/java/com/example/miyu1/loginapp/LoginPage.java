package com.example.miyu1.loginapp;

//Android packages
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//Project packages
import com.example.miyu1.loginapp.data.model.Post;
import com.example.miyu1.loginapp.data.remote.APIService;
import com.example.miyu1.loginapp.data.remote.ApiUtils;
import com.google.gson.Gson;

//Retrofit packages
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPage extends AppCompatActivity{
    ProgressDialog progressDialog = null;
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button getUser;
    private int counter = 5;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        progressDialog = new ProgressDialog(LoginPage.this);
        mAPIService = ApiUtils.getAPIService();
        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPass);
        Info = findViewById(R.id.tvInfo);
        Login = findViewById(R.id.btnLogin);
        getUser = findViewById(R.id.getUserBtn);

        Info.setText("No of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMax(100);
                progressDialog.setMessage("Loading. Please wait.");
                progressDialog.show();
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });
        getUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progressDialog.setMax(100);
                progressDialog.setMessage("Loading. Please wait.");
                progressDialog.show();
                getUserData(Name.getText().toString(),Password.getText().toString());
            }
        });

    }

    public void getUser(String username, String password){
        try{
            JSONObject paramObject = new JSONObject();
            paramObject.put("username", username);
            paramObject.put("password", password);
            Call<Post> postCall = mAPIService.findUser(paramObject);
            postCall.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    progressDialog.dismiss();
                    if(response.isSuccessful()) {
                        Intent newIntent;
                        Post newPost = response.body();
                        if(newPost != null){
                            if(newPost.toString().contains("null")) {
                                Toast.makeText(LoginPage.this, "Invalid Login credentials. Please try again with correct credentials!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                newIntent = new Intent(LoginPage.this, SecondActivity.class);
                                newIntent.putExtra("username", newPost.getUsername());
                                newIntent.putExtra("level", String.valueOf(newPost.getLevel()));
                                newIntent.putExtra("current_exp", String.valueOf(newPost.getCurr_exp()));
                                newIntent.putExtra("needed_exp", String.valueOf(newPost.getNeeded_exp()));
                                startActivity(newIntent);
                            }
                        }
                        else{
                            Toast.makeText(LoginPage.this, "Something went wrong. Please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        if(response.code() == 403){
                            Toast.makeText(LoginPage.this, "Invalid Login credentials. Please try again with correct credentials!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginPage.this, "Something went wrong. Please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {

                }
            });
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void sendPost(String username, String password) {
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("username", username);
            paramObject.put("password", password);
            Call<Post> postCall = mAPIService.savePost(paramObject);
            postCall.enqueue(new Callback<Post>(){
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    progressDialog.dismiss();
                    if(response.isSuccessful()) {
                        Intent openapp = getPackageManager().getLaunchIntentForPackage("com.mcdonalds.app");
                        if(openapp == null){
                            openapp = new Intent(Intent.ACTION_VIEW);
                            openapp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            openapp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            openapp.setData(Uri.parse("market://details?id=com.mcdonalds.app"));
                        }
                        openapp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(openapp);
                    }
                    else{
                        if(response.code() == 403){
                            Toast.makeText(LoginPage.this, "Invalid Login credentials. Please try again with correct credentials!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginPage.this, "Something went wrong. Please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginPage.this, "Something went wrong. Please try again later!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void getUserData(String userName, String userPass){
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPass)) {
            getUser(userName,userPass);
        }
        else{
            counter--;
            Info.setText("No of atttempts remaining: " + String.valueOf(counter));
            if(counter == 0) {
                Login.setEnabled(false);
            }
        }
    }

    private void validate(String userName, String userPass){
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPass)){
            sendPost(userName, userPass);
        }else{
            counter--;
            Info.setText("No of atttempts remaining: " + String.valueOf(counter));
            if(counter == 0) {
                Login.setEnabled(false);
            }
        }
    }
}
