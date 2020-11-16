package com.example.kvantorium;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kvantorium.server.DeleteObjective;
import com.example.kvantorium.server.SetCheckObjective;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RVAdapterObjective extends RecyclerView.Adapter<RVAdapterObjective.ViewHolder> {
    @NonNull
    List<Objective> objectives;
    private Context mContext;
    OnObjectiveListener mListener;
    boolean mentor;
    RVAdapterObjective (Context mContext, List<Objective> objectives, OnObjectiveListener mListener, boolean mentor){
        this.mContext = mContext;
        this.objectives = objectives;
        this.mListener = mListener;
        this.mentor = mentor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.objective_view, viewGroup, false);
        RVAdapterObjective.ViewHolder pvh = new RVAdapterObjective.ViewHolder(v);
        return pvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.objective.setText(objectives.get(i).getObjective());
        viewHolder.checkObjective.setChecked(objectives.get(i).getDone());
        if (viewHolder.checkObjective.isChecked()) {
            viewHolder.objective.setTextColor(mContext.getResources().getColor(R.color.text_checked, null));
        }
        if (!mentor) {
            viewHolder.checkObjective.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SetCheckObjective setCheckObjective;
                    if (viewHolder.checkObjective.isChecked()) {
                        viewHolder.objective.setTextColor(mContext.getResources().getColor(R.color.text_checked, null));
                        setCheckObjective = new SetCheckObjective(objectives.get(i).getId(), 1);
                    } else {
                        viewHolder.objective.setTextColor(mContext.getResources().getColor(R.color.text_unchecked, null));
                        setCheckObjective = new SetCheckObjective(objectives.get(i).getId(), 0);
                    }
                    setCheckObjective.execute();
                }
            });
            viewHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(mContext, v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.objective_menu, popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.action_edit_objective:
                                    DialogFragment dialogFragment = new ChangeObjectiveFragment(mListener, i, objectives.get(i));
                                    FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                                    dialogFragment.show(fragmentManager, TAG);
                                    return true;
                                case R.id.action_delete_objective:
                                    // Тут короче мне тоже пока лень делать диалог, поэтому пока что чисто так
                                    DeleteObjective deleteObjective = new DeleteObjective(objectives.get(i).getId());
                                    deleteObjective.execute();
                                    objectives.remove(i);
                                    notifyDataSetChanged();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });

                    return true;
                }
            });
            ;
        } else {
            viewHolder.cv.setClickable(false);
            viewHolder.checkObjective.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return objectives.size();
    }

    public ArrayList<Integer> getIdObjectives() {
        ArrayList<Integer> id_ = new ArrayList<Integer>();
        for (int position = 0; position < objectives.size(); position++) {
            id_.add(objectives.get(position).getId());
        }
        return id_;
    }

    public ArrayList<Integer> getChecks() {
        ArrayList<Integer> checks_ = new ArrayList<Integer>();
        for (int position = 0; position < objectives.size(); position++) {
            checks_.add(Integer.valueOf(String.valueOf(objectives.get(position).getDone())));
        }
        return checks_;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView objective;
        CheckBox  checkObjective;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            objective = (TextView)itemView.findViewById(R.id.objective);
            checkObjective = (CheckBox)itemView.findViewById(R.id.checkObjective);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setObjectives(List<Objective> objectives){
        this.objectives = objectives;
        notifyDataSetChanged();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mContext, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.objective_menu, popup.getMenu());
        popup.show();
    }

    public void addObjective(Objective o){
        objectives.add(o);
        notifyDataSetChanged();
    }
}
