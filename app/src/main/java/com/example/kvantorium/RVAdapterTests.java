package com.example.kvantorium;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class RVAdapterTests extends RecyclerView.Adapter<RVAdapterTests.ViewHolder>  {
    @NonNull
    private Context mContext;
    List<Test> tests;
    RVAdapterTests(Context mContext, List<Test> tests){
        this.mContext = mContext;
        this.tests = tests;
    }

    @Override
    public RVAdapterTests.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.test_view, viewGroup, false);
        RVAdapterTests.ViewHolder pvh = new RVAdapterTests.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterTests.ViewHolder viewHolder, int i) {
        viewHolder.testName.setText(tests.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView testName;
        ProgressBar progressBar;
        ImageView completedImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            testName = (TextView)itemView.findViewById(R.id.test_name);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBarTest);
            completedImage = (ImageView)itemView.findViewById(R.id.completedTest);
        }
    }

}
