package org.pindad.jemuran.history;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.pindad.jemuran.R;
import org.pindad.jemuran.cuaca.CuacaViewModel;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.history.adapter.HistoryAdapter;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    private LineChart mChart;
    HistoryViewModel historyViewModel;
    private TextView mDataKosong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        mDataKosong = view.findViewById(R.id.data_kosong);
        recyclerView = view.findViewById(R.id.recycler_view);
        mChart = view.findViewById(R.id.lineChart1);
        firebaseSetUp();
        return view;
    }

    private void firebaseSetUp() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getListHistoryMutableLiveData().observe(this, new Observer<ArrayList<ListHistory>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ListHistory> listHistories) {
                if (listHistories==null)
                    mDataKosong.setVisibility(View.VISIBLE);
                else
                    mDataKosong.setVisibility(View.GONE);
                setRecyclerView(listHistories);
                setChart(listHistories);
            }
        });
    }

    private void setChart(ArrayList<ListHistory> listHistories){
        try {
            List<Entry> entries = new ArrayList<>();

            for (int i=0; i<listHistories.size(); i++){
                entries.add(new Entry(i+1,(int)listHistories.get(i).getKelembapan()));
            }

            LineDataSet data = new LineDataSet(entries, "Kelembapan");
            data.setFillAlpha(110);
            data.setColor(Color.BLUE);

            LineData lineData = new LineData(data);

            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            mChart.setData(lineData);
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }catch (Exception e){
            Toast.makeText(getContext(), "Histori Kosong", Toast.LENGTH_SHORT).show();
        }
    }
    private void setRecyclerView(ArrayList<ListHistory> ListHistory){
        HistoryAdapter adapter=new HistoryAdapter(getContext(), ListHistory );
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
