package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.project.d_saku.R;

public class JadwalActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String username, password, idJabatan, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_main);

        Intent fromHome = getIntent();
        String[] messages = fromHome.getStringArrayExtra(HomeActivity.EXTRA_MESSAGE);

        username = messages[0];
        password = messages[1];
        idJabatan = messages[2];
        nikMessage = messages[3];

        Button buatJadwalButton = (Button) findViewById(R.id.buatJadwalButton);
        if (idJabatan.equals("1")){
            buatJadwalButton.setVisibility(View.GONE);
        }
    }

    public void gotoSuratNikah(View view) {
        Intent gotoSuratNikah = new Intent(this, SuratNikahActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoSuratNikah.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoSuratNikah);
    }

    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }

    public void gotoBuatJadwal(View view) {
        Intent gotoBuatJadwal = new Intent(this, JadwalBuatActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoBuatJadwal.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoBuatJadwal);
    }

    public void gotoNotifikasiJadwal(View view) {
        Intent gotoNotifikasiJadwal = new Intent(this, JadwalNotifikasiActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoNotifikasiJadwal.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoNotifikasiJadwal);
    }
}
