package com.smartcerist.mobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.model.Rule;

import java.util.List;

public class RulesCustomAdapter extends RecyclerView.Adapter<RulesCustomAdapter.MyViewHolder> {

    private Context context;
    private List<Rule> rulesList;

    public RulesCustomAdapter(Context context, List<Rule> rulesList) {
        this.context = context;
        this.rulesList = rulesList;
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
        holder.status.setChecked(rule.getState());
        holder.rule.setText(rule.getDescription());
    }

    @Override
    public int getItemCount() {
        return rulesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox status;
        TextView rule;

        MyViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status_cb);
            rule = itemView.findViewById(R.id.rule_tv);
        }
    }
}
