package com.example.parkir;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkir.model.history.HistoryModel;

import java.util.ArrayList;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {
    private ArrayList<HistoryModel> dataList;

    public RetrofitAdapter(ArrayList<HistoryModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtAlamatLokasi.setText(dataList.get(position).getData().getDatas().get(position).getLocationDetail());
        holder.txtTipeKendaraan.setText(dataList.get(position).getData().getDatas().get(position).getParkingType().getName());
        holder.txtPlatNomor.setText(dataList.get(position).getData().getDatas().get(position).getVehicleRegistration());
        holder.txtCreatedAt.setText(dataList.get(position).getData().getDatas().get(position).getCreatedAt());
        holder.txtNominal.setText(dataList.get(position).getData().getDatas().get(position).getNominal());
        holder.txtNama.setText(dataList.get(position).getData().getDatas().get(position).getSender().getFullName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtAlamatLokasi, txtTipeKendaraan, txtPlatNomor, txtCreatedAt, txtNominal, txtNama;

        MyViewHolder(View itemView) {
            super(itemView);
            txtAlamatLokasi = (TextView) itemView.findViewById(R.id.alamatLokasi);
            txtTipeKendaraan = (TextView) itemView.findViewById(R.id.tipeKendaraan);
            txtPlatNomor = (TextView) itemView.findViewById(R.id.platNomor);
            txtCreatedAt = (TextView) itemView.findViewById(R.id.createdAt);
            txtNominal = (TextView) itemView.findViewById(R.id.nominal);
            txtNama = (TextView) itemView.findViewById(R.id.nama);
        }
    }
}
