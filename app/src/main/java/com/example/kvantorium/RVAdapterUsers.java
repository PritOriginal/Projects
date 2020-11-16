package com.example.kvantorium;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RVAdapterUsers extends RecyclerView.Adapter<RVAdapterUsers.ViewHolder> {
    @NonNull
    List<User> users;
    private Context mContext;
    RVAdapterUsers (Context mContext, List<User> users) {
        this.users = users;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.teammate_view, viewGroup, false);
        RVAdapterUsers.ViewHolder pvh = new RVAdapterUsers.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name_teammate.setText(users.get(i).getSecondName() + " " + users.get(i).getFirstName());
        viewHolder.role.setText(users.get(i).getRole());
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new ProfileMiniFragment(users.get(i).getSecondName() + " " + users.get(i).getFirstName(), users.get(i).getVk());
                FragmentManager fragmentManager =  ((FragmentActivity) mContext).getSupportFragmentManager();
                dialogFragment.show(fragmentManager, TAG);
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
