package com.smartcerist.mobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Notification;

import java.util.List;

/**
 * Created by root on 17/05/18.
 */

public class NotificationsCustomAdapter extends RecyclerView.Adapter<NotificationsCustomAdapter.MyViewHolder>{
    private Context context;
    private List<Notification> notificationsList;

    public NotificationsCustomAdapter(Context context, List<Notification> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }


    public void refreshList(List<Notification> notifications){

        this.notificationsList.clear();
        this.notificationsList.addAll(notifications);
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public NotificationsCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_layout, parent, false);
        return new NotificationsCustomAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsCustomAdapter.MyViewHolder holder, int position) {
        final Notification notification = notificationsList.get(position);
        holder.date.setText(notification.getDate());
        holder.category.setText(notification.getCategory());
        holder.msg.setText(notification.getMessage());
        //DeviceService deviceService = new DeviceService(context);
        //String density = deviceService.getScreenDensity();
        //Glide.with(context).load(Consts.images_url+"drawable-"+density+"/"+house.getImage()).skipMemoryCache(true).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category;
        TextView date;
        TextView msg;

        MyViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.notification_date);
            category = itemView.findViewById(R.id.notification_category);
            msg = itemView.findViewById(R.id.notification_msg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isTwoPan()) {

                        if (notificationsList.size() == 0)
                            return;
                    } else {
                        Toast.makeText(context, "not two pan", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private boolean isTwoPan() {
            // View view = itemView.findViewById(R.id.details_fragment);
            // return view != null;
            return false;
        }
    }
}
