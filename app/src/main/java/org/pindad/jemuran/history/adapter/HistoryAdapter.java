package org.pindad.jemuran.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pindad.jemuran.R;
import org.pindad.jemuran.history.modelhistory.ListHistory;

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
        try {
            String[] temp = splitString(listItems.get(position).getTanggal());
            holder.nomer.setText((position+1)+"");
            holder.tanggal.setText(temp[0]);
            holder.jam.setText(temp[1]);
            holder.suhu.setText(String.valueOf(listItems.get(position).getSuhu()));
            holder.kelembapan.setText(String.valueOf(listItems.get(position).getKelembapan()));
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        try {
            return listItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public String[] splitString(String tanggal){
        return tanggal.split(" ");
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomer, tanggal, jam, suhu, kelembapan;

        public ViewHolder(View itemView) {
            super(itemView);
            nomer = itemView.findViewById(R.id.number);
            tanggal = itemView.findViewById(R.id.tanggal_jemuran);
            jam = itemView.findViewById(R.id.jam);
            suhu = itemView.findViewById(R.id.suhu);
            kelembapan = itemView.findViewById(R.id.kelembapan);
        }
    }

}
