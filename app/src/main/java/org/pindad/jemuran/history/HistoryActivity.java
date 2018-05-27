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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pindad.jemuran.history.adapter.HistoryAdapter;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.R;

import java.util.ArrayList;

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

        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValue = new ArrayList<>();
//
        yValue.add(new Entry(2, (int) 3000f));
        yValue.add(new Entry(3, (int) 4000f));
        yValue.add(new Entry(4, (int) 3500f));
        yValue.add(new Entry(5, (int) 4000f));
        yValue.add(new Entry(6, (int) 4500f));
        yValue.add(new Entry(7, (int) 4000f));
        yValue.add(new Entry(8, (int) 3500f));
        yValue.add(new Entry(9, (int) 4500f));
        yValue.add(new Entry(10, (int) 4000f));
        yValue.add(new Entry(11, (int) 4500f));
        yValue.add(new Entry(12, (int) 4000f));

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

        LineDataSet set1 = new LineDataSet(yValue,"Lama Proses Penjemuran");

        set1.setFillAlpha(110);
        set1.setColor(Color.BLUE);
        set1.setLineWidth(3f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
        firebaseSetUp();
    }
    private void firebaseSetUp() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getListHistoryMutableLiveData().observe(this, new Observer<ArrayList<ListHistory>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ListHistory> listHistories) {
                HistoryAdapter adapter=new HistoryAdapter(getApplicationContext(), listHistories );
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });
    }
}
