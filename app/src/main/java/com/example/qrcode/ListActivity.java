package com.example.qrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import static java.security.AccessController.getContext;

public class ListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Menu> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button button = findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFCD853F));
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();




        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override

            public Unit invoke(User user, Throwable throwable) {

                database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
                databaseReference = database.getReference("sample").child("menu").child("'"+user.getKakaoAccount().getProfile().getNickname()+"'");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                       Menu menu = snapshot.getValue(Menu.class);
                                       arrayList.add(0,menu);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("ListActivity", String.valueOf(databaseError.toException()));
                    }
                });
                return null;

            }

        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);


    }


}




