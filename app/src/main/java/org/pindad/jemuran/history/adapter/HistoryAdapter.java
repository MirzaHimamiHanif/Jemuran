package org.pindad.jemuran.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.pindad.jemuran.history.modelhistory.ListDataTanggal;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by WIDHIYANTO NUGROHO on 15/04/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<ListDataTanggal> listItems;
    private Context mContext;

    public HistoryAdapter(Context context, List<ListDataTanggal> List) {
        mContext = context;
        listItems = List;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nomer.setText((position+1)+"");
        holder.tanggal.setText(listItems.get(position).getTanggal());
        holder.awal.setText(listItems.get(position).getWaktuMulai());
        holder.akhir.setText(listItems.get(position).getWaktuAkhir());
        holder.rata.setText(listItems.get(position).getJumlahWaktu());
    }

    @Override
    public int getItemCount() {
        try {
            return listItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomer, tanggal, awal, akhir, rata;

        public ViewHolder(View itemView) {
            super(itemView);
            nomer = itemView.findViewById(R.id.number);
            tanggal = itemView.findViewById(R.id.tanggal_jemuran);
            awal = itemView.findViewById(R.id.waktuMulai);
            akhir = itemView.findViewById(R.id.waktuAkhir);
            rata = itemView.findViewById(R.id.waktu);
        }
    }

}
