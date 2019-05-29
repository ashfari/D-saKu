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

import com.project.d_saku.Class.UserDetail;
import com.project.d_saku.GUI.AddPendudukActivity;
import com.project.d_saku.GUI.DataPendudukActivity;
import com.project.d_saku.GUI.SuratNikahLangkah1VerifyActivity;
import com.project.d_saku.GUI.UpdatePendudukActivity;
import com.project.d_saku.R;

import java.util.ArrayList;

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.ViewHolder>{

    private String usernameMessage, passwordMessage, idJabatanMessage, nikMessage;

    private ArrayList<UserDetail> listUser;
    private Context context;

    public interface dataListener{
        void onDeleteData(UserDetail data, int position);
    }

    dataListener listener;

    public UserDetailAdapter(ArrayList<UserDetail> listUser, Context context, String usernameMessage, String passwordMessage, String idJabatanMessage, String nikMessage) {
        this.listUser = listUser;
        this.context = context;
        this.usernameMessage = usernameMessage;
        this.passwordMessage = passwordMessage;
        this.idJabatanMessage = idJabatanMessage;
        this.nikMessage = nikMessage;
        listener = (DataPendudukActivity)context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView username, nama, nik, jabatan;
        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            nama = itemView.findViewById(R.id.nama);
            nik = itemView.findViewById(R.id.nik);
            jabatan = itemView.findViewById(R.id.jabatan);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_datauser_view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String username = listUser.get(position).getUsername();
        final String nama = listUser.get(position).getNamaLengkap();
        final String nik = listUser.get(position).getNik();
        final String jabatan = listUser.get(position).getJabatan();

        holder.username.setText("Username : " + username);
        holder.nama.setText("Nama : " + nama);
        holder.nik.setText("NIK : " + nik);
        holder.jabatan.setText("Jabatan : " + jabatan);

        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String[] action = {"Perbarui", "Hapus"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Bundle bundle = new Bundle();
                                bundle.putString("dataUsername", listUser.get(position).getUsername());
                                bundle.putString("dataNama", listUser.get(position).getNamaLengkap());
                                bundle.putString("dataNik", listUser.get(position).getNik());
                                bundle.putString("dataAgama", listUser.get(position).getAgama());
                                bundle.putString("dataJabatan", listUser.get(position).getJabatan());
                                bundle.putString("dataJenisKelamin", listUser.get(position).getJenisKelamin());
                                bundle.putString("dataJenisPekerjaan", listUser.get(position).getJenisPekerjaan());
                                bundle.putString("dataNamaKelurahan", listUser.get(position).getNamaKelurahan());
                                bundle.putString("dataNoKK", listUser.get(position).getNoKK());
                                bundle.putString("dataNomorRT", listUser.get(position).getNomorRT());
                                bundle.putString("dataNomorRW", listUser.get(position).getNomorRW());
                                bundle.putString("dataPendidikan", listUser.get(position).getPendidikan());
                                bundle.putString("dataTanggalLahir", listUser.get(position).getTanggalLahir());
                                bundle.putString("dataTempatLahir", listUser.get(position).getTempatLahir());
                                bundle.putString("dataTanggalDaftar", listUser.get(position).getTanggalDaftar());
                                bundle.putString("dataUsernameMessageUser", usernameMessage);
                                bundle.putString("dataPasswordMessageUser", passwordMessage);
                                bundle.putString("dataIdJabatanMessageUser", idJabatanMessage);
                                bundle.putString("dataNikMessageUser", nikMessage);
                                Intent gotoUpdatePenduduk = new Intent(view.getContext(), UpdatePendudukActivity.class);
                                gotoUpdatePenduduk.putExtras(bundle);
                                context.startActivity(gotoUpdatePenduduk);
                                break;
                            case 1:
                                listener.onDeleteData(listUser.get(position), position);
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
        return listUser.size();
    }
}