package com.smartcerist.mobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcerist.mobile.R;
import com.smartcerist.mobile.model.Object;
import com.smartcerist.mobile.model.ObjectsIcons;
import com.smartcerist.mobile.model.ObjectsTypes;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.util.NetworkUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ObjectsCustomAdapter extends RecyclerView.Adapter<ObjectsCustomAdapter.MyViewHolder> {

    private Context context;
    private List<Object> objectsList;

    private CompositeDisposable mCompositeDisposable;

    public ObjectsCustomAdapter(Context context, List<Object> objectsList, CompositeDisposable mDisposable) {
        this.context = context;
        this.objectsList = objectsList;
        this.mCompositeDisposable = mDisposable;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_list_layout,parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Object object = objectsList.get(position);
        holder.object_icon.setBackgroundResource(getIcon(object.getType()));
        holder.object_name.setText(object.getName());
        holder.object_value.setText(object.getMeasure());

        if(object.getType().toString().equals(ObjectsTypes.led.toString())) {
            holder.action_btn.setVisibility(View.VISIBLE);
            //holder.action_btn.setOnClickListener(v -> toggleObjectStateProcess(object));
        }
    }

    private int getIcon(ObjectsTypes objectType) {
        int objectIcon;
        switch (objectType){
            case led:
                objectIcon = ObjectsIcons.led;
            break;

            case light:
                objectIcon = ObjectsIcons.light;
            break;

            case power:
                objectIcon = ObjectsIcons.power;
            break;

            case temperature:
                objectIcon = ObjectsIcons.temperature;
            break;

            case presence:
                objectIcon = ObjectsIcons.presence;
            break;
            default:
                objectIcon = ObjectsIcons.sensor;
            break;
        }
        return objectIcon;
    }

    @Override
    public int getItemCount() {
        return objectsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView object_icon;
        TextView object_name;
        TextView object_value;
        Button action_btn;
        ProgressBar progressBar;


        MyViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            object_icon = itemView.findViewById(R.id.object_icon);
            object_name = itemView.findViewById(R.id.object_name);
            object_value = itemView.findViewById(R.id.object_value);
            action_btn = itemView.findViewById(R.id.action_btn);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION ) {
                    progressBar.setVisibility(View.VISIBLE);
                    getObjectMeasureProcess(position);
                }
            });



        }
    }

    private void toggleObjectStateProcess(Object object) {


        String path = object.getPath();
        String value = (object.getMeasure().equals("1")) ? "0" : "1";
        mCompositeDisposable.add(NetworkUtil.getRetrofit().toggleObjectState(path, value)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }




    private void getObjectMeasureProcess(int position) {
        Object object = objectsList.get(position);
        String path = object.getPath();
        mCompositeDisposable.add(NetworkUtil.getRetrofit().getObjectMeasure(path)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }


    private void handleResponse(String s) {

    }

    private void showSnackBarMessage(String message){
        //View parentLayout = findViewById(android.R.id.content);
        //Snackbar.make(parentLayout, message,Snackbar.LENGTH_SHORT).show();
    }

    private void handleError(Throwable error)
    {
        if (error instanceof HttpException) {

            try {

                String errorBody = Objects.requireNonNull(((HttpException) error).response().errorBody()).string();
                showSnackBarMessage(errorBody);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }
}
