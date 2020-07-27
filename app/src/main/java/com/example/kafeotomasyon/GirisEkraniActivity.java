package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GirisEkraniActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextUserPassword;
    private Button buttonLogin;
    private TextView txtRegister;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String userName;
    private String userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextUserPassword = (EditText)findViewById(R.id.editTextUserPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        txtRegister = (TextView) findViewById(R.id.txtRegister);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser(); // authenticated user

        if(firebaseUser != null){ // check user session

            Intent i = new Intent(GirisEkraniActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = editTextUserName.getText().toString();
                userPassword = editTextUserPassword.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();
                }else{

                    loginFunc();
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisEkraniActivity.this, KayitEkraniActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginFunc() {

        mAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Intent i = new Intent(GirisEkraniActivity.this, MainActivity.class);
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
