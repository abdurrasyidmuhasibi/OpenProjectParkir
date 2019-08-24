package com.example.parkir.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.activities.DetailPembayaran;
import com.example.parkir.activities.HomeUser;
import com.example.parkir.activities.Topup;
import com.example.parkir.activities.TopupConfirm;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.paymenttopup.PaymentTopupModel;
import com.example.parkir.model.paymenttype.Data_;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GatewayAdapter extends RecyclerView.Adapter<GatewayAdapter.CustomViewHolder> {

    private HistoryAdapter adapter;
    private RecyclerView recyclerView;

    private List<Data_> dataList;
    private Context context;

    public GatewayAdapter(Context context, List<Data_> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private ImageView imgLogo;
        private Button btnGateway;
        private EditText edNominal;
        api mApiInterface;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imgLogo = mView.findViewById(R.id.imgLogo);
            btnGateway = mView.findViewById(R.id.btnGateway);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_gateway, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        holder.btnGateway.setText(dataList.get(position).getName());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getImage())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgLogo);

        holder.btnGateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TopupConfirm.class);
                intent.putExtra("payment_gateway", holder.btnGateway.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
