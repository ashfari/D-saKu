package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.d_saku.R;

public class LaporanActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String username, password, idJabatan, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan);

        Intent fromHome = getIntent();
        String[] messages = fromHome.getStringArrayExtra(HomeActivity.EXTRA_MESSAGE);

        username = messages[0];
        password = messages[1];
        idJabatan = messages[2];
        nikMessage = messages[3];
    }

    public void gotoSuratNikahList(View view) {
        Intent gotoSuratNikahList = new Intent(this, ListSuratNikahActivity.class);
        String[] messages = new String[] {username, password, idJabatan, nikMessage};
        gotoSuratNikahList.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoSuratNikahList);
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }
}
