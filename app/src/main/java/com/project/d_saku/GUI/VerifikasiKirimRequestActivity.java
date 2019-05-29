package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.d_saku.Class.Verifikasi;
import com.project.d_saku.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class VerifikasiKirimRequestActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private SimpleDateFormat dateFormatter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifikasi_kirim_request);

        Intent fromVerifikasi = getIntent();
        String[] messages = fromVerifikasi.getStringArrayExtra(VerifikasiActivity.EXTRA_MESSAGE);
        Intent fromVerifikasiKirim = getIntent();
        String[] messages2 = fromVerifikasiKirim.getStringArrayExtra(VerifikasiKirimRequestActivity.EXTRA_MESSAGE);

        if (messages != null){
            usernameMessage = messages[0];
            passwordMessage = messages[1];
            idJabatanMessage = messages[2];
            nikMessage = messages[3];
        }

        else if(messages2 != null){
            usernameMessage = messages2[0];
            passwordMessage = messages2[1];
            idJabatanMessage = messages2[2];
            nikMessage = messages2[3];
        }

        spinnerSet();
    }

    private void spinnerSet(){
        Spinner namaKelurahan = findViewById(R.id.verifikasiNamaKelurahanDropdown);
        String[] namaKelurahanList = new String[]{"Nama Kelurahan", "Candirenggo", "Losari", "Pagentan"};
        ArrayAdapter<String> namaKelurahanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namaKelurahanList);
        namaKelurahan.setAdapter(namaKelurahanItems);

        Spinner nomorRW = findViewById(R.id.verifikasiNomorRWDropdown);
        String[] nomorRWList = new String[]{"Nomor RW", "01", "02", "03", "04"};
        ArrayAdapter<String> nomorRWItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomorRWList);
        nomorRW.setAdapter(nomorRWItems);

        Spinner nomorRT = findViewById(R.id.verifikasiNomorRTDropdown);
        String[] nomorRTList = new String[]{"Nomor RT", "01", "02", "03", "04"};
        ArrayAdapter<String> nomorRTItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomorRTList);
        nomorRT.setAdapter(nomorRTItems);
    }

    public void kirimRequest(View view) {
        Spinner namaKelurahan = (Spinner) findViewById(R.id.verifikasiNamaKelurahanDropdown);
        Spinner nomorRW = (Spinner) findViewById(R.id.verifikasiNomorRWDropdown);
        Spinner nomorRT = (Spinner) findViewById(R.id.verifikasiNomorRTDropdown);
        String keVerifikasi = namaKelurahan.getSelectedItem().toString() + "-" + nomorRW.getSelectedItem().toString() +
                "-" + nomorRT.getSelectedItem().toString();
        String dariNik = nikMessage;

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date currentDate = Calendar.getInstance().getTime();
        String formattedCurrentDate = dateFormatter.format(currentDate);

        String idVerifikasi = usernameMessage + "-" + currentDate.toString();
        idVerifikasi = idVerifikasi.replaceAll("\\s","");
        idVerifikasi = idVerifikasi.replaceAll(":","");
        idVerifikasi = idVerifikasi.replaceAll("\\+","");

        reference = FirebaseDatabase.getInstance().getReference();
        Verifikasi verifikasiData = new Verifikasi(usernameMessage, idVerifikasi, keVerifikasi,
                "Terkirim", formattedCurrentDate, "", dariNik);
        reference.child("verifikasi").child(usernameMessage)
                .setValue(verifikasiData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerifikasiKirimRequestActivity.this, "Request Verifikasi Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        Intent gotoVerifikasi = new Intent(this, VerifikasiActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoVerifikasi.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoVerifikasi);
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}
