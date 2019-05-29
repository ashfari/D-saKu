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
import com.project.d_saku.Adapter.VerifikasiNotifikasiAdapter;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.Class.Verifikasi;
import com.project.d_saku.R;

import java.util.ArrayList;

public class VerifikasiNotifikasiActivity extends AppCompatActivity implements VerifikasiNotifikasiAdapter.dataListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<Verifikasi> verifikasis;
    private ArrayList<UserDetail> userDetails;
    private UserDetail currentUser;
    private String namaJabatan;

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rtrw_pemberitahuansurat);

        try {
            usernameMessage = getIntent().getExtras().getString("dataUsernameMessageUser");
            passwordMessage = getIntent().getExtras().getString("dataPasswordMessageUser");
            idJabatanMessage = getIntent().getExtras().getString("dataIdJabatanMessageUser");
            nikMessage = getIntent().getExtras().getString("dataNikMessageUser");
        }
        catch (Exception e){

        }

        Intent fromVerifikasi = getIntent();
        String[] messages = fromVerifikasi.getStringArrayExtra(VerifikasiActivity.EXTRA_MESSAGE);

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

        recyclerView = findViewById(R.id.dataPemberitahuanList);

        MyRecyclerView();
        GetData();

    }

    private void GetData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("userDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDetails = new ArrayList<>();
                currentUser = new UserDetail();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDetail userDetail = snapshot.getValue(UserDetail.class);
                    userDetails.add(userDetail);
                    if (userDetail.getUsername().equals(usernameMessage)){
                        currentUser = userDetail;
                    }
                }
                UserDetail emptyUser = new UserDetail("-", "-", "-", "-","-", "-", "-", "-","-", "-", "-", "-","-", "-", "-");
                userDetails.add(emptyUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
            }
        });
        reference.child("verifikasi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                verifikasis = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Verifikasi verifikasi = snapshot.getValue(Verifikasi.class);
                    if (!verifikasi.getStatus().equals("Diterima")){
                        verifikasis.add(verifikasi);
                    }
                }
                adapter = new VerifikasiNotifikasiAdapter(verifikasis, VerifikasiNotifikasiActivity.this, usernameMessage, passwordMessage, idJabatanMessage, nikMessage, userDetails);
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
    public void onDeleteData(Verifikasi data, int position) {
        if(reference != null){
            reference.child("verifikasi").child(data.getDariVerifikasi()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    Toast.makeText(VerifikasiNotifikasiActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}