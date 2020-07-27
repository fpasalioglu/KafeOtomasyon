package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitEkraniActivity extends AppCompatActivity {
    private EditText registerUserName;
    private EditText registerPassword;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private String userName;
    private String userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        registerUserName = (EditText)findViewById(R.id.registerUserName);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = registerUserName.getText().toString();
                userPassword = registerPassword.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();
                }else{
                    registerFunc();
                }

            }
        });
    }

    private void registerFunc() {
        mAuth.createUserWithEmailAndPassword(userName,userPassword)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent i = new Intent(KayitEkraniActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
