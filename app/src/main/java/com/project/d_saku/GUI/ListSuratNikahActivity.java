package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.d_saku.Adapter.LaporanSuratNikahAdapter;
import com.project.d_saku.Adapter.PemberitahuanSuratAdapter;
import com.project.d_saku.Class.SuratPengantarNikah;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.util.ArrayList;

public class ListSuratNikahActivity extends AppCompatActivity implements LaporanSuratNikahAdapter.dataListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<SuratPengantarNikah> listSuratNikahs;
    private UserDetail currentUser;
    private String namaJabatan;

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suratnikah_listlaporan);

        Intent fromLaporan = getIntent();
        String[] messages = fromLaporan.getStringArrayExtra(LaporanActivity.EXTRA_MESSAGE);

        if (messages != null){
            usernameMessage = messages[0];
            passwordMessage = messages[1];
            idJabatanMessage = messages[2];
            nikMessage = messages[3];
        }

        switch (idJabatanMessage){
            case "1" :
                namaJabatan = "Lurah";
                break;
            case "2" :
                namaJabatan = "Ketua RW";
                break;
            case "3" :
                namaJabatan = "Ketua RT";
                break;
            case "4" :
                namaJabatan = "Warga";
                break;
            case "5" :
                namaJabatan = "Admin";
                break;
            default :
                namaJabatan = "";
                break;
        }

        recyclerView = findViewById(R.id.listLaporanSurat);

        MyRecyclerView();
        GetData();

    }

    private void GetData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("userDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = new UserDetail();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDetail userDetail = snapshot.getValue(UserDetail.class);
                    if (userDetail.getNik().equals(nikMessage)){
                        currentUser = userDetail;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
            }
        });
        reference.child("suratPengantarNikah").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSuratNikahs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SuratPengantarNikah suratNikah = snapshot.getValue(SuratPengantarNikah.class);
                    if(namaJabatan.equals("Warga")){
                        if (nikMessage.equals(suratNikah.getNoKTP())){
                            listSuratNikahs.add(suratNikah);
                        }
                    }
                    else{
                        if (currentUser.getNamaKelurahan().equals(suratNikah.getNamaKelurahan())
                                && currentUser.getNomorRW().equals(suratNikah.getNomorRW()) && currentUser.getNomorRW().equals(suratNikah.getNomorRT())){
                            listSuratNikahs.add(suratNikah);
                        }
                    }

                }
                adapter = new LaporanSuratNikahAdapter(listSuratNikahs, ListSuratNikahActivity.this, usernameMessage, passwordMessage, idJabatanMessage, nikMessage);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void MyRecyclerView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onDeleteData(SuratPengantarNikah data, int position) {
        if(reference != null){
            reference.child("suratPengantarNikah").child(data.getNomorSurat()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    Toast.makeText(ListSuratNikahActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    public void gotoAddPenduduk(View view) {
        Intent gotoAddPenduduk = new Intent(this, AddPendudukActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoAddPenduduk.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoAddPenduduk);
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}