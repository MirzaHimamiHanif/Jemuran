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
import org.pindad.jemuran.history.modelhistory.ListHistory;
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
        mChart = (LineChart) findViewById(R.id.lineChart1);

        ArrayList<String> xValue = new ArrayList<String>();

        xValue.add ("January");
        xValue.add ("February");
        xValue.add ("Maret");
        xValue.add ("April");
        xValue.add ("Mei");
        xValue.add ("Juni");
        firebaseSetUp();
    }
    private void firebaseSetUp() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getListHistoryMutableLiveData().observe(this, new Observer<ArrayList<ListHistory>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ListHistory> listHistories) {
                setRecyclerView(listHistories);
            }
        });
    }

    private void setRecyclerView(ArrayList<ListHistory> ListHistory){
        HistoryAdapter adapter=new HistoryAdapter(getApplicationContext(), ListHistory );
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
