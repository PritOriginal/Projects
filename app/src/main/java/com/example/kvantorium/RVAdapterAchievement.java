package com.example.kvantorium;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class RVAdapterAchievement extends RecyclerView.Adapter<RVAdapterAchievement.ViewHolder> {
    @NonNull
    List<Integer> achievements;
    ArrayList<Integer> image = new ArrayList<>();
    private Context mContext;
    RVAdapterAchievement (Context mContext, List<Integer> achievements){
        this.mContext = mContext;
        this.achievements = achievements;
        image.add(R.drawable.achivement);
        image.add(R.drawable.achivement);
    }
    @Override
    public RVAdapterAchievement.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.achievement_view, viewGroup, false);
        RVAdapterAchievement.ViewHolder pvh = new RVAdapterAchievement.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterAchievement.ViewHolder viewHolder, int i) {
        viewHolder.image.setImageResource(image.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            image = (ImageView) itemView.findViewById(R.id.imageAchievement);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }
}
