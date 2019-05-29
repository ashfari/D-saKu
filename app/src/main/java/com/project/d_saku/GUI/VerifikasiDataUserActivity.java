package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.d_saku.Class.RegisteredUser;
import com.project.d_saku.Class.SuratPengantarNikah;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class VerifikasiDataUserActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private SimpleDateFormat dateFormatter;

    private RegisteredUser dataUser;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifikasi_datauser);

        Intent fromVerifikasi = getIntent();
        String[] messages = fromVerifikasi.getStringArrayExtra(VerifikasiActivity.EXTRA_MESSAGE);

        usernameMessage = messages[0];
        passwordMessage = messages[1];
        idJabatanMessage = messages[2];
        nikMessage = messages[3];

        dataUser = new RegisteredUser();

        GetData();
    }

    private void GetData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("registeredUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    dataUser = snapshot.getValue(RegisteredUser.class);
                    if (dataUser.getUsername().equals(usernameMessage)){
                        updateForm();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateForm(){
        EditText username = (EditText) findViewById(R.id.usernameData);
        username.setText(dataUser.getUsername());

        EditText password = (EditText) findViewById(R.id.passwordData);
        String[] splitPass = dataUser.getPassword().split("");
        String decryptedPass = "";
        for (int i = 6; i < splitPass.length; i++){
            decryptedPass = decryptedPass + splitPass[i];
        }
        password.setText(decryptedPass);

        EditText nik = (EditText) findViewById(R.id.nikData);
        nik.setText(dataUser.getNik());
    }

    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}
