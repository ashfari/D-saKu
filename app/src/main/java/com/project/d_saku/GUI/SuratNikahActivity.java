package com.project.d_saku.GUI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.d_saku.Class.SuratPengantarNikah;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.util.ArrayList;

public class SuratNikahActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String username, password, idJabatan, nikMessage;

    private ArrayList<SuratPengantarNikah> suratNikahs;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surat_nikah_steps);

        Intent fromProsedur = getIntent();
        String[] messages = fromProsedur.getStringArrayExtra(ProsedurActivity.EXTRA_MESSAGE);

        username = messages[0];
        password = messages[1];
        idJabatan = messages[2];
        nikMessage = messages[3];

        if (!idJabatan.equals("4")){
            TextView langkah1 = (TextView) findViewById(R.id.suratNikah1);
            langkah1.setClickable(false);
            langkah1.setTextColor(getResources().getColor(R.color.colorDefault));
            langkah1.setTypeface(langkah1.getTypeface(), Typeface.NORMAL);
        }
        getData();
    }

    public void getData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("suratPengantarNikah").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suratNikahs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SuratPengantarNikah suratNikah = snapshot.getValue(SuratPengantarNikah.class);
                    suratNikahs.add(suratNikah);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void gotoLangkah1(View view) {
        boolean allow = true;
        for (int i = 0; i < suratNikahs.size(); i++){
            if (suratNikahs.get(i).getNoKTP().equals(nikMessage)){
                if (!suratNikahs.get(i).getStatus().equals("Selesai")){
                    allow = false;
                    break;
                }
            }
        }
        if (!allow){
            Toast.makeText(getApplicationContext(),"Maaf, surat sebelumnya masih diproses", Toast.LENGTH_LONG).show();
        }
        else{
            Intent gotoLangkah1 = new Intent(this, SuratNikahLangkah1Activity.class);
            String[] messages = new String[] {username, password, idJabatan, nikMessage};
            gotoLangkah1.putExtra(EXTRA_MESSAGE, messages);
            startActivity(gotoLangkah1);
        }
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}
