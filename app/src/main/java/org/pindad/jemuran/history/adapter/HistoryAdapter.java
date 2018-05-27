package org.pindad.jemuran.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by WIDHIYANTO NUGROHO on 15/04/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<ListHistory> listItems;
    private Context mContext;

    public HistoryAdapter(Context context, List<ListHistory> List) {
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
        holder.tanggal.setText(convertDate(listItems.get(position).getWaktu_awal()).toString());
        holder.awal.setText(getHour(convertDate(listItems.get(position).getWaktu_awal())));
        holder.akhir.setText(getHour(convertDate(listItems.get(position).getWaktu_akhir())));
        holder.rata.setText(listItems.get(position).getWaktu_rata()+"");
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

    public Date convertDate(long tanggal){
        return new Date(tanggal * 1000);
    }
    public String getHour(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        return hours + " : " + minute;
    }
}
