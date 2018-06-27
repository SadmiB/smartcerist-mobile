package com.smartcerist.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.RoomActivity;
import com.smartcerist.mobile.activity.RoomsActivity;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Room;

import java.util.List;

public class RoomsCustomAdapter extends RecyclerView.Adapter<RoomsCustomAdapter.MyViewHolder> {


    private Context context;
    private List<Room> roomsList;


    public RoomsCustomAdapter(Context context, List<Room> roomsList) {
        this.context = context;
        this.roomsList = roomsList;
    }

    public void refreshList(List<Room> homes){

        this.roomsList.clear();
        this.roomsList.addAll(homes);
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Room room = roomsList.get(position);
        holder.name.setText(room.getName());
        //DeviceService deviceService = new DeviceService(context);
        //String density = deviceService.getScreenDensity();
        //Glide.with(context).load(Consts.images_url+"drawable-"+density+"/"+house.getImage()).skipMemoryCache(true).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        MyViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.room_icon);
            name = itemView.findViewById(R.id.room_name);

            itemView.setOnClickListener(v -> {
              Room  room = roomsList.get(getAdapterPosition());
              if(isTwoPan()) {
                  RoomsActivity activity = (RoomsActivity) context;
                  activity.setRoom(room);
                  Log.d("TwoPan", "onClick: TwoPan");
              }else {
                  Intent intent = new Intent(context, RoomActivity.class);
                  intent.putExtra("room", room);
                  context.startActivity(intent);
                  Log.d("NotTwoPan", "onClick: NotTwoPan");
              }

            });
        }
        private boolean isTwoPan() {
            View view = itemView.findViewById(R.id.the_room_fragment);
            return view != null;
        }
    }
}