package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kvantorium.server.DeleteProject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> implements OnConfirmListener {
    @NonNull
    List<Project> projects;
    ArrayList<Integer> image = new ArrayList<>();
    private Context mContext;
    boolean mentor_view;
    OnProjectsListener mListener;
    OnConfirmListener confirmListener = this;
    int index;
    RVAdapter(Context mContext, List<Project> projects, boolean mentor_view) {
        this.projects = projects;
        this.mContext = mContext;
        this.mentor_view = mentor_view;

        image.add(R.drawable.check);
        image.add(R.drawable.ic_projects);
    }
    RVAdapter(Context mContext, List<Project> projects, boolean mentor_view, OnProjectsListener mListener) {
        this.projects = projects;
        this.mContext = mContext;
        this.mentor_view = mentor_view;
        this.mListener = mListener;
        image.add(R.drawable.check);
        image.add(R.drawable.ic_projects);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_view, viewGroup, false);
        View v = LayoutInflater.from(mContext).inflate(R.layout.project_view, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.projectName.setText(projects.get(i).getName());
        //viewHolder.projectName.setId(i);
        viewHolder.projectDescription.setText(projects.get(i).getDescription());
        //viewHolder.projectDescription.setId(i);
        //viewHolder.completedImage.setVisibility(projects.get(i).isCompleted() == true ? View.VISIBLE : View.INVISIBLE);
        viewHolder.completedImage.setImageResource(projects.get(i).isCompleted() == true ? image.get(0) : image.get(1));
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onProjectCheck(projects.get(i), i);
            }
        });
        viewHolder.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.project_menu, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_edit:
                                Intent intent = new Intent(mContext, EditProject.class);
                                intent.putExtra("id", projects.get(i).getId());
                                mContext.startActivity(intent);
                                return true;
                            case R.id.action_delete_project:
                                index = i;
                                DialogFragment dialogFragment = new ConfirmFragment(confirmListener, 2);
                                FragmentManager fragmentManager =  ((FragmentActivity) mContext).getSupportFragmentManager();
                                dialogFragment.show(fragmentManager, TAG);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    @Override
    public void OnConfirmPositive() {

    }

    @Override
    public void OnConfirmNegative() {

    }

    @Override
    public void OnConfirmDelete() {
        DeleteProject deleteProject = new DeleteProject(projects.get(index).getId());
        deleteProject.execute();
        projects.remove(index);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView projectName;
        TextView projectDescription;
        ImageView completedImage;
        ImageView setting;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            projectName = (TextView)itemView.findViewById(R.id.project_name);
            projectDescription = (TextView)itemView.findViewById(R.id.project_description);
            completedImage = (ImageView)itemView.findViewById(R.id.completedProject);
            setting =(ImageView)itemView.findViewById(R.id.project_setting);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setProjects(List<Project> projects){
        this.projects = projects;
        notifyDataSetChanged();
    }
}