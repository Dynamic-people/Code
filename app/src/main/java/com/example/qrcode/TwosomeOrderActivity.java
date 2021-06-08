package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import static com.example.qrcode.StarbucksOrderActivity.i;

public class TwosomeOrderActivity extends AppCompatActivity {

    Button drinkOrderbtn;
    EditText orderEdit;

    private String DrinkName;
    private String DrinkDetails;
    private String Nickname;

    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String OrderTime = mFormat.format(mReDate);

    String tAmericano;
    String tCafemoca;
    String tDalgona;
    String tColdbrew;
    String tColdbrewtonic;
    String tCalamel;
    String tSignature;




    public static int t;



    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("sample");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twosome_order);
        getSupportActionBar().setTitle("주문서");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFCD853F));
        Intent intent = getIntent();


        tAmericano = intent.getStringExtra("TwosomeAmericano");
        tCafemoca = intent.getStringExtra("TwosomeCafemoca");
       tDalgona= intent.getStringExtra("TwosomeDalgonalatte");
       tColdbrew = intent.getStringExtra("TwosomeColdbrew");
        tSignature = intent.getStringExtra("TwosomeSignature");
       tCalamel = intent.getStringExtra("TwosomeCaramel");
       tColdbrewtonic = intent.getStringExtra("TwosomeColdbrewtonic");


        drinkOrderbtn = (Button)findViewById(R.id.drinkOrderbtn);
        orderEdit = (EditText)findViewById(R.id.orderEdit);



        if(tAmericano!=null) {
            DrinkName = tAmericano;
        }else if(tCafemoca!=null){
            DrinkName = tCafemoca;}
        else if(tDalgona!= null) {
            DrinkName = tDalgona;
        }else if(tColdbrew!= null) {
            DrinkName =tColdbrew;
        }
        else if( tSignature!= null) {
            DrinkName =  tSignature;
        } else if(  tCalamel!= null) {
            DrinkName =   tCalamel;
        }
        else if( tColdbrewtonic!= null) {
            DrinkName =  tColdbrewtonic;
        }
        else{return;}



        drinkOrderbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                i++;
                t++;

                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {

                    @Override
                    public Unit invoke(User user, Throwable throwable) {
                        DrinkDetails = orderEdit.getText().toString();

                        String nickname = user.getKakaoAccount().getProfile().getNickname();


                        Menu menu = new Menu(nickname,DrinkName,DrinkDetails,OrderTime);

                        databaseReference.child("menu").child("'"+nickname+"'").child(i+"번째 주문").setValue(menu);
                        databaseReference.child("menu").child("POS").child("TWOSOME").child(nickname+"고객의"+t+"번째 주문").setValue(menu);

                        return null;

                    }

                });

                Intent intent6 = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent6);

            }
        });
    }
}