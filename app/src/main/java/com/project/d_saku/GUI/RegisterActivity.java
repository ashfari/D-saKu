package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.d_saku.Class.RegisteredUser;
import com.project.d_saku.R;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private ArrayList<RegisteredUser> registeredUsers;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
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

    public void daftarUser(View view) {
        EditText username = (EditText) findViewById(R.id.register_username);
        EditText password = (EditText) findViewById(R.id.register_password);
        EditText nik = (EditText) findViewById(R.id.register_nik);

        String passwordEncrypted = "sha-1" + password.getText();

        boolean allowUsername = true;
        for (int i = 0; i < registeredUsers.size(); i++){
            if (username.getText().toString().equals(registeredUsers.get(i).getUsername())){
                allowUsername = false;
            }
        }
        if (username.getText().toString().equals("")){
            username.setError("Nama user tidak boleh kosong!");
        }
        else if (password.getText().toString().equals("")){
            password.setError("Password tidak boleh kosong!");
        }
        else if (nik.getText().toString().equals("")){
            nik.setError("NIK tidak boleh kosong!");
        }
        //registrasi user jika data sama pada data base yang di buat oleh admin.
        else if (allowUsername){
            reference = FirebaseDatabase.getInstance().getReference();
            String getUsername = username.getText().toString();
            RegisteredUser user = new RegisteredUser(getUsername, passwordEncrypted, nik.getText().toString(), "");
            reference.child("registeredUser").child(getUsername)
                    .setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

            Intent gotoLogin = new Intent(this, LoginActivity.class);
            startActivity(gotoLogin);
        }
        else {
            username.setError("Nama user sudah ada!");
        }
    }

    public void gotoLogin(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }
}
