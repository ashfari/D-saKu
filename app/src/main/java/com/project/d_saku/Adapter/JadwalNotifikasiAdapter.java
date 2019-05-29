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
import com.project.d_saku.Class.Verifikasi;
import com.project.d_saku.GUI.JadwalNotifikasiActivity;
import com.project.d_saku.GUI.VerifikasiNotifikasiActivity;
import com.project.d_saku.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JadwalNotifikasiAdapter extends RecyclerView.Adapter<JadwalNotifikasiAdapter.ViewHolder>{

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

    public JadwalNotifikasiAdapter(ArrayList<Jadwal> listJadwal, Context context, String usernameMessage, String passwordMessage, String idJabatanMessage, String nikMessage, ArrayList<UserDetail> userDetails) {
        this.listJadwal = listJadwal;
        this.context = context;
        this.usernameMessage = usernameMessage;
        this.passwordMessage = passwordMessage;
        this.idJabatanMessage = idJabatanMessage;
        this.userDetails = new ArrayList<>();
        this.userDetails = userDetails;
        this.nikMessage = nikMessage;
        listener = (JadwalNotifikasiActivity)context;
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
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.jadwal_notifikasijadwal_view_design, parent, false);
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
        final String keterangan = userDetails.get(indeks).getJabatan() + " RT"
                + userDetails.get(indeks).getNomorRT() + "/RW" + userDetails.get(indeks).getNomorRW()
                + " " + userDetails.get(indeks).getNamaKelurahan();

        currentUser = new UserDetail();
        for (int i = 0; i < userDetails.size(); i++){
            if (usernameMessage.equals(userDetails.get(i).getUsername())){
                currentUser = userDetails.get(i);
            }
        }

        if (currentUser.getNomorRT().equals(userDetails.get(indeks).getNomorRT()) && currentUser.getNomorRW().equals(userDetails.get(indeks).getNomorRW())
                && currentUser.getNamaKelurahan().equals(userDetails.get(indeks).getNamaKelurahan())){
            holder.nama.setText("Nama : " + nama);
            holder.nik.setText("NIK : " + nik);
            holder.keterangan.setText("Keterangan : " + keterangan);
            holder.kepentingan.setText("Kepentingan : " + kepentingan);
            holder.waktu.setText("Waktu : " + waktu);
            holder.status.setText("Status : " + status);

            holder.ListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    String[] actionTemp = {"Setujui Jadwal", "Tolak Jadwal"};
                    if (username.equals(usernameMessage)){
                        actionTemp = new String[] {"Hapus Jadwal"};
                    }
                    final String[] action = actionTemp;
                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setItems(action,  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i){
                                case 0:
                                    Bundle bundle = new Bundle();
                                    bundle.putString("dataUsernameMessageUser", usernameMessage);
                                    bundle.putString("dataPasswordMessageUser", passwordMessage);
                                    bundle.putString("dataIdJabatanMessageUser", idJabatanMessage);
                                    bundle.putString("dataNikMessageUser", nikMessage);
                                    reference = FirebaseDatabase.getInstance().getReference();
                                    reference.child("jadwal").child(listJadwal.get(position).getIdJadwal())
                                            .child("status").setValue("Disetujui");
                                    Intent gotoJadwalNotifikasi = new Intent(view.getContext(), JadwalNotifikasiActivity.class);
                                    gotoJadwalNotifikasi.putExtras(bundle);
                                    context.startActivity(gotoJadwalNotifikasi);
                                    break;
                                case 1:
                                    listener.onDeleteData(listJadwal.get(position), position);
                                    break;
                            }
                        }
                    });
                    alert.create();
                    alert.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }
}