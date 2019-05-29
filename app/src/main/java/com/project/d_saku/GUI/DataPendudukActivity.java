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
import com.project.d_saku.Adapter.UserDetailAdapter;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.util.ArrayList;

public class DataPendudukActivity extends AppCompatActivity implements UserDetailAdapter.dataListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<UserDetail> userDetails;

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_datauser);

        Intent fromHome = getIntent();
        String[] messages = fromHome.getStringArrayExtra(HomeActivity.EXTRA_MESSAGE);

        usernameMessage = messages[0];
        passwordMessage = messages[1];
        idJabatanMessage = messages[2];
        nikMessage = messages[3];

        recyclerView = findViewById(R.id.dataUserList);

        MyRecyclerView();
        GetData();
    }

    private void GetData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("userDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDetails = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDetail user = snapshot.getValue(UserDetail.class);
                    userDetails.add(user);
                }
                adapter = new UserDetailAdapter(userDetails, DataPendudukActivity.this, usernameMessage, passwordMessage, idJabatanMessage, nikMessage);
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
    public void onDeleteData(UserDetail data, int position) {
        if(reference != null){
            reference.child("userDetail").child(data.getNik()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    Toast.makeText(DataPendudukActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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