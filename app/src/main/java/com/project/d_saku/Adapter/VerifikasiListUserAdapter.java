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
import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.Class.Verifikasi;
import com.project.d_saku.GUI.VerifikasiListUserActivity;
import com.project.d_saku.GUI.VerifikasiNotifikasiActivity;
import com.project.d_saku.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class VerifikasiListUserAdapter extends RecyclerView.Adapter<VerifikasiListUserAdapter.ViewHolder>{

    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage, keteranganVerifikasi;

    private ArrayList<Verifikasi> listVerifikasi;
    private ArrayList<UserDetail> userDetails;
    private Context context;

    private SimpleDateFormat dateFormatter;

    private DatabaseReference reference;

    public interface dataListener{
        void onDeleteData(Verifikasi data, int position);
    }

    dataListener listener;

    public VerifikasiListUserAdapter(ArrayList<Verifikasi> listVerifikasi, Context context, String usernameMessage, String passwordMessage, String idJabatanMessage, String nikMessage, ArrayList<UserDetail> userDetails) {
        this.listVerifikasi = listVerifikasi;
        this.context = context;
        this.usernameMessage = usernameMessage;
        this.passwordMessage = passwordMessage;
        this.idJabatanMessage = idJabatanMessage;
        this.userDetails = new ArrayList<>();
        this.userDetails = userDetails;
        this.nikMessage = nikMessage;
        listener = (VerifikasiListUserActivity)context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView username, nama, nik, keterangan;
        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            nama = itemView.findViewById(R.id.nama);
            nik = itemView.findViewById(R.id.nik);
            keterangan = itemView.findViewById(R.id.keterangan);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.verifikasi_notifikasi_view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int indeksTmp = userDetails.size() - 1;
        final String username = listVerifikasi.get(position).getDariVerifikasi();
        for (int i = 0; i < userDetails.size(); i++){
            if (listVerifikasi.get(position).getDariNik().equals(userDetails.get(i).getNik())){
                indeksTmp = i;
            }
        }
        final int indeks = indeksTmp;
        final String nama = userDetails.get(indeks).getNamaLengkap();
        final String nik = userDetails.get(indeks).getNik();
        String keteranganTemp = "Data tidak ditemukan";
        if (!nama.equals("-")){
            keteranganTemp = userDetails.get(indeks).getJabatan() + " RT"
                    + userDetails.get(indeks).getNomorRT() + "/RW"
                    + userDetails.get(indeks).getNomorRW() + " "
                    + userDetails.get(indeks).getNamaKelurahan();
        }
        final String keterangan = keteranganTemp;

        holder.username.setText("Username : " + username);
        holder.nama.setText("Nama : " + nama);
        holder.nik.setText("NIK : " + nik);
        holder.keterangan.setText("Keterangan : " + keterangan);

//        holder.ListItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                String[] actionTemp = {"Terima", "Tolak"};
//                if (keterangan.equals("Data tidak ditemukan")){
//                    actionTemp = new String[] {"Tolak"};
//                }
//                final String[] action = actionTemp;
//                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
//                alert.setItems(action,  new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        switch (i){
//                            case 0:
//                                Bundle bundle = new Bundle();
//                                bundle.putString("dataUsernameMessageUser", usernameMessage);
//                                bundle.putString("dataPasswordMessageUser", passwordMessage);
//                                bundle.putString("dataIdJabatanMessageUser", idJabatanMessage);
//                                bundle.putString("dataNikMessageUser", nikMessage);
//                                dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//                                Date currentDate = Calendar.getInstance().getTime();
//                                String formattedCurrentDate = dateFormatter.format(currentDate);
//                                reference = FirebaseDatabase.getInstance().getReference();
//                                reference.child("verifikasi").child(listVerifikasi.get(position).getIdVerifikasi())
//                                        .child("status").setValue("Diterima");
//                                reference.child("verifikasi").child(listVerifikasi.get(position).getIdVerifikasi())
//                                        .child("tanggalTerima").setValue(formattedCurrentDate);
//                                reference.child("userDetail").child(listVerifikasi.get(position).getDariNik())
//                                        .child("username").setValue(listVerifikasi.get(position).getDariVerifikasi());
//                                String jabatan = userDetails.get(indeks).getJabatan();
//                                String idJabatan = "";
//                                switch (jabatan){
//                                    case "Warga" :
//                                        idJabatan = "4";
//                                        break;
//                                    case "Ketua RT" :
//                                        idJabatan = "3";
//                                        break;
//                                    case "Ketua RW" :
//                                        idJabatan = "2";
//                                        break;
//                                    default :
//                                        idJabatan = "";
//                                        break;
//                                }
//                                reference.child("registeredUser").child(listVerifikasi.get(position).getDariVerifikasi())
//                                        .child("idJabatan").setValue(idJabatan);
//                                Intent gotoVerifikasiNotifikasi = new Intent(view.getContext(), VerifikasiNotifikasiActivity.class);
//                                gotoVerifikasiNotifikasi.putExtras(bundle);
//                                context.startActivity(gotoVerifikasiNotifikasi);
//                                break;
//                            case 1:
//                                listener.onDeleteData(listVerifikasi.get(position), position);
//                                break;
//                        }
//                    }
//                });
//                alert.create();
//                alert.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listVerifikasi.size();
    }
}