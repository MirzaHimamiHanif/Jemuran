package org.pindad.jemuran.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.pindad.jemuran.R;
import org.pindad.jemuran.RecyclerViewHolder;

/**
 * Created by WIDHIYANTO NUGROHO on 15/04/2018.
 */

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerViewHolder> {

    //deklarasi variable context

    private final Context context;

    String [] name={"Tanggal 1","Tanggal 2","Tanggal 3","Tanggal 4",
            "Tanggal 5","Tanggal 6","Tanggal 7","Tanggal 8"};
    // menampilkan list item dalam bentuk text dengan tipe data string dengan variable name

    LayoutInflater inflater;
    public RecyclerAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.list_history, parent, false);

        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

//        holder.number.setText(position);
        holder.judul.setText(name[position]);
        holder.awal.setOnClickListener(clickListener);
        holder.akhir.setOnClickListener(clickListener);
        holder.judul.setTag(holder);

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        //member aksi saat cardview diklik berdasarkan posisi tertentu
            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();

            int position = vholder.getPosition();


            Toast.makeText(context, "Menu ini berada di posisi " + position, Toast.LENGTH_LONG).show();


        }
    };



    @Override
    public int getItemCount() {
        return name.length;
    }
}
