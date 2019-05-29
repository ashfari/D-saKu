package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.project.d_saku.Adapter.UserDetailAdapter;
import com.project.d_saku.Class.SuratPengantarNikah;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SuratNikahLangkah1Activity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private SimpleDateFormat dateFormatter;

    private UserDetail userDetail;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suratnikah_suratketeranganrtrw);

        Intent fromSuratNikah = getIntent();
        String[] messages = fromSuratNikah.getStringArrayExtra(ProsedurActivity.EXTRA_MESSAGE);

        usernameMessage = messages[0];
        passwordMessage = messages[1];
        idJabatanMessage = messages[2];
        nikMessage = messages[3];

        userDetail = new UserDetail();

        GetData();
    }

    private void GetData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("userDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    userDetail = snapshot.getValue(UserDetail.class);
                    if (userDetail.getUsername().equals(usernameMessage)){
                        updateForm();
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
        EditText namaLengkap = (EditText) findViewById(R.id.add_namalengkap);
        namaLengkap.setText(userDetail.getNamaLengkap());

        EditText tempatLahir = (EditText) findViewById(R.id.add_tempatLahir);
        tempatLahir.setText(userDetail.getTempatLahir());

        EditText tanggalLahir = (EditText) findViewById(R.id.add_tanggalLahir);
        tanggalLahir.setText(userDetail.getTanggalLahir());

        EditText nomorKTP = (EditText) findViewById(R.id.add_nik);
        nomorKTP.setText(userDetail.getNik());

        RadioButton jkLaki = (RadioButton) findViewById(R.id.radioButtonLakiLaki);
        RadioButton jkPerempuan = (RadioButton) findViewById(R.id.radioButtonPerempuan);
        if (jkLaki.getText().equals(userDetail.getJenisKelamin())){
            jkLaki.setChecked(true);
        }
        else {
            jkPerempuan.setChecked(true);
        }

        Spinner kewarganegaraan = findViewById(R.id.kewarganegaraan);
        String[] kewarganegaraanList = new String[]{"Kewarganegaraan", "Brunei", "Indonesia", "Jepang", "Korea", "Malaysia", "Singapura"};
        ArrayAdapter<String> kewarganegaraanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kewarganegaraanList);
        kewarganegaraan.setAdapter(kewarganegaraanItems);
        for (int i = 1; i < kewarganegaraanList.length; i++){
            if (kewarganegaraanList[i].equals("Indonesia")){
                kewarganegaraan.setSelection(i);
                break;
            }
        }
        kewarganegaraan.setEnabled(false);

        Spinner agama = findViewById(R.id.agamaDropdown);
        String[] agamaList = new String[]{"Agama", "Islam", "Kristen", "Hindu", "Budha", "Konghuchu"};
        ArrayAdapter<String> agamaItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, agamaList);
        agama.setAdapter(agamaItems);
        for (int i = 1; i < agamaList.length; i++){
            if (agamaList[i].equals(userDetail.getAgama())){
                agama.setSelection(i);
                break;
            }
        }
        agama.setEnabled(false);

        Spinner jenisPekerjaan = findViewById(R.id.jenisPekerjaanDropdown);
        String[] jenisPekerjaanList = new String[]{"Jenis Pekerjaan", "Belum/Tidak Bekerja", "Pelajar/Mahasiswa"
                , "Pensiunan", "Wiraswasta", "Guru", "Dokter", "Karyawan"};
        ArrayAdapter<String> jenisPekerjaanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, jenisPekerjaanList);
        jenisPekerjaan.setAdapter(jenisPekerjaanItems);
        for (int i = 1; i < jenisPekerjaanList.length; i++){
            if (jenisPekerjaanList[i].equals(userDetail.getJenisPekerjaan())){
                jenisPekerjaan.setSelection(i);
                break;
            }
        }
        jenisPekerjaan.setEnabled(false);

        Spinner namaKelurahan = findViewById(R.id.namaKelurahanDropdown);
        String[] namaKelurahanList = new String[]{"Nama Kelurahan", "Candirenggo", "Losari", "Pagentan"};
        ArrayAdapter<String> namaKelurahanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namaKelurahanList);
        namaKelurahan.setAdapter(namaKelurahanItems);
        for (int i = 1; i < namaKelurahanList.length; i++){
            if (namaKelurahanList[i].equals(userDetail.getNamaKelurahan())){
                namaKelurahan.setSelection(i);
                break;
            }
        }
        namaKelurahan.setEnabled(false);

        Spinner nomorRW = findViewById(R.id.nomorRWDropdown);
        String[] nomorRWList = new String[]{"Nomor RW", "01", "02", "03", "04"};
        ArrayAdapter<String> nomorRWItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomorRWList);
        nomorRW.setAdapter(nomorRWItems);
        for (int i = 1; i < nomorRWList.length; i++){
            if (nomorRWList[i].equals(userDetail.getNomorRW())){
                nomorRW.setSelection(i);
                break;
            }
        }
        nomorRW.setEnabled(false);

        Spinner nomorRT = findViewById(R.id.nomorRTDropdown);
        String[] nomorRTList = new String[]{"Nomor RT", "01", "02", "03", "04"};
        ArrayAdapter<String> nomorRTItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomorRTList);
        nomorRT.setAdapter(nomorRTItems);
        for (int i = 1; i < nomorRTList.length; i++){
            if (nomorRTList[i].equals(userDetail.getNomorRT())){
                nomorRT.setSelection(i);
                break;
            }
        }
        nomorRT.setEnabled(false);
    }

    public void kirimKeRT(View view) {
        EditText namaLengkap = (EditText) findViewById(R.id.add_namalengkap);
        EditText nomorKTP = (EditText) findViewById(R.id.add_nik);

        RadioGroup groupJK = (RadioGroup) findViewById(R.id.radioGroupJK);
        int selectedJK = groupJK.getCheckedRadioButtonId();
        RadioButton jenisKelamin = (RadioButton) findViewById(selectedJK);

        EditText tempatLahir = (EditText) findViewById(R.id.add_tempatLahir);
        EditText tanggalLahir = (EditText) findViewById(R.id.add_tanggalLahir);
        String ttl = tempatLahir.getText().toString() + ", " + tanggalLahir.getText().toString();

        EditText alamat = (EditText) findViewById(R.id.add_alamat);
        EditText namaCalon = (EditText) findViewById(R.id.add_namacalon);

        Spinner agama = (Spinner) findViewById(R.id.agamaDropdown);
        Spinner jenisPekerjaan = (Spinner) findViewById(R.id.jenisPekerjaanDropdown);
        Spinner kewarganegaraan = (Spinner) findViewById(R.id.kewarganegaraan);
        Spinner namaKelurahan = (Spinner) findViewById(R.id.namaKelurahanDropdown);
        Spinner nomorRW = (Spinner) findViewById(R.id.nomorRWDropdown);
        Spinner nomorRT = (Spinner) findViewById(R.id.nomorRTDropdown);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date currentDate = Calendar.getInstance().getTime();
        String formattedCurrentDate = dateFormatter.format(currentDate);

        String nomorSurat = usernameMessage + "-" + currentDate;
        nomorSurat = nomorSurat.replaceAll("\\s", "");
        nomorSurat = nomorSurat.replaceAll(":", "");
        nomorSurat = nomorSurat.replaceAll("\\+", "");

        reference = FirebaseDatabase.getInstance().getReference();
        SuratPengantarNikah suratPengantarNikah = new SuratPengantarNikah(nomorSurat, namaLengkap.getText().toString(),
                ttl, jenisKelamin.getText().toString(), jenisPekerjaan.getSelectedItem().toString(),
                nomorKTP.getText().toString(), kewarganegaraan.getSelectedItem().toString(),
                agama.getSelectedItem().toString(), alamat.getText().toString(), nomorRT.getSelectedItem().toString(),
                nomorRW.getSelectedItem().toString(), namaKelurahan.getSelectedItem().toString(),
                "Singosari", "Kabupaten Malang", namaCalon.getText().toString(),
                formattedCurrentDate, "Ketua RT");
        reference.child("suratPengantarNikah").child(nomorSurat)
                .setValue(suratPengantarNikah)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Toast.makeText(SuratNikahLangkah1Activity.this, "Surat Pengantar Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        Intent gotoHome = new Intent(this, HomeActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoHome.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoHome);
    }

    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}
