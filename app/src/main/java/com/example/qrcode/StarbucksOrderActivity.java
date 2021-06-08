package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class StarbucksOrderActivity extends AppCompatActivity {

    Button drinkOrderbtn;
    EditText orderEdit;

    private String DrinkName;
    private String DrinkDetails;
    private String Nickname;

    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String OrderTime = mFormat.format(mReDate);

    String sAmericano;
    String sCafemoca;
    String sVanillalatte;
    String sDolce;
    String sToffenut;
    static int i;
    public static int s;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("sample");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starbucks_order);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFCD853F));
        getSupportActionBar().setTitle("주문서");
        Intent intent = getIntent();


        sAmericano = intent.getStringExtra("StarbucksAmericano");
        sVanillalatte = intent.getStringExtra("StarbucksVanillaLatte");
        sCafemoca = intent.getStringExtra("StarbucksCafemoca");
        sDolce = intent.getStringExtra("StarbucksDolceLatte");
        sToffenut = intent.getStringExtra("Starbucks ToffenutLatte");


        drinkOrderbtn = (Button)findViewById(R.id.drinkOrderbtn);
        orderEdit = (EditText)findViewById(R.id.orderEdit);



       if(sAmericano!=null) {
           DrinkName = sAmericano;
       }else if( sVanillalatte!=null){
           DrinkName = sVanillalatte;}
       else if(sCafemoca!= null) {
           DrinkName =sCafemoca;
       } else if(sDolce!= null) {
           DrinkName = sDolce;
       } else if(sToffenut!= null) {
           DrinkName = sToffenut;
       }
       else{return;}



        drinkOrderbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                i++;
                s++;

                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {

                    @Override
                    public Unit invoke(User user, Throwable throwable) {
                        DrinkDetails = orderEdit.getText().toString();

                        String nickname = user.getKakaoAccount().getProfile().getNickname();


                         Menu menu = new Menu(nickname,DrinkName,DrinkDetails,OrderTime);

                        databaseReference.child("menu").child("'"+nickname+"'").child(i+"번째 주문").setValue(menu);
                        databaseReference.child("menu").child("POS").child("STARBUCKS").child(nickname+"고객의"+s+"번째 주문").setValue(menu);

                        return null;

                    }

                });

                Intent intent6 = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent6);

            }
        });
    }
}