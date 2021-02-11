package com.example.projects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapterUsers2 extends RecyclerView.Adapter<RVAdapterUsers2.ViewHolder> {
    @NonNull
    List<User> users;
    private Context mContext;
    RVAdapterUsers2 (Context mContext, List<User> users) {
        this.users = users;
        this.mContext = mContext;
    }

    @Override
    public RVAdapterUsers2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_view, viewGroup, false);
        RVAdapterUsers2.ViewHolder pvh = new RVAdapterUsers2.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAdapterUsers2.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.name_teammate.setText(users.get(i).getSecondName() + " " + users.get(i).getFirstName());
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("id", users.get(i).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name_teammate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name_teammate = (TextView)itemView.findViewById(R.id.teammate_name);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
