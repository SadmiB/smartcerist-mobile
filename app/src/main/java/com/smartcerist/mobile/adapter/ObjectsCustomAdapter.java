package com.smartcerist.mobile.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.fragment.ObjectSettingsFragment;
import com.smartcerist.mobile.model.Object;
import com.smartcerist.mobile.model.ObjectsIcons;
import com.smartcerist.mobile.model.ObjectsTypes;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.reactivex.disposables.CompositeDisposable;



public class ObjectsCustomAdapter extends RecyclerView.Adapter<ObjectsCustomAdapter.MyViewHolder> {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);


    private Context context;
    private List<Object> objectsList;

    public ObjectsCustomAdapter(Context context, List<Object> objectsList, CompositeDisposable mDisposable) {
        this.context = context;
        this.objectsList = objectsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_list_layout,parent, false);
        return new MyViewHolder(viewItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Object object = objectsList.get(position);


        holder.object_icon.setBackgroundResource(getIcon(object.getType()));
        holder.object_name.setText(object.getName());

        if(object.getType().toString().equals(ObjectsTypes.led.toString()) || object.getType().toString().equals(ObjectsTypes.ventilator.toString())) {
            holder.action_btn.setVisibility(View.VISIBLE);
            //holder.action_btn.setOnClickListener(v -> toggleObjectStateProcess(object));
        }

        holder.toolbar.inflateMenu(R.menu.card_menu);
        holder.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.settings:
                    ObjectSettingsFragment fragment = new ObjectSettingsFragment();
                    fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), ObjectSettingsFragment.TAG);
                    break;
            }
            return false;
        });

        CoapGetTask task = new CoapGetTask(holder);
        String url = "coap://[" + object.getIpv6() +"]"+ object.getPath()+":5683";
        startTask.execute(task, url);

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

            case ventilator:
                objectIcon = ObjectsIcons.ventilator;
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
        Toolbar toolbar;


        MyViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            object_icon = itemView.findViewById(R.id.object_icon);
            object_name = itemView.findViewById(R.id.object_name);
            object_value = itemView.findViewById(R.id.object_value);
            action_btn = itemView.findViewById(R.id.action_btn);
            progressBar = itemView.findViewById(R.id.progressBar);
            toolbar = itemView.findViewById(R.id.toolbar);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION ) {
                    progressBar.setVisibility(View.VISIBLE);

                }
            });
        }
    }

    private void toggleObjectStateProcess(Object object) {

        String path = object.getPath();
        String value = (object.getMeasure().equals("1")) ? "0" : "1";

    }


    private void showSnackBarMessage(String message) {
        View parentLayout = ((Activity)context).findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message,Snackbar.LENGTH_SHORT).show();
    }



    @SuppressLint("StaticFieldLeak")
    class CoapGetTask extends AsyncTask<String,String, CoapResponse> {

        private MyViewHolder holder;

        CoapGetTask(MyViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected CoapResponse doInBackground(String... params) {
            CoapClient coapClient = new CoapClient(params[0]);
            return coapClient.get();
        }

        @Override
        protected void onPostExecute(CoapResponse coapResponse) {
            if(coapResponse!=null) {
                holder.object_value.setText(coapResponse.getResponseText());
                Log.d(holder.object_name.getText().toString(), "onPostExecute: " + coapResponse.advanced().getRTT()+"ms");
            }else{
                holder.object_value.setText(String.format("%s", "Disconnected"));
                showSnackBarMessage(holder.object_name.getText() + "is Disconnected");
            }
        }
    }

    static class startTask {
        public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
            execute(task, (P[]) null);
        }

        @SafeVarargs
        static <P, T extends AsyncTask<P, ?, ?>> void execute(T task, P... params) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }
    }
}
