package com.example.qrcode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.Random;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private View loginButton, logoutButton;
    private TextView nickname;
    private ImageView profileImage;
    private Button Orderbtn, btnOrder, btnList, btnRandom;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent5 = new Intent(this, splashActivity.class);
        startActivity(intent5);
        Orderbtn= findViewById(R.id.Orderbtn);
        btnList = findViewById(R.id.btnList);
        btnRandom = findViewById(R.id.btnRandom);

        loginButton = findViewById(R.id.login);
        logoutButton = findViewById(R.id.logout);
        nickname = findViewById(R.id.nickname);
        profileImage = findViewById(R.id.profile);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null){
                    //TBD
                }
                if (throwable != null){
                    //TBD
                }
                updateKakaoLoginUi();
                return null;
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                   UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
                }else {
                   UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
               }

            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });

        updateKakaoLoginUi();



        Orderbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,CafeListActivity.class);
                startActivity(intent);


            }
        });

       btnList.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, ListActivity.class);
               startActivity(intent);
           }
       });

       btnRandom.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,RandomMenuActivity.class);
               startActivity(intent);
           }
       });
    }
    private void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null){
                    Log.i(TAG,"invoke: id=" + user.getId());
                    Log.i(TAG,"invoke: email=" + user.getKakaoAccount().getEmail());
                    Log.i(TAG,"invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG,"invoke: gender=" + user.getKakaoAccount().getGender());
                    Log.i(TAG,"invoke: age=" + user.getKakaoAccount().getAgeRange());


                    nickname.setText(user.getKakaoAccount().getProfile().getNickname());
                    Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileImage);
                    loginButton.setVisibility(View.GONE);
                    logoutButton.setVisibility(View.VISIBLE);


                }else{
                    nickname.setText(null);
                    profileImage.setImageBitmap(null);
                    loginButton.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.GONE);
                }
                return null;
            }
        });
    }


}