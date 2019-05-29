package com.project.d_saku.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.d_saku.R;

public class HomeActivity extends LoginActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent fromLogin = getIntent();
        String[] messages = fromLogin.getStringArrayExtra(LoginActivity.EXTRA_MESSAGE);
        Intent fromSuratNikah = getIntent();
        String[] messages2 = fromSuratNikah.getStringArrayExtra(SuratNikahLangkah1Activity.EXTRA_MESSAGE);

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

        if (messages[2].equals("5")){
            setContentView(R.layout.home_admin_main);
        }
        else if (messages[2].equals("4")){
            setContentView(R.layout.home_warga_main);
        }
        else if (messages[2].equals("3") || messages[2].equals("2")){
            setContentView(R.layout.home_rtrw_main);
        }
        else if (messages[2].equals("1")){
            setContentView(R.layout.home_lurah_main);
        }
        else{
            setContentView(R.layout.home_unverified_main);;
        }
        System.out.println("nikMessage" + nikMessage);
    }

    public void gotoVerifikasiMain(View view) {
        Intent gotoVerifikasi = new Intent(this, VerifikasiActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoVerifikasi.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoVerifikasi);
    }

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }

    public void lihatDataPenduduk(View view) {
        Intent gotoDataUser = new Intent(this, DataPendudukActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoDataUser.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoDataUser);
    }

    public void gotoProsedur(View view) {
        Intent gotoProsedur = new Intent(this, ProsedurActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoProsedur.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoProsedur);
    }

    public void gotoPemberitahuan(View view) {
        Intent gotoPemberitahuan = new Intent(this, PemberitahuanActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoPemberitahuan.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoPemberitahuan);
    }

    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }

    public void gotoJadwal(View view) {
        Intent gotoJadwal = new Intent(this, JadwalActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoJadwal.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoJadwal);
    }

    public void gotoListJadwal(View view) {
        Intent gotoListJadwal = new Intent(this, JadwalListActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoListJadwal.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoListJadwal);
    }

    public void gotoLaporan(View view) {
        Intent gotoLaporan = new Intent(this, LaporanActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoLaporan.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoLaporan);
    }

    public void gotoCetakSurat(View view) {
        Intent gotoCetakSurat = new Intent(this, CetakSuratActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoCetakSurat.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoCetakSurat);
    }
}
