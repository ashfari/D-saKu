package com.project.d_saku.GUI;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.d_saku.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SuratNikahLangkah1VerifyActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.project.d_saku.GUI.extra.MESSAGE";
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private DatabaseReference reference;
    private Bitmap bitmap;
    private String savedFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usernameMessage = getIntent().getExtras().getString("dataUsernameMessageUser");
        passwordMessage = getIntent().getExtras().getString("dataPasswordMessageUser");
        idJabatanMessage = getIntent().getExtras().getString("dataIdJabatanMessageUser");
        nikMessage = getIntent().getExtras().getString("dataNikMessageUser");

        if (idJabatanMessage.equals("3")){
            setContentView(R.layout.suratnikah_suratketeranganrtrw_verifyrtrw);
            Button kirimKeLurah = (Button) findViewById(R.id.kirimKelurahanButton);
            kirimKeLurah.setVisibility(View.GONE);
            updateSuratRTRW();
        }
        else if (idJabatanMessage.equals("2")){
            setContentView(R.layout.suratnikah_suratketeranganrtrw_verifyrtrw);
            Button kirimKeRW = (Button) findViewById(R.id.kirimRWButton);
            kirimKeRW.setVisibility(View.GONE);
            updateSuratRTRW();
        }
        else if (idJabatanMessage.equals("1")){
            setContentView(R.layout.suratnikah_suratketeranganrtrw_verifylurah);
            Button cetakSurat = (Button) findViewById(R.id.cetakSuratButton);
            cetakSurat.setEnabled(false);
            updateSuratLurah();
        }

        if (idJabatanMessage.equals("2") || idJabatanMessage.equals("3")){
            Button kirimRW = (Button) findViewById(R.id.kirimRWButton);
            kirimRW.setEnabled(false);
            Button kirimLurah = (Button) findViewById(R.id.kirimKelurahanButton);
            kirimLurah.setEnabled(false);
        }

    }

    private void updateSuratRTRW(){
        TextView kecamatan = (TextView) findViewById(R.id.skKecamatan1);
        kecamatan.setText(": " + getIntent().getExtras().getString("dataNamaKecamatan"));
        TextView kelurahan = (TextView) findViewById(R.id.skKelurahan1);
        kelurahan.setText(": " + getIntent().getExtras().getString("dataNamaKelurahan"));
        TextView rtrw = (TextView) findViewById(R.id.skRTRW);
        rtrw.setText("RT." + getIntent().getExtras().getString("dataNomorRT") + " RW." + getIntent().getExtras().getString("dataNomorRW"));
        TextView nomorSurat = (TextView) findViewById(R.id.skNomor);
        nomorSurat.setText("No. : " + getIntent().getExtras().getString("dataNomorSurat"));
        TextView paragraph1 = (TextView) findViewById(R.id.skParagraph1);
        paragraph1.setText("      Yang bertanda tangan di bawah ini, Pengurus Rt." + getIntent().getExtras().getString("dataNomorRT") + " / Rw." + getIntent().getExtras().getString("dataNomorRW") + " Kelurahan "+ getIntent().getExtras().getString("dataNamaKelurahan") + ", Kecamatan "+ getIntent().getExtras().getString("dataNamaKecamatan") + " dengan ini menerangkan bahwa:");
        TextView nama = (TextView) findViewById(R.id.skNama1);
        nama.setText(": " + getIntent().getExtras().getString("dataNama"));
        TextView ttl = (TextView) findViewById(R.id.skTTL1);
        ttl.setText(": " + getIntent().getExtras().getString("dataTTL"));
        TextView jenisKelamin = (TextView) findViewById(R.id.skJenisKelamin1);
        jenisKelamin.setText(": " + getIntent().getExtras().getString("dataJenisKelamin"));
        TextView pekerjaan = (TextView) findViewById(R.id.skPekerjaan1);
        pekerjaan.setText(": " + getIntent().getExtras().getString("dataPekerjaan"));
        TextView noKTP = (TextView) findViewById(R.id.skKK1);
        noKTP.setText(": " + getIntent().getExtras().getString("dataNoKTP"));
        TextView kewarganegaraan = (TextView) findViewById(R.id.skKewarganegaraan1);
        kewarganegaraan.setText(": " + getIntent().getExtras().getString("dataKewarganegaraan"));
        TextView agama = (TextView) findViewById(R.id.skAgama1);
        agama.setText(": " + getIntent().getExtras().getString("dataAgama"));
        TextView alamat = (TextView) findViewById(R.id.skAlamat1);
        alamat.setText(": " + getIntent().getExtras().getString("dataAlamat"));
        TextView alamatRtRw = (TextView) findViewById(R.id.skAlamatRTRW);
        alamatRtRw.setText("    Rt." + getIntent().getExtras().getString("dataNomorRT") + "/Rw." + getIntent().getExtras().getString("dataNomorRW"));
        TextView keperluan = (TextView) findViewById(R.id.skKeperluan1);
        keperluan.setText(": " + getIntent().getExtras().getString("dataKeterangan"));
        TextView tanggalDikirim = (TextView) findViewById(R.id.skCurrentdate);
        tanggalDikirim.setText("Malang, " + getIntent().getExtras().getString("dataTanggalDikirim"));
        TextView mengetahuiRW = (TextView) findViewById(R.id.skMengetahuiRW);
        mengetahuiRW.setText("Mengetahui Ketua Rw." + getIntent().getExtras().getString("dataNomorRW"));
        TextView stempelRW = (TextView) findViewById(R.id.skStempelRW);
        stempelRW.setText("RW." + getIntent().getExtras().getString("dataNomorRW") + " KELURAHAN " + getIntent().getExtras().getString("dataNamaKelurahan"));
        TextView mengetahuiRT = (TextView) findViewById(R.id.skMengetahuiRT);
        mengetahuiRT.setText("Ketua Rt." + getIntent().getExtras().getString("dataNomorRT") + " Rw." + getIntent().getExtras().getString("dataNomorRW"));
        TextView stempelRT = (TextView) findViewById(R.id.skStempelRT);
        stempelRT.setText("RT." + getIntent().getExtras().getString("dataNomorRT") +"/RW." + getIntent().getExtras().getString("dataNomorRW") + " KELURAHAN " + getIntent().getExtras().getString("dataNamaKelurahan"));
        stempelRT.setVisibility(View.INVISIBLE);
        stempelRW.setVisibility(View.INVISIBLE);
        if (getIntent().getExtras().getString("dataStatus").equals("Ketua RW")){
            stempelRT.setVisibility(View.VISIBLE);
        }
    }

    private void updateSuratLurah() {
        TextView kelurahan = (TextView) findViewById(R.id.skKelurahan1);
        kelurahan.setText(": " + getIntent().getExtras().getString("dataNamaKelurahan"));
        TextView kecamatan = (TextView) findViewById(R.id.skKecamatan1);
        kecamatan.setText(": " + getIntent().getExtras().getString("dataNamaKecamatan"));
        TextView nomorSurat = (TextView) findViewById(R.id.skNomor);
        nomorSurat.setText("No. : " + getIntent().getExtras().getString("dataNomorSurat"));
        TextView nama = (TextView) findViewById(R.id.skNama1);
        nama.setText(": " + getIntent().getExtras().getString("dataNama"));
        TextView jenisKelamin = (TextView) findViewById(R.id.skJenisKelamin1);
        jenisKelamin.setText(": " + getIntent().getExtras().getString("dataJenisKelamin"));
        TextView ttl = (TextView) findViewById(R.id.skTTL1);
        ttl.setText(": " + getIntent().getExtras().getString("dataTTL"));
        TextView kewarganegaraan = (TextView) findViewById(R.id.skKewarganegaraan1);
        kewarganegaraan.setText(": " + getIntent().getExtras().getString("dataKewarganegaraan"));
        TextView agama = (TextView) findViewById(R.id.skAgama1);
        agama.setText(": " + getIntent().getExtras().getString("dataAgama"));
        TextView pekerjaan = (TextView) findViewById(R.id.skPekerjaan1);
        pekerjaan.setText(": " + getIntent().getExtras().getString("dataPekerjaan"));
        TextView alamat = (TextView) findViewById(R.id.skAlamat1);
        alamat.setText(": " + getIntent().getExtras().getString("dataAlamat"));

        TextView tanggalDikirim = (TextView) findViewById(R.id.skCurrentdate);
        tanggalDikirim.setText("Malang, " + getIntent().getExtras().getString("dataTanggalDikirim"));
        TextView stempelLurah = (TextView) findViewById(R.id.skStempelLurah);
        stempelLurah.setText("KELURAHAN " + getIntent().getExtras().getString("dataNamaKelurahan"));
        stempelLurah.setVisibility(View.INVISIBLE);

        if (getIntent().getExtras().getString("dataStatus").equals("Selesai")){
            stempelLurah.setVisibility(View.VISIBLE);
            Button cetakSurat = (Button) findViewById(R.id.cetakSuratButton);
            cetakSurat.setEnabled(true);
            Button beriStempel = (Button) findViewById(R.id.beriStempelButton);
            beriStempel.setEnabled(false);
        }
    }

    public void gotoLangkah1(View view) {
        Intent gotoLangkah1 = new Intent(this, DataPendudukActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage};
        gotoLangkah1.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoLangkah1);
    }

    public void beriStempel(View view) {
        if (idJabatanMessage.equals("3")){
            TextView stempelRT = (TextView) findViewById(R.id.skStempelRT);
            stempelRT.setVisibility(View.VISIBLE);
            Button kirimRW = (Button) findViewById(R.id.kirimRWButton);
            kirimRW.setEnabled(true);
        }
        else if (idJabatanMessage.equals("2")){
            TextView stempelRW = (TextView) findViewById(R.id.skStempelRW);
            stempelRW.setVisibility(View.VISIBLE);
            Button kirimLurah = (Button) findViewById(R.id.kirimKelurahanButton);
            kirimLurah.setEnabled(true);
        }
        else {
            TextView stempelLurah = (TextView) findViewById(R.id.skStempelLurah);
            stempelLurah.setVisibility(View.VISIBLE);
            Button cetakSurat = (Button) findViewById(R.id.cetakSuratButton);
            cetakSurat.setEnabled(true);
        }

    }

    public void kirimRW(View view) {
        reference = FirebaseDatabase.getInstance().getReference();
        String status = "Ketua RW";
//        Button beri= (Button) findViewById(R.id.beriStempelButton);
//        beri.setVisibility(View.INVISIBLE);
//        Button beri1=(Button) findViewById(R.id.cetakSuratButton);
//        beri1.setVisibility(View.INVISIBLE);
        reference.child("suratPengantarNikah").child(getIntent().getExtras().getString("dataNomorSurat"))
            .child("status").setValue(status)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SuratNikahLangkah1VerifyActivity.this, "Surat dilanjutkan ke Ketua RW", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        Intent gotoPemberitahuan = new Intent(this, PemberitahuanActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage, nikMessage};
        gotoPemberitahuan.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoPemberitahuan);
    }

    public void cetakSurat(View view) {
        View surat = (View) findViewById(R.id.suratPengantarNikah);
        Log.d("size"," "+ surat.getWidth() +"  "+ surat.getWidth());
        bitmap = loadBitmapFromView(surat, surat.getWidth(), surat.getHeight());
        createPdf();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("suratPengantarNikah").child(getIntent().getExtras().getString("dataNomorSurat"))
                .child("status").setValue("Selesai");
        Intent gotoCetakSurat = new Intent(this, CetakSuratActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage, nikMessage};
        gotoCetakSurat.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoCetakSurat);
    }

    public static Bitmap loadBitmapFromView(View suratPengantar, int width, int height) {

        Bitmap bitmapCreating = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreating);
        suratPengantar.draw(canvas);

        return bitmapCreating;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        File pdfRoot = Environment.getExternalStorageDirectory();
        File pdfFolder = new File(pdfRoot.getAbsolutePath() + "/Documents/DsaKu");
        File pdfFile = new File(pdfFolder + "/" + getIntent().getExtras().getString("dataNomorSurat") + ".pdf");
        System.out.println("location " + pdfFile);
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }
        if (!pdfFile.exists()) {
            try {
                pdfFile.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        try {
            document.writeTo(new FileOutputStream(pdfFile));
            savedFilePath = pdfFolder.getAbsolutePath() + "/Documents/DsaKu/" + getIntent().getExtras().getString("dataNomorSurat") + ".pdf";
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created with the name " + getIntent().getExtras().getString("dataNomorSurat") + ".pdf", Toast.LENGTH_SHORT).show();

//        openGeneratedPDF();
    }

    private void openGeneratedPDF(){
        File file = new File(savedFilePath);
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void doingLogout(View view) {
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        startActivity(gotoLogin);
    }

    public void kirimKelurahan(View view) {
        reference = FirebaseDatabase.getInstance().getReference();
        String status = "Lurah";
        reference.child("suratPengantarNikah").child(getIntent().getExtras().getString("dataNomorSurat"))
                .child("status").setValue(status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SuratNikahLangkah1VerifyActivity.this, "Surat dilanjutkan ke Lurah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        Intent gotoPemberitahuan = new Intent(this, PemberitahuanActivity.class);
        String[] messages = new String[] {usernameMessage, passwordMessage, idJabatanMessage, nikMessage, nikMessage};
        gotoPemberitahuan.putExtra(EXTRA_MESSAGE, messages);
        startActivity(gotoPemberitahuan);
    }
}
