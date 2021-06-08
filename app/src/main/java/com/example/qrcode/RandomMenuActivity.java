package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class RandomMenuActivity extends AppCompatActivity {
    public View cardview;
    public ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_menu);
        getSupportActionBar().setTitle("메뉴추천");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFCD853F));

        final Random rand = new Random();
        final int[] server_ico = {
                R.drawable.starbuckslogo, R.drawable.ediyalogo, R.drawable.twosome
        };
        int server_num = rand.nextInt(server_ico.length);
        cardview = findViewById(R.id.cardView2);


        final ImageView server_img = (ImageView) findViewById(R.id.eVanilla);
        server_img.setImageResource(server_ico[server_num]);


        final TextView textView14 = (TextView) findViewById(R.id.textView14);
        if (server_ico[server_num] == R.drawable.starbuckslogo) {
            textView14.setText("오늘은 스타벅스에서 여유로운 한잔을..~");
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent1 = new Intent(getApplicationContext(), StarbucksChoiceActivity.class);
                    startActivity(intent1);


                }
            });
        }
        else if (server_ico[server_num] == R.drawable.ediyalogo) {
            textView14.setText("너의 카페 나의 카페 모두의 카페 이디야로~");
                cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(getApplicationContext(), EdiyaChoiceActivity.class);
                        startActivity(intent2);
                    }

                });
            } else if (server_ico[server_num] == R.drawable.twosome) {
            textView14.setText("원두가 살아숨쉬는 투썸으로 당첨!");
                cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent(getApplicationContext(), TwosomeChoiceActivity.class);
                        startActivity(intent3);

                    }
                });

            }


        }
    }









