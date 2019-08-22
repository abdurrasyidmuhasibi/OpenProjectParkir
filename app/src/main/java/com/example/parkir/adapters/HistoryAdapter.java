package com.example.parkir.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkir.R;
import com.example.parkir.activities.History;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.history.Data_;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CustomViewHolder> {

    private HistoryAdapter adapter;
    private RecyclerView recyclerView;

    private List<Data_> dataList;
    private Context context;

    public HistoryAdapter(Context context, List<Data_> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private TextView alamatLokasi, tipeKendaraan, platNomor, createdAt, nominal, nama;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            alamatLokasi = mView.findViewById(R.id.alamatLokasi);
            tipeKendaraan = mView.findViewById(R.id.tipeKendaraan);
            platNomor = mView.findViewById(R.id.platNomor);
            createdAt = mView.findViewById(R.id.createdAt);
            nominal = mView.findViewById(R.id.nominal);
            nama = mView.findViewById(R.id.nama);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_history, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.alamatLokasi.setText(dataList.get(position).getLocationDetail());
        holder.tipeKendaraan.setText(dataList.get(position).getParkingType().getName());
        holder.platNomor.setText(dataList.get(position).getVehicleRegistration());
        holder.createdAt.setText(dataList.get(position).getCreatedAt());
        holder.nominal.setText(dataList.get(position).getNominal().toString());
        /* GET JWT TOKEN */
        PreferenceHelper prefShared = new PreferenceHelper(context);
        String roleid = prefShared.getStr("roleid");
        /* GET JWT TOKEN */
        if (roleid.equals("1")){
            holder.nama.setText(dataList.get(position).getSender().getFullName());
        }else{
            holder.nama.setText(dataList.get(position).getReceiver().getFullName());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}