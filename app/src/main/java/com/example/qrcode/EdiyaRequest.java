package com.example.qrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class EdiyaRequest extends AppCompatActivity {
    private RecyclerView eRecyclerView;
    public  RecyclerView.Adapter eAdapter;
    private RecyclerView.LayoutManager eLayoutManager;
    private List<ChatDataE> EchatList;
    private User user;
    //private String nick; // ID 전달 받아서 넣기

    private EditText EdiyaText_chat;
    private Button E_Button_send;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ediya_request);
        E_Button_send = findViewById(R.id.E_Button_send);
        EdiyaText_chat = findViewById(R.id.EdiyaText_chat);


        eRecyclerView = findViewById(R.id.Ediya_recycler_view);
        eRecyclerView.setHasFixedSize(true);
        eLayoutManager = new LinearLayoutManager(this);
        eRecyclerView.setLayoutManager(eLayoutManager);

        EchatList = new ArrayList<>();

        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override

            public Unit invoke(User user, Throwable throwable) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //myRef = database.getReference();
                String nick = user.getKakaoAccount().getProfile().getNickname();
                myRef = database.getReference("Message").child("Ediya").child(nick);


                E_Button_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = EdiyaText_chat.getText().toString(); //msg

                        if (msg != null) {
                            ChatDataE chat = new ChatDataE();
                            chat.setName(nick);
                            chat.setMsg(msg);
                            myRef.setValue(chat);
                        }

                    }  });


                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                        ChatDataE chat = dataSnapshot.getValue(ChatDataE.class);
                        ((EdiyaAdapter) eAdapter).addChat(chat);
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
        eAdapter = new EdiyaAdapter(EchatList, EdiyaRequest.this);
        eRecyclerView.setAdapter(eAdapter);


    }
    }
