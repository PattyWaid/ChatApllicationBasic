package com.example.prathamesh.splashscreen;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChatActivity extends AppCompatActivity {

    private EditText editText;
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerMessages;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentuser;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editText = (EditText)findViewById(R.id.message_type);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
        mRecyclerMessages = (RecyclerView) findViewById(R.id.list_messages);
        mRecyclerMessages.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        mRecyclerMessages.setLayoutManager(lm);


        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(ChatActivity.this, Register.class));
                }
            }
        };
        
    }

    public void sendClick(View view) {
        mCurrentuser = firebaseAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentuser.getUid());
        final String msgValue = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(msgValue)) {
            final DatabaseReference newPost = mDatabase.push();
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newPost.child("content").setValue(msgValue);
                    newPost.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mRecyclerMessages.scrollToPosition(mRecyclerMessages.getAdapter().getItemCount());

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Messages,MessageViewHolder> FR = new FirebaseRecyclerAdapter<Messages, MessageViewHolder>(
                Messages.class,
                R.layout.list_item,
                MessageViewHolder.class,
                mDatabase
        ) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Messages model, int position) {
                    viewHolder.setContent(model.getContent());
                    viewHolder.setUsername(model.getUsername());
            }


        };

        mRecyclerMessages.setAdapter(FR);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        View mV;
        public MessageViewHolder(View itemView) {
            super(itemView);
            mV = itemView;

        }

        public void setContent(String content){
            TextView tv_content = (TextView)mV.findViewById(R.id.message_text);
            tv_content.setText(content);
        }

        public void setUsername(String username){
            TextView tv_username = (TextView)mV.findViewById(R.id.message_user);
            tv_username.setText(username);
        }
    }
}