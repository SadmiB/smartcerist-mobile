package com.smartcerist.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Rule;
import com.smartcerist.mobile.util.NetworkUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RulesCustomAdapter extends RecyclerView.Adapter<RulesCustomAdapter.MyViewHolder> {

    private Context context;
    private String homeId;
    private List<Rule> rulesList;
    private CompositeDisposable mSubscriptions;


    private void refreshList(List<Rule> rules){

        this.rulesList.clear();
        this.rulesList.addAll(rules);
        this.notifyDataSetChanged();

    }

    public RulesCustomAdapter(Context context, String homeId, List<Rule> rulesList) {
        this.context = context;
        this.homeId = homeId;
        this.rulesList = rulesList;
        this.mSubscriptions = new CompositeDisposable();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rule_list_layout, parent,false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Rule rule = rulesList.get(position);
        holder.name.setText(rule.getName());
        holder.status.setChecked(rule.getState());
        holder.rule.setText(rule.getDescription());

        holder.status.setOnCheckedChangeListener((buttonView, isChecked) -> {
            rule.setState(isChecked);
            updateRuleProcess(homeId, rule);
        });
    }

    @Override
    public int getItemCount() {
        return rulesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView rule;
        CheckBox status;

        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv);
            rule = itemView.findViewById(R.id.rule_tv);
            status = itemView.findViewById(R.id.status_cb);

            itemView.setOnClickListener(v -> {
                int position = this.getAdapterPosition();
                Rule rule = rulesList.get(position);
                rule.setState(!rule.getState());
                updateRuleProcess(homeId, rule);
            });
        }

        private void updateRule() {
            int position = this.getAdapterPosition();
            Rule rule = rulesList.get(position);
            rule.setState(!rule.getState());
            updateRuleProcess(homeId, rule);
        }
    }

    private void updateRuleProcess(String homeId, Rule rule) {

        mSubscriptions.add(NetworkUtil.getRetrofit().updateRule(homeId, rule.get_id(), rule)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(List<Rule> rules) {
        refreshList(rules);
    }

    private void handleError(Throwable error) {
        if (error instanceof HttpException) {

            try {

                String errorBody = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    errorBody = Objects.requireNonNull(((HttpException) error).response().errorBody()).string();
                }
                showSnackBarMessage(errorBody);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }



    private void showSnackBarMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        /*if (this.getView() != null) {
            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }*/
    }
}
