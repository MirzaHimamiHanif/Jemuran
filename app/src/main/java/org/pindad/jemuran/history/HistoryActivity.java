package org.pindad.jemuran.history;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.history.adapter.HistoryAdapter;
import org.pindad.jemuran.history.modelhistory.ListDataTanggal;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    //deklarasi variabel reyclerview
    RecyclerView recyclerView;
    private LineChart mChart;
    HistoryViewModel historyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        //menampilkan reyclerview yang ada pada file layout dengan id reycler view
        mChart = (LineChart) findViewById(R.id.lineChart1);

//        mChart.setOnChartGestureListener(HistoryBulanFragment.this);
//        mChart.setOnChartValueSelectedListener(HistoryBulanFragment.this);


        ArrayList<String> xValue = new ArrayList<String>();

        xValue.add ("January");
        xValue.add ("February");
        xValue.add ("Maret");
        xValue.add ("April");
        xValue.add ("Mei");
        xValue.add ("Juni");
////
//        yValue.add("Jan");
//        yValue.add("Feb");
//        yValue.add("Mar");
//        yValue.add("Apr");
//        yValue.add("mei");
//        yValue.add("Jun");
//        yValue.add("Jul");
//        yValue.add("Agus");
//        yValue.add("Sept");
//        yValue.add("Okt");
//        yValue.add("Nov");
//        yValue.add("Des");

        firebaseSetUp();
    }
    private void firebaseSetUp() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getListHistoryMutableLiveData().observe(this, new Observer<ArrayList<ListDataTanggal>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ListDataTanggal> listHistories) {
                setRecyclerView(listHistories);
                setGrafik(listHistories);
            }
        });
    }

    private void setGrafik(ArrayList<ListDataTanggal> listDataTanggal){
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        final HashMap<Integer, String>numMap = new HashMap<>();

        ArrayList<Entry> setValue = new ArrayList<>();
        for (int i=0; i<listDataTanggal.size(); i++){
            numMap.put(i,listDataTanggal.get(i).getTanggal());
            setValue.add(new Entry(i, Integer.parseInt(listDataTanggal.get(i).getJumlahWaktu())));
        }

        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return numMap.get((int)value);
            }
        });

        LineDataSet set1 = new LineDataSet(setValue,"Lama Proses Penjemuran (Jam)");

        set1.setFillAlpha(110);
        set1.setColor(Color.BLUE);
        set1.setLineWidth(3f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
    }

    private void setRecyclerView(ArrayList<ListDataTanggal> listDataTanggal){
        HistoryAdapter adapter=new HistoryAdapter(getApplicationContext(), listDataTanggal );
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
