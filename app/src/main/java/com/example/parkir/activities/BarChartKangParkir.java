package com.example.parkir.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.adapters.HistoryAdapter;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.chartmonthly.ChartMonthlyModel;
import com.example.parkir.model.chartmonthly.Data_;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarChartKangParkir extends AppCompatActivity {

    private BarChart mBarChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart_kang_parkir);

        mBarChart = findViewById(R.id.chart);

        /*Create handle for the RetrofitInstance interface*/
        api service = RetrofitClient.getRetrofitInstance().create(api.class);
        /* GET JWT TOKEN */
        PreferenceHelper prefShared3 = new PreferenceHelper(this);
        String jwtToken = prefShared3.getStr("jwtToken");
        /* GET JWT TOKEN */
        Call<ChartMonthlyModel> call = service.payments_chart_account(jwtToken);

        call.enqueue(new Callback<ChartMonthlyModel>() {
            @Override
            public void onResponse(Call<ChartMonthlyModel> call, Response<ChartMonthlyModel> response) {
                Toast.makeText(BarChartKangParkir.this, "Success...", Toast.LENGTH_SHORT).show();
                generateDataList(response.body().getData().getDatas());


            }

            @Override
            public void onFailure(Call<ChartMonthlyModel> call, Throwable t) {
                Toast.makeText(BarChartKangParkir.this, "error"+ t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Data_> dataList) {
//      Toast.makeText(BarChartKangParkir.this, ""+ dataList.get(i).getTotal(), Toast.LENGTH_SHORT).show();

        float groupSpace = 0.08f;
        float barSpace = 0.02f;
        float barWidth = 0.45f;
        float tahunAwal = 3f;

        // Data-data yang akan ditampilkan di Chart
        List<BarEntry> dataPemasukan = new ArrayList<BarEntry>();
        List<BarEntry> dataPengeluaran = new ArrayList<BarEntry>();

        for (Integer i = 0; i < dataList.size(); i++) {
            dataPemasukan.add(new BarEntry(dataList.get(i).getMonth(), dataList.get(i).getTotal()));
            dataPengeluaran.add(new BarEntry(dataList.get(i).getMonth(), dataList.get(i).getGoverment()));
        }

        // Pengaturan atribut bar, seperti warna dan lain-lain
        BarDataSet dataSet1 = new BarDataSet(dataPemasukan, "Saya");
        dataSet1.setColors(Color.parseColor("#0037ff"));

        BarDataSet dataSet2 = new BarDataSet(dataPengeluaran, "Pemerintah");
        dataSet2.setColor(Color.parseColor("#ff0000"));

        // Membuat Bar data yang akan di set ke Chart
        BarData barData = new BarData(dataSet1, dataSet2);

        // Pengaturan sumbu X
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM.BOTTOM);
        xAxis.setCenterAxisLabels(true);

        // Agar ketika di zoom tidak menjadi pecahan
        xAxis.setGranularity(1f);

        // Diubah menjadi integer, kemudian dijadikan String
        // Ini berfungsi untuk menghilankan koma, dan tanda ribuah pada tahun
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });

        //Menghilangkan sumbu Y yang ada di sebelah kanan
        mBarChart.getAxisRight().setEnabled(false);

        // Menghilankan deskripsi pada Chart
        mBarChart.getDescription().setEnabled(false);

        // Set data ke Chart
        // Tambahkan invalidate setiap kali mengubah data chart
        mBarChart.setData(barData);
        mBarChart.getBarData().setBarWidth(barWidth);
        mBarChart.getXAxis().setAxisMinimum(tahunAwal);
        mBarChart.getXAxis().setAxisMaximum(dataList.size());
        mBarChart.getXAxis().setAxisMaximum(tahunAwal + mBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * dataList.size());
        mBarChart.groupBars(tahunAwal, groupSpace, barSpace);
        mBarChart.setDragEnabled(true);
        mBarChart.invalidate();
    }
}
