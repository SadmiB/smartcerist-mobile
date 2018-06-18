package com.smartcerist.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.RoomsActivity;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.ObjectEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by root on 24/05/18.
 */

public class HistoryCustomAdapter extends RecyclerView.Adapter<HistoryCustomAdapter.MyViewHolder>{
    private Context context;
    private List<ObjectEvent> eventsList;

    public HistoryCustomAdapter(Context context, List<ObjectEvent> eventsList){
        this.context = context;
        Collections.reverse(eventsList);
        this.eventsList = eventsList;
    }

    public void refreshList(List<ObjectEvent> events){
        this.eventsList.clear();
        this.eventsList.addAll(events);
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ObjectEvent event = eventsList.get(position);
        holder.date.setText(event.getDate());
        holder.message.setText(event.getMessage());
        //DeviceService deviceService = new DeviceService(context);
        //String density = deviceService.getScreenDensity();
        //Glide.with(context).load(Consts.images_url+"drawable-"+density+"/"+house.getImage()).skipMemoryCache(true).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView message;

        MyViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.event_date);
            message = itemView.findViewById(R.id.event_message);

        }
    }
}
