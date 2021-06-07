package com.example.qrcode;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class StartbucksRequest extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    public  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private User user;


    //private String nick; // ID 전달 받아서 넣기

    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startbucks_request);
        Button_send = findViewById(R.id.E_Button_send);
        EditText_chat = findViewById(R.id.EdiyaText_chat);


        mRecyclerView = findViewById(R.id.Ediya_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();

        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override

            public Unit invoke(User user, Throwable throwable) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //myRef = database.getReference();
                String nick = user.getKakaoAccount().getProfile().getNickname();
                myRef = database.getReference("Message").child("Starbucks").child(nick);


                Button_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = EditText_chat.getText().toString(); //msg

                        if (msg != null) {
                            ChatData chat = new ChatData();
                            chat.setName(nick);
                            chat.setMsg(msg);
                            myRef.setValue(chat);
                        }

                    }  });


                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                        ChatData chat = dataSnapshot.getValue(ChatData.class);
                        ((StarChatAdapter) mAdapter).addChat(chat);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("QuestActivity", String.valueOf(databaseError.toException()));
                    }
                });
                return null;
            }
        });

        /*mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();*/
        mAdapter = new StarChatAdapter(chatList, StartbucksRequest.this);
        mRecyclerView.setAdapter(mAdapter);

        // Write a message to the database



    }
}