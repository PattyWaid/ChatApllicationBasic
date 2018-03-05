package com.example.prathamesh.splashscreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText email, name, set_password;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.email_id);
        name = (EditText) findViewById(R.id.name);
        set_password = (EditText) findViewById(R.id.set_pass);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    public void signUpClicked(View view){
        final String name_content, email_content, password_content;
        name_content = name.getText().toString().trim();
        email_content = email.getText().toString().trim();
        password_content = set_password.getText().toString().trim();
        if(!TextUtils.isEmpty(name_content) && !TextUtils.isEmpty(email_content) && !TextUtils.isEmpty(password_content)){
            firebaseAuth.createUserWithEmailAndPassword(email_content,password_content).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String uid = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference current_db_user = mDatabase.child(uid);
                        current_db_user.child("Name").setValue(name_content);
                        startActivity(new Intent(Register.this, Login.class));
                        Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void logInClicked(View view){
        startActivity(new Intent(Register.this, Login.class));
    }
}
