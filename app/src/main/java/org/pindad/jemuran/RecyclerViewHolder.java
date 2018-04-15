package org.pindad.jemuran;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by WIDHIYANTO NUGROHO on 15/04/2018.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    // ViewHolder akan mendeskripisikan item view yang ditempatkan di dalam RecyclerView.
    public TextView tv1;
    TextView tv2; //deklarasi textview
    public ImageView imageView;  //deklarasi imageview

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        tv1= (TextView) itemView.findViewById(R.id.daftar_judul);
        //menampilkan text dari widget CardView pada id daftar_judul
        tv2= (TextView) itemView.findViewById(R.id.daftar_deskripsi);
        //menampilkan text deskripsi dari widget CardView pada id daftar_deskripsi
        imageView= (ImageView) itemView.findViewById(R.id.daftar_icon);
        //menampilkan gambar atau icon pada widget Cardview pada id daftar_icon
    }
}
