package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CafeListActivity extends AppCompatActivity {

    View starbucks, ediya, twosome;
    Button starAsk, ediyaAsk, twosomeAsk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_list);

        starbucks = findViewById(R.id.starbuckscard);
        ediya = findViewById(R.id.ediyacard);
        twosome = findViewById(R.id.twosomecard);
        starAsk = findViewById(R.id.starAsk);
        ediyaAsk= findViewById(R.id.ediyaAsk);
        twosomeAsk = findViewById(R.id.twosomeAsk);


        starbucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StarbucksChoiceActivity.class);
                startActivity(intent);
            }
        });

        ediya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EdiyaChoiceActivity.class);
                startActivity(intent);
            }
        });
        twosome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TwosomeChoiceActivity.class);
                startActivity(intent);
            }
        });
        starAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StartbucksRequest.class);
                startActivity(intent);
            }
        });
        ediyaAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EdiyaRequest.class);
                startActivity(intent);
            }
        });
        twosomeAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TwoRequest.class);
                startActivity(intent);
            }
        });


    }


}