package org.pindad.jemuran.cuaca.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
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