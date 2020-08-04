package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import static com.example.kafeotomasyon.GirisEkraniActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.kullanici;

public class SettingsActivity extends AppCompatActivity {
    private EditText registerUserName, registerName, registerPassword;
    private FirebaseAuth mAuth;
    private String gorevYeni;
    private DatabaseReference databaseUser;
    private RadioGroup radioGroup;
    private FirebaseUser firebaseUser;
    private int gorevID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Ayarlar");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        registerUserName = (EditText)findViewById(R.id.registerUserName);
        registerName = (EditText)findViewById(R.id.registerName);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        Button buttonCikis = (Button) findViewById(R.id.buttonCikis);
        Button buttonKaydet = (Button) findViewById(R.id.buttonKaydet);
        radioGroup = (RadioGroup) findViewById(R.id.radio);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseUser = database.child("users/"+firebaseUser.getUid());

        registerUserName.setText(firebaseUser.getEmail());
        registerName.setText(kullanici.getIsim());

        String gorev = kullanici.getGorev();
        if (gorev.equals("garson"))
            radioGroup.check(R.id.radioButton);
        else if (gorev.equals("kasiyer"))
            radioGroup.check(R.id.radioButton2);
        else
            radioGroup.check(R.id.radioButton3);
        gorevID = radioGroup.getCheckedRadioButtonId();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton) {
                    gorevYeni = "garson";
                } else if(checkedId == R.id.radioButton2) {
                    gorevYeni = "kasiyer";
                } else {
                    gorevYeni = "yonetici";
                }
            }
        });

        buttonKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!registerName.getText().toString().equals(kullanici.getIsim())){
                    databaseUser.child("isim").setValue(registerName.getText().toString());
                    finish();
                }
                if (!registerUserName.getText().toString().equals(firebaseUser.getEmail())){
                    firebaseUser.updateEmail(registerUserName.getText().toString());
                    cikisyap();
                }
                if (!registerPassword.getText().toString().isEmpty()){
                    firebaseUser.updatePassword(registerPassword.getText().toString());
                    cikisyap();
                }
                if (radioGroup.getCheckedRadioButtonId()!=gorevID){
                    databaseUser.child("gorev").setValue(gorevYeni);
                    finish();
                }
            }
        });

        buttonCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cikisyap();
            }
        });
    }

    private void cikisyap(){
        mAuth.signOut();
        startActivity(new Intent(SettingsActivity.this, GirisEkraniActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
