package com.example.projects;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class RVAdapterTests extends RecyclerView.Adapter<RVAdapterTests.ViewHolder>  {
    @NonNull
    private Context mContext;
    List<Test> tests;
    ArrayList<Integer> image = new ArrayList<>();
    OnTestsListener mListener;
    RVAdapterTests(Context mContext, List<Test> tests, OnTestsListener mListener){
        this.mContext = mContext;
        this.tests = tests;
        this.mListener = mListener;

        image.add(R.drawable.check);
        image.add(R.drawable.ic_projects);
    }

    @Override
    public RVAdapterTests.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.test_view, viewGroup, false);
        RVAdapterTests.ViewHolder pvh = new RVAdapterTests.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterTests.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.testName.setText(tests.get(i).getName());
        viewHolder.completedImage.setImageResource(tests.get(i).isCompleted() == true ? image.get(0) : image.get(1));
        if(tests.get(i).isCompleted() == true) {
            viewHolder.progressBar.setProgress(tests.get(i).getProgress());
        } else {
            viewHolder.progressBar.setProgress(0);
        }
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTestCheck(tests.get(i), i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
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

    public void setTests(List<Test> tests) {
        this.tests = tests;
        notifyDataSetChanged();
    }
}
