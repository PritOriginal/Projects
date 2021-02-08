package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapterProjectsMini extends RecyclerView.Adapter<RVAdapterProjectsMini.ViewHolder> {
    @NonNull
    List<Project> projects;
    SQLiteDatabase db;
    private Context mContext;
    RVAdapterProjectsMini(Context mContext, List<Project> projects, SQLiteDatabase db) {
        this.projects = projects;
        this.db = db;
        this.mContext = mContext;
    }

    @Override
    public RVAdapterProjectsMini.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.project_mini_view, viewGroup, false);
        RVAdapterProjectsMini.ViewHolder pvh = new RVAdapterProjectsMini.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterProjectsMini.ViewHolder viewHolder, final int i) {
        viewHolder.projectName.setText(projects.get(i).getName());
        viewHolder.projectDescription.setText(projects.get(i).getDescription());
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mContext, ProjectActivity.class);
                intent.putExtra("id", projects.get(i).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView projectName;
        TextView projectDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            projectName = (TextView)itemView.findViewById(R.id.project_name);
            projectDescription = (TextView)itemView.findViewById(R.id.project_description);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
