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

import org.pindad.jemuran.Cuaca.ListCuaca;
import org.pindad.jemuran.R;

import java.util.List;

public class CuacaAdapter extends RecyclerView.Adapter<CuacaAdapter.ViewHolder> {
    private List<ListCuaca> listItems;
    private Context mContext;

    public CuacaAdapter(Context context, List<ListCuaca> List) {
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
        holder.details_field.setText(listItems.get(position).getDate());
        holder.weather_icon.setText(listItems.get(position).getWeather());
        holder.current_temperature_field.setText(listItems.get(position).getTemperature());
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
        public TextView details_field, weather_icon, current_temperature_field;
        Typeface weatherFont;

        public ViewHolder(View itemView) {
            super(itemView);
            weatherFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/weathericons-regular-webfont.ttf");
            details_field = (TextView) itemView.findViewById(R.id.details_field);
            weather_icon = (TextView) itemView.findViewById(R.id.weather_icon);
            current_temperature_field = (TextView) itemView.findViewById(R.id.current_temperature_field);
            weather_icon.setTypeface(weatherFont);
        }
    }
}