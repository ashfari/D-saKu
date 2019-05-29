package com.project.d_saku.GUI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.d_saku.Class.Jadwal;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class JadwalBuatActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tanggalJadwal;
    private Button datePicker;

    UserDetail userDetail;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_buatjadwal);

        Intent fromJadwal = getIntent();
        String[] messages = fromJadwal.getStringArrayExtra(JadwalActivity.EXTRA_MESSAGE);

        usernameMessage = messages[0];
        passwordMessage = messages[1];
        idJabatanMessage = messages[2];
        nikMessage = messages[3];

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        tanggalJadwal = (EditText) findViewById(R.id.tanggalJadwal);
        datePicker = (Button) findViewById(R.id.datePicker_button);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        updateForm();

        userDetail = new UserDetail();

        GetData();
    }

    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggalJadwal.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void GetData(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("userDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDetail userDetailTemp = snapshot.getValue(UserDetail.class);
                    if (userDetailTemp.getUsername().equals(usernameMessage)){
                        userDetail = userDetailTemp;
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
        Spinner keJadwal = findViewById(R.id.keJabatan);
        String[] keJadwalList = new String[]{"Dikirim ke", "Ketua RT", "Ketua RW", "Lurah"};
        ArrayAdapter<String> keJadwalItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, keJadwalList);
        keJadwal.setAdapter(keJadwalItems);
        for (int i = 1; i < keJadwalList.length; i++){
            if (keJadwalList[i].equals("Indonesia")){
                keJadwal.setSelection(i);
                break;
            }
        }
    }

    public void kirimJadwal(View view) {
        Spinner keJabatan = (Spinner) findViewById(R.id.keJabatan);
        EditText tanggalJadwal = (EditText) findViewById(R.id.tanggalJadwal);
        EditText kepentinganJadwal = (EditText) findViewById(R.id.kepentinganJadwal);
        TimePicker waktuJadwal = (TimePicker) findViewById(R.id.waktuJadwal);

        String waktuJadwalText = "Tanggal " + tanggalJadwal.getText().toString() + " Pukul " + waktuJadwal.getCurrentHour() + ":" + waktuJadwal.getCurrentMinute() + " Waktu Setempat";

        reference = FirebaseDatabase.getInstance().getReference();
        Jadwal buatJadwal = new Jadwal(usernameMessage + keJabatan.getSelectedItem().toString(), usernameMessage, kepentinganJadwal.getText().toString(), waktuJadwalText, keJabatan.getSelectedItem().toString(), "Terkirim");
        reference.child("jadwal").child(usernameMessage + keJabatan.getSelectedItem().toString())
                .setValue(buatJadwal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Toast.makeText(JadwalBuatActivity.this, "Jadwal Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        Intent gotoJadwal = new Intent(this, JadwalActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoJadwal.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoJadwal);
    }

    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}
