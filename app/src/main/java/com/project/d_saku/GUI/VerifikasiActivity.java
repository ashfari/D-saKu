package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.d_saku.R;

public class VerifikasiActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String username, password, idJabatan, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent fromHome = getIntent();
        String[] messages = fromHome.getStringArrayExtra(HomeActivity.EXTRA_MESSAGE);

        if (messages != null){
            username = messages[0];
            password = messages[1];
            idJabatan = messages[2];
            nikMessage = messages[3];
        }

        if (idJabatan.equals("5")){
            setContentView(R.layout.verifikasiadmin_main);
        }
        else {
            setContentView(R.layout.verifikasi_main);
        }
    }

    public void gotoKirimRequest(View view) {
        Intent gotoVerifikasiKirimRequest = new Intent(this, VerifikasiKirimRequestActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoVerifikasiKirimRequest.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoVerifikasiKirimRequest);
    }

    public void gotoNotifikasi(View view) {
        Intent gotoVerifikasiNotifikasi = new Intent(this, VerifikasiNotifikasiActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoVerifikasiNotifikasi.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoVerifikasiNotifikasi);
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }

    public void gotoListUser(View view) {
        Intent gotoListUser = new Intent(this, VerifikasiListUserActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoListUser.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoListUser);
    }

    public void gotoDataUser(View view) {
        Intent gotoDataUser = new Intent(this, VerifikasiDataUserActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoDataUser.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoDataUser);
    }
}
