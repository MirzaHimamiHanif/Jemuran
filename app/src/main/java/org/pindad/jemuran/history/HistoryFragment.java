package org.pindad.jemuran.history;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.github.mikephil.charting.charts.LineChart;

import org.pindad.jemuran.R;
import org.pindad.jemuran.cuaca.CuacaViewModel;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.history.adapter.HistoryAdapter;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.w3c.dom.Text;

import java.util.ArrayList;

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
            }
        });
    }

    private void setRecyclerView(ArrayList<ListHistory> ListHistory){
        HistoryAdapter adapter=new HistoryAdapter(getContext(), ListHistory );
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
