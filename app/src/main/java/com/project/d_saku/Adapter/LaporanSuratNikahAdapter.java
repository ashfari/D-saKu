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
import com.project.d_saku.GUI.ListSuratNikahActivity;
import com.project.d_saku.GUI.PemberitahuanActivity;
import com.project.d_saku.GUI.SuratNikahLangkah1VerifyActivity;
import com.project.d_saku.R;

import java.util.ArrayList;

public class LaporanSuratNikahAdapter extends RecyclerView.Adapter<LaporanSuratNikahAdapter.ViewHolder>{

    private ArrayList<SuratPengantarNikah> listSuratNikah;
    private Context context;
    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    public interface dataListener{
        void onDeleteData(SuratPengantarNikah data, int position);
    }

    dataListener listener;

    public LaporanSuratNikahAdapter(ArrayList<SuratPengantarNikah> listSuratNikah, Context context, String usernameMessage, String passwordMessage, String idJabatanMessage, String nikMessage) {
        this.listSuratNikah = listSuratNikah;
        this.context = context;
        this.usernameMessage = usernameMessage;
        this.passwordMessage = passwordMessage;
        this.idJabatanMessage = idJabatanMessage;
        this.nikMessage = nikMessage;
        listener = (ListSuratNikahActivity)context;
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
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.suratnikah_listlaporan_view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String nama = listSuratNikah.get(position).getNama();
        final String noKTP = listSuratNikah.get(position).getNoKTP();
        final String tanggalDikirim = listSuratNikah.get(position).getTanggalDikirim();
        final String keterangan = "Menunggu persetujuan " + listSuratNikah.get(position).getStatus();;

        holder.nama.setText("Nama : " + nama);
        holder.nik.setText("NIK : " + noKTP);
        holder.tanggalDikirim.setText("Tanggal Dikirim : " + tanggalDikirim);
        holder.keterangan.setText("Keterangan : " + keterangan);

        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String[] action = {"Hapus"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                listener.onDeleteData(listSuratNikah.get(position), position);
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
        return listSuratNikah.size();
    }
}