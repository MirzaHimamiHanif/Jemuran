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
    public TextView number, judul, awal, akhir;
    TextView tv2; //deklarasi textview

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        number = (TextView) itemView.findViewById(R.id.number);
        //menampilkan text dari widget CardView pada id daftar_judul
        judul = (TextView) itemView.findViewById(R.id.tanggal_jemuran);
        //menampilkan text deskripsi dari widget CardView pada id daftar_deskripsi
        awal = (TextView) itemView.findViewById(R.id.waktuMulai);
        akhir = (TextView) itemView.findViewById(R.id.waktuAkhir);
        //menampilkan gambar atau icon pada widget Cardview pada id daftar_icon
    }
}
