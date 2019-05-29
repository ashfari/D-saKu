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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.d_saku.Class.Jadwal;
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.GUI.JadwalListActivity;
import com.project.d_saku.GUI.JadwalNotifikasiActivity;
import com.project.d_saku.R;

import java.util.ArrayList;

public class JadwalListAdapter extends RecyclerView.Adapter<JadwalListAdapter.ViewHolder>{

    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private ArrayList<Jadwal> listJadwal;
    private ArrayList<UserDetail> userDetails;
    private Context context;

    private DatabaseReference reference;

    private UserDetail currentUser;

    private boolean allow = false;

    public interface dataListener{
        void onDeleteData(Jadwal data, int position);
    }

    dataListener listener;

    public JadwalListAdapter(ArrayList<Jadwal> listJadwal, Context context, String usernameMessage, String passwordMessage, String idJabatanMessage, String nikMessage, ArrayList<UserDetail> userDetails) {
        this.listJadwal = listJadwal;
        this.context = context;
        this.usernameMessage = usernameMessage;
        this.passwordMessage = passwordMessage;
        this.idJabatanMessage = idJabatanMessage;
        this.userDetails = new ArrayList<>();
        this.userDetails = userDetails;
        this.nikMessage = nikMessage;
        listener = (JadwalListActivity)context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nama, nik, keterangan, kepentingan, waktu, status;
        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.jadwalNama);
            nik = itemView.findViewById(R.id.jadwalNik);
            keterangan = itemView.findViewById(R.id.jadwalKeterangan);
            kepentingan = itemView.findViewById(R.id.jadwalKepentingan);
            waktu = itemView.findViewById(R.id.jadwalWaktu);
            status = itemView.findViewById(R.id.jadwalStatus);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.jadwal_listjadwal_view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int indeksTmp = userDetails.size() - 1;
        final String username = listJadwal.get(position).getDariUsername();
        for (int i = 0; i < userDetails.size(); i++){
            if (listJadwal.get(position).getDariUsername().equals(userDetails.get(i).getUsername())){
                indeksTmp = i;
            }
        }
        final int indeks = indeksTmp;
        final String nama = userDetails.get(indeks).getNamaLengkap();
        final String nik = userDetails.get(indeks).getNik();
        final String kepentingan = listJadwal.get(position).getKepentinganJadwal();
        final String waktu = listJadwal.get(position).getWaktuJadwal();
        final String status = listJadwal.get(position).getStatusJadwal();
        final String keterangan = "Dari " + userDetails.get(indeks).getJabatan() + " RT"
                + userDetails.get(indeks).getNomorRT() + "/RW" + userDetails.get(indeks).getNomorRW()
                + " " + userDetails.get(indeks).getNamaKelurahan() + " kepada " + listJadwal.get(position).getKeJabatan();

        currentUser = new UserDetail();
        for (int i = 0; i < userDetails.size(); i++){
            if (usernameMessage.equals(userDetails.get(i).getUsername())){
                currentUser = userDetails.get(i);
            }
        }
        holder.nama.setText("Nama : " + nama);
        holder.nik.setText("NIK : " + nik);
        holder.keterangan.setText("Keterangan : " + keterangan);
        holder.kepentingan.setText("Kepentingan : " + kepentingan);
        holder.waktu.setText("Waktu : " + waktu);
        holder.status.setText("Status : " + status);
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }
}