package com.smartcerist.mobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.model.Camera;

import java.util.List;
import java.util.zip.Inflater;

public class CamerasCustomAdapter extends RecyclerView.Adapter<CamerasCustomAdapter.MyViewHolder>{

    private Context context;
    private List<Camera> camerasList;

    public CamerasCustomAdapter(Context context, List<Camera> camerasList) {
        this.context = context;
        this.camerasList = camerasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.camera_list_layout, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Camera camera = camerasList.get(position);

        holder.cameraName.setText(camera.getName());
        getCameraStream(camera.getSubStream(), holder.videoView, context);
        holder.toggleButton.setOnClickListener(v-> toggleCameraProcess());
    }

    private void getCameraStream(String url, VideoView videoView, Context context) {

        //add controls to a MediaPlayer like play, pause.
        MediaController mc = new MediaController(context);
        videoView.setMediaController(mc);
        //Set the path of Video or URI
        videoView.setVideoURI(Uri.parse(url));
        //Set the focus
        videoView.start();
    }

    private void toggleCameraProcess() {

    }

    @Override
    public int getItemCount() {
        return camerasList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cameraName;
        VideoView videoView;
        Button toggleButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            cameraName = itemView.findViewById(R.id.cameras_name);
            videoView = itemView.findViewById(R.id.videoView);
            toggleButton = itemView.findViewById(R.id.action_camera);
        }
    }
}
