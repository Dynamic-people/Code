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

public class TwoRequest extends AppCompatActivity {
    private RecyclerView tRecyclerView;
    public  RecyclerView.Adapter tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;
    private List<ChatDataT> TchatList;
    private User user;
    //private String nick; // ID 전달 받아서 넣기

    private EditText T_Text_chat;
    private Button T_Button_send;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_request);
        T_Button_send = findViewById(R.id.T_Button_send);
        T_Text_chat = findViewById(R.id.T_Text_chat);


        tRecyclerView = findViewById(R.id.Two_recycler_view);
        tRecyclerView.setHasFixedSize(true);
        tLayoutManager = new LinearLayoutManager(this);
        tRecyclerView.setLayoutManager(tLayoutManager);

        TchatList = new ArrayList<>();

        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override

            public Unit invoke(User user, Throwable throwable) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //myRef = database.getReference();
                String nick = user.getKakaoAccount().getProfile().getNickname();
                myRef = database.getReference("Message").child("Twosomeplaces").child(nick);


                T_Button_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = T_Text_chat.getText().toString(); //msg

                        if (msg != null) {
                            ChatDataT chat = new ChatDataT();
                            chat.setName(nick);
                            chat.setMsg(msg);
                            myRef.setValue(chat);
                        }

                    }  });


                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                        ChatDataT chat = dataSnapshot.getValue(ChatDataT.class);
                        ((TwoAdapter) tAdapter).addChat(chat);
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
        tAdapter = new TwoAdapter(TchatList, TwoRequest.this);
        tRecyclerView.setAdapter(tAdapter);



    }
}