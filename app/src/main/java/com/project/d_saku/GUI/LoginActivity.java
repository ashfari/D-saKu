package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.d_saku.Class.RegisteredUser;
import com.project.d_saku.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private ArrayList<RegisteredUser> registeredUsers;
    private String nikMessage = "";
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getData();
    }

    private void getData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("registeredUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                registeredUsers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    RegisteredUser registered = snapshot.getValue(RegisteredUser.class);
                    registeredUsers.add(registered);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void verifikasiUser(View view) {
        EditText username = (EditText) findViewById(R.id.login_username);
        EditText password = (EditText) findViewById(R.id.login_password);
        String passwordEncrypted = "sha-1" + password.getText();
        String idJabatan = "null";
        Intent gotoHome = new Intent(this, HomeActivity.class);
        boolean allowUsername = false;
        boolean allowPassword = false;
        for (int i = 0; i < registeredUsers.size(); i++){
            if (username.getText().toString().equals(registeredUsers.get(i).getUsername())){
                allowUsername = true;
                if (passwordEncrypted.equals(registeredUsers.get(i).getPassword())){
                    idJabatan = registeredUsers.get(i).getIdJabatan();
                    nikMessage = registeredUsers.get(i).getNik();
                    allowPassword = true;
                    break;
                }
            }
        }
        if (allowUsername && allowPassword){
            String[] messages = new String[] {username.getText().toString(), passwordEncrypted, idJabatan, nikMessage};
            gotoHome.putExtra(EXTRA_MESSAGE, messages);
            Toast.makeText(getApplicationContext(),
                    "Selamat datang " + username.getText(), Toast.LENGTH_LONG).show();
            startActivity(gotoHome);
        }
        else if (!allowUsername){
            username.setError("Nama user tidak ditemukan!");
        }
        else if (!allowPassword){
            password.setError("Password salah!");
        }
    }

    public void gotoRegister(View view) {
        Intent gotoRegister = new Intent(this, RegisterActivity.class);
        startActivity(gotoRegister);
    }
    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }
}
