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
import com.project.d_saku.Adapter.JadwalListAdapter;
import com.project.d_saku.Adapter.JadwalNotifikasiAdapter;
import com.project.d_saku.Class.Jadwal;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.util.ArrayList;

public class JadwalListActivity extends AppCompatActivity implements JadwalListAdapter.dataListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<Jadwal> jadwals;
    private ArrayList<UserDetail> userDetails;
    private UserDetail currentUser;
    private String namaJabatan;

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_listjadwal);

        try {
            usernameMessage = getIntent().getExtras().getString("dataUsernameMessageUser");
            passwordMessage = getIntent().getExtras().getString("dataPasswordMessageUser");
            idJabatanMessage = getIntent().getExtras().getString("dataIdJabatanMessageUser");
            nikMessage = getIntent().getExtras().getString("dataNikMessageUser");
        }
        catch (Exception e){

        }

        Intent fromJadwal = getIntent();
        String[] messages = fromJadwal.getStringArrayExtra(JadwalActivity.EXTRA_MESSAGE);

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

        recyclerView = findViewById(R.id.listJadwal);

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
        reference.child("jadwal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jadwals = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Jadwal jadwal = snapshot.getValue(Jadwal.class);
                    jadwals.add(jadwal);
                }
                adapter = new JadwalListAdapter(jadwals, JadwalListActivity.this, usernameMessage, passwordMessage, idJabatanMessage, nikMessage, userDetails);
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
    public void onDeleteData(Jadwal data, int position) {
        if(reference != null){
            reference.child("jadwal").child(data.getIdJadwal()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    Toast.makeText(JadwalListActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}