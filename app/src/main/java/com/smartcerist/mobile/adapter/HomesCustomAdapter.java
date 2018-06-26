package com.smartcerist.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.RoomsActivity;
import com.smartcerist.mobile.model.Home;

import java.util.List;

public class HomesCustomAdapter extends RecyclerView.Adapter<HomesCustomAdapter.MyViewHolder>{

    private Context context;
    private List<Home> homesList;

    public HomesCustomAdapter(Context context, List<Home> homesList) {
        this.context = context;
        this.homesList = homesList;
    }


    public void refreshList(List<Home> homes){

        this.homesList.clear();
        this.homesList.addAll(homes);
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Home house = homesList.get(position);
        holder.name.setText(house.getName());
        //DeviceService deviceService = new DeviceService(context);
        //String density = deviceService.getScreenDensity();
        //Glide.with(context).load(Consts.images_url+"drawable-"+density+"/"+house.getImage()).skipMemoryCache(true).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return homesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        MyViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.home_img);
            name = itemView.findViewById(R.id.home_name);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, RoomsActivity.class);
                intent.putExtra("home", homesList.get(getAdapterPosition()));
                context.startActivity(intent);
            });
        }
    }




}
