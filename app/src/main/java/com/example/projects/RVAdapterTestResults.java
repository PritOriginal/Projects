package com.example.projects;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapterTestResults extends RecyclerView.Adapter<RVAdapterTestResults.ViewHolder> {
    @NonNull
    List<Question> results;
    ArrayList<Integer> image = new ArrayList<>();
    private Context mContext;
    RVAdapterTestResults(Context mContext, List<Question> results) {
        this.mContext = mContext;
        this.results = results;
        image.add(R.drawable.ic_baseline_check_24);
        image.add(R.drawable.ic_baseline_clear_24);
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.question_result_view, viewGroup, false);
        RVAdapterTestResults.ViewHolder pvh = new RVAdapterTestResults.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.question_number.setText(results.get(i).getQuestion());
        viewHolder.correctImage.setImageResource(results.get(i).isCorrect() == true ? image.get(0) : image.get(1));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question_number;
        ImageView correctImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question_number = (TextView)itemView.findViewById(R.id.question_number);
            correctImage = (ImageView)itemView.findViewById(R.id.question_correct);
        }
    }
}
