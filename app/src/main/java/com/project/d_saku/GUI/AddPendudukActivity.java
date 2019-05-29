package com.project.d_saku.GUI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddPendudukActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tanggalLahir;
    private Button datePicker;

    private ArrayList<UserDetail> userDetails;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_penduduk);

        Intent fromHome = getIntent();
        String[] messages = fromHome.getStringArrayExtra(HomeActivity.EXTRA_MESSAGE);
        Intent fromHere = getIntent();
        String[] messages2 = fromHere.getStringArrayExtra(AddPendudukActivity.EXTRA_MESSAGE);

        if (messages != null){
            usernameMessage = messages[0];
            passwordMessage = messages[1];
            idJabatanMessage = messages[2];
            nikMessage = messages[3];
        }
        else if (messages2 != null){
            usernameMessage = messages2[0];
            passwordMessage = messages2[1];
            idJabatanMessage = messages2[2];
            nikMessage = messages2[3];
        }

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        tanggalLahir = (EditText) findViewById(R.id.add_tanggalLahir);
        datePicker = (Button) findViewById(R.id.datePicker_button);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        spinnerSet();
        getData();
    }

    private void spinnerSet(){
        Spinner agama = findViewById(R.id.agamaDropdown);
        String[] agamaList = new String[]{"Agama", "Islam", "Kristen", "Hindu", "Budha", "Konghuchu"};
        ArrayAdapter<String> agamaItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, agamaList);
        agama.setAdapter(agamaItems);

        Spinner pendidikan = findViewById(R.id.pendidikanDropdown);
        String[] pendidikanList = new String[]{"Pendidikan", "Sekolah Dasar", "Sekolah Menengah Pertama"
                , "Sekolah Menengah Atas", "Sekolah Menengah Kejuruan", "Perguruan Tinggi"};
        ArrayAdapter<String> pendidikanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pendidikanList);
        pendidikan.setAdapter(pendidikanItems);

        Spinner jenisPekerjaan = findViewById(R.id.jenisPekerjaanDropdown);
        String[] jenisPekerjaanList = new String[]{"Jenis Pekerjaan", "Belum/Tidak Bekerja", "Pelajar/Mahasiswa"
                , "Pensiunan", "Wiraswasta", "Guru", "Dokter", "Karyawan"};
        ArrayAdapter<String> jenisPekerjaanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, jenisPekerjaanList);
        jenisPekerjaan.setAdapter(jenisPekerjaanItems);

        Spinner namaKelurahan = findViewById(R.id.namaKelurahanDropdown);
        String[] namaKelurahanList = new String[]{"Nama Kelurahan", "Candirenggo", "Losari", "Pagentan"};
        ArrayAdapter<String> namaKelurahanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namaKelurahanList);
        namaKelurahan.setAdapter(namaKelurahanItems);

        Spinner nomorRW = findViewById(R.id.nomorRWDropdown);
        String[] nomorRWList = new String[]{"Nomor RW", "01", "02", "03", "04"};
        ArrayAdapter<String> nomorRWItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomorRWList);
        nomorRW.setAdapter(nomorRWItems);

        Spinner nomorRT = findViewById(R.id.nomorRTDropdown);
        String[] nomorRTList = new String[]{"Nomor RT", "01", "02", "03", "04"};
        ArrayAdapter<String> nomorRTItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomorRTList);
        nomorRT.setAdapter(nomorRTItems);

        Spinner jabatan = findViewById(R.id.jabatanDropdown);
        String[] jabatanList = new String[]{"Jabatan", "Admin", "Lurah", "Ketua RW", "Ketua RT", "Warga"};
        ArrayAdapter<String> jabatanItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, jabatanList);
        jabatan.setAdapter(jabatanItems);
    }

    private void getData() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("userDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDetails = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDetail userDetail = snapshot.getValue(UserDetail.class);
                    userDetails.add(userDetail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Data Gagal Dimuat", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(1990,12,30);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggalLahir.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void addPenduduk(View view) {
        EditText nik = (EditText) findViewById(R.id.add_nik);
        boolean allow = true;
        for (int i = 0; i < userDetails.size(); i++){
            if (nik.getText().toString().equals(userDetails.get(i).getNik())){
                allow = false;
                break;
            }
        }

        EditText nomorKK = (EditText) findViewById(R.id.add_nokk);
        EditText namaLengkap = (EditText) findViewById(R.id.add_namalengkap);

        RadioGroup groupJK = (RadioGroup) findViewById(R.id.radioGroupJK);
        int selectedJK = groupJK.getCheckedRadioButtonId();
        RadioButton jenisKelamin = (RadioButton) findViewById(selectedJK);

        EditText tempatLahir = (EditText) findViewById(R.id.add_tempatLahir);
        EditText tanggalLahir = (EditText) findViewById(R.id.add_tanggalLahir);

        Spinner agama = (Spinner) findViewById(R.id.agamaDropdown);
        Spinner pendidikan = (Spinner) findViewById(R.id.pendidikanDropdown);
        Spinner jenisPekerjaan = (Spinner) findViewById(R.id.jenisPekerjaanDropdown);
        Spinner namaKelurahan = (Spinner) findViewById(R.id.namaKelurahanDropdown);
        Spinner nomorRW = (Spinner) findViewById(R.id.nomorRWDropdown);
        Spinner nomorRT = (Spinner) findViewById(R.id.nomorRTDropdown);
        Spinner jabatan = (Spinner) findViewById(R.id.jabatanDropdown);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date currentDate = Calendar.getInstance().getTime();
        String formattedCurrentDate = dateFormatter.format(currentDate);

        reference = FirebaseDatabase.getInstance().getReference();
        UserDetail addUserDetail = new UserDetail("", nik.getText().toString(), agama.getSelectedItem().toString(),
                jabatan.getSelectedItem().toString(), jenisKelamin.getText().toString(),
                jenisPekerjaan.getSelectedItem().toString(), namaKelurahan.getSelectedItem().toString(),
                namaLengkap.getText().toString(), nomorKK.getText().toString(), nomorRT.getSelectedItem().toString(),
                nomorRW.getSelectedItem().toString(), pendidikan.getSelectedItem().toString(),
                formattedCurrentDate, tanggalLahir.getText().toString(), tempatLahir.getText().toString());
        //tambah penduduk
        if (allow){
            reference.child("userDetail").child(nik.getText().toString())
                    .setValue(addUserDetail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddPendudukActivity.this, "Penduduk Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
            Intent gotoDataPenduduk = new Intent(this, DataPendudukActivity.class);
            String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
            gotoDataPenduduk.putExtra(EXTRA_MESSAGE, messages);
            startActivity(gotoDataPenduduk);
        }
        else {
            Toast.makeText(AddPendudukActivity.this, "Data dengan NIK yang sama tidak diperbolehkan", Toast.LENGTH_SHORT).show();
        }
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}
