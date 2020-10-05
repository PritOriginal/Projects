package com.example.kvantorium;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class RVAdapterTeammates extends RecyclerView.Adapter<RVAdapterTeammates.ViewHolder> {
    @NonNull
    List<Teammate> teammates;
    private Context mContext;
    RVAdapterTeammates (Context mContext, List<Teammate> teammates) {
        this.teammates = teammates;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.teammate_view, viewGroup, false);
        RVAdapterTeammates.ViewHolder pvh = new RVAdapterTeammates.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name_teammate.setText(teammates.get(i).getSecondName() + " " + teammates.get(i).getFirstName());
        viewHolder.role.setText(teammates.get(i).getRole());
    }

    @Override
    public int getItemCount() {
        return teammates.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name_teammate;
        TextView role;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name_teammate = (TextView)itemView.findViewById(R.id.teammate_name);
            role = (TextView)itemView.findViewById(R.id.teammate_role);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
