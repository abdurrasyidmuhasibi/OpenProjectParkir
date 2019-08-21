package com.example.parkir;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.history.HistoryModel;

import java.util.ArrayList;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<HistoryModel> dataArrayList;
    private ArrayList<AccountModel> dataAccount;

    public RetrofitAdapter(Context ctx, ArrayList<HistoryModel> dataArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.dataArrayList = dataArrayList;
    }

    @Override
    public RetrofitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.activity_history, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RetrofitAdapter.MyViewHolder holder, int position) {
        holder.alamatLokasi.setText(dataArrayList.get(position).getData().getDatas().get(position).getLocationDetail());
        holder.tipeKendaraan.setText(dataArrayList.get(position).getData().getDatas().get(position).getParkingType().getName());
        holder.platNomor.setText(dataArrayList.get(position).getData().getDatas().get(position).getVehicleRegistration());
        holder.createdAt.setText(dataArrayList.get(position).getData().getDatas().get(position).getCreatedAt());
        holder.createdAt.setText(dataArrayList.get(position).getData().getDatas().get(position).getNominal());
        int roleid = dataAccount.get(position).getData().getDatas().getAccountRole().getId();
        if (roleid == 1) {
            holder.nama.setText(dataArrayList.get(position).getData().getDatas().get(position).getSender().getFullName());
        } else if (roleid == 2) {
            holder.nama.setText(dataArrayList.get(position).getData().getDatas().get(position).getReceiver().getFullName());
        }
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView alamatLokasi, tipeKendaraan, platNomor, createdAt, nominal, nama;

        public MyViewHolder(View itemView) {
            super(itemView);
            alamatLokasi = (TextView) itemView.findViewById(R.id.alamatLokasi);
            tipeKendaraan = (TextView) itemView.findViewById(R.id.tipeKendaraan);
            platNomor = (TextView) itemView.findViewById(R.id.platNomor);
            createdAt = (TextView) itemView.findViewById(R.id.createdAt);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
            nama = (TextView) itemView.findViewById(R.id.nama);
        }

    }
}
