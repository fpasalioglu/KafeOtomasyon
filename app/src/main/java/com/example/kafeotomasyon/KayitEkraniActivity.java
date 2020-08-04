package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import static com.example.kafeotomasyon.GirisEkraniActivity.database;

public class KayitEkraniActivity extends AppCompatActivity {
    private EditText registerUserName, registerName, registerPassword;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private String userName, name, gorev;
    private String userPassword;
    private DatabaseReference databaseUser;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        registerUserName = (EditText)findViewById(R.id.registerUserName);
        registerName = (EditText)findViewById(R.id.registerName);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        radioGroup = (RadioGroup) findViewById(R.id.radio);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton) {
                    gorev = "garson";
                } else if(checkedId == R.id.radioButton2) {
                    gorev = "kasiyer";
                } else {
                    gorev = "yonetici";
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        databaseUser = database.child("users");

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = registerUserName.getText().toString();
                userPassword = registerPassword.getText().toString();
                name = registerName.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty() || name.isEmpty() || radioGroup.getCheckedRadioButtonId() == -1){
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
                        String userID = mAuth.getCurrentUser().getUid();
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uid", userID);
                        result.put("isim", name);
                        result.put("gorev", gorev);
                        databaseUser.child(userID).setValue(result);

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
