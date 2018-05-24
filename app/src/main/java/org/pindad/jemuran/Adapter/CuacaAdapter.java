package org.pindad.jemuran.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.pindad.jemuran.Cuaca.ListCuaca;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelForecast.ListHourly;
import org.pindad.jemuran.History.ModelHistory.ListHistory;
import org.pindad.jemuran.R;

import java.util.List;

public class CuacaAdapter extends RecyclerView.Adapter<CuacaAdapter.ViewHolder> {
    private List<ListHourly> listItems;
    private Context mContext;

    public CuacaAdapter(Context context, List<ListHourly> List) {
        mContext = context;
        listItems = List;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_forecast, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.details_field.setText(listItems.get(position).getTime());
        Glide.with(mContext)
                .load(listItems.get(position).getWeatherIconUrl().get(0).getValue())
                .into(holder.weather_icon);
        holder.current_temperature_field.setText(listItems.get(position).getTempC());
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
        public TextView details_field, current_temperature_field;
        public ImageView weather_icon;
        Typeface weatherFont;

        public ViewHolder(View itemView) {
            super(itemView);
            details_field = (TextView) itemView.findViewById(R.id.details_field);
            weather_icon = (ImageView) itemView.findViewById(R.id.weather_icon);
            current_temperature_field = (TextView) itemView.findViewById(R.id.current_temperature_field);
        }
    }
}