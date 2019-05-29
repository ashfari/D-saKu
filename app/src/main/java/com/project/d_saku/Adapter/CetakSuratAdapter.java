package com.project.d_saku.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.d_saku.Class.SuratPengantarNikah;
import com.project.d_saku.GUI.CetakSuratActivity;
import com.project.d_saku.GUI.PemberitahuanActivity;
import com.project.d_saku.GUI.SuratNikahLangkah1VerifyActivity;
import com.project.d_saku.R;

import java.util.ArrayList;

public class CetakSuratAdapter extends RecyclerView.Adapter<CetakSuratAdapter.ViewHolder>{

    private ArrayList<SuratPengantarNikah> listPemberitahuan;
    private Context context;
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    public interface dataListener{
        void onDeleteData(SuratPengantarNikah data, int position);
    }

    dataListener listener;

    public CetakSuratAdapter(ArrayList<SuratPengantarNikah> listPemberitahuan, Context context, String usernameMessage, String passwordMessage, String idJabatanMessage, String nikMessage) {
        this.listPemberitahuan = listPemberitahuan;
        this.context = context;
        this.usernameMessage = usernameMessage;
        this.passwordMessage = passwordMessage;
        this.idJabatanMessage = idJabatanMessage;
        this.nikMessage = nikMessage;
        listener = (CetakSuratActivity)context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nama, nik, tanggalDikirim, keterangan;
        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.pemberitahuanNama);
            nik = itemView.findViewById(R.id.pemberitahuanNik);
            tanggalDikirim = itemView.findViewById(R.id.pemberitahuanTanggalDikirim);
            keterangan = itemView.findViewById(R.id.pemberitahuanKeterangan);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.rtrw_pemberitahuansurat_view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String nama = listPemberitahuan.get(position).getNama();
        final String noKTP = listPemberitahuan.get(position).getNoKTP();
        final String tanggalDikirim = listPemberitahuan.get(position).getTanggalDikirim();
        final String keterangan = "Surat Pengantar Nikah RT/RW";

        holder.nama.setText("Nama : " + nama);
        holder.nik.setText("NIK : " + noKTP);
        holder.tanggalDikirim.setText("Tanggal Dikirim : " + tanggalDikirim);
        holder.keterangan.setText("Keterangan : " + keterangan);

        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String[] action = {"Lihat", "Hapus"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Bundle bundle = new Bundle();
                                bundle.putString("dataAgama", listPemberitahuan.get(position).getAgama());
                                bundle.putString("dataAlamat", listPemberitahuan.get(position).getAlamat());
                                bundle.putString("dataJenisKelamin", listPemberitahuan.get(position).getJenisKelamin());
                                bundle.putString("dataKewarganegaraan", listPemberitahuan.get(position).getKewarganegaraan());
                                bundle.putString("dataNamaCalon", listPemberitahuan.get(position).getNamaCalon());
                                bundle.putString("dataNamaKecamatan", listPemberitahuan.get(position).getNamaKecamatan());
                                bundle.putString("dataWilayah", listPemberitahuan.get(position).getNamaWilayah());
                                bundle.putString("dataPekerjaan", listPemberitahuan.get(position).getPekerjaan());
                                bundle.putString("dataNomorSurat", listPemberitahuan.get(position).getNomorSurat());
                                bundle.putString("dataTTL", listPemberitahuan.get(position).getTtl());
                                bundle.putString("dataNama", listPemberitahuan.get(position).getNama());
                                bundle.putString("dataNoKTP", listPemberitahuan.get(position).getNoKTP());
                                bundle.putString("dataTanggalDikirim", listPemberitahuan.get(position).getTanggalDikirim());
                                bundle.putString("dataNomorRT", listPemberitahuan.get(position).getNomorRT());
                                bundle.putString("dataNomorRW", listPemberitahuan.get(position).getNomorRW());
                                bundle.putString("dataNamaKelurahan", listPemberitahuan.get(position).getNamaKelurahan());
                                bundle.putString("dataStatus", listPemberitahuan.get(position).getStatus());
                                bundle.putString("dataKeterangan", keterangan);
                                bundle.putString("dataUsernameMessageUser", usernameMessage);
                                bundle.putString("dataPasswordMessageUser", passwordMessage);
                                bundle.putString("dataIdJabatanMessageUser", idJabatanMessage);
                                bundle.putString("dataNikMessageUser", nikMessage);
                                Intent gotoSuratNikahVerify = new Intent(view.getContext(), SuratNikahLangkah1VerifyActivity.class);
                                gotoSuratNikahVerify.putExtras(bundle);
                                context.startActivity(gotoSuratNikahVerify);
                                break;
                            case 1:
                                listener.onDeleteData(listPemberitahuan.get(position), position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPemberitahuan.size();
    }
}