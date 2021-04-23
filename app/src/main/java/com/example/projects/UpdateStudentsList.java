package com.example.projects;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projects.server.GetTimeGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UpdateStudentsList extends AsyncTask<String, Integer, ArrayList<View>> {
    Context mContext;

    ProgressBar progressBar;
    private OnUpdateStudents mListener;
    List<User> users = new ArrayList<User>();
    RVAdapterUsers2 adapter;
    LinearLayout linearLayout;
    ArrayList<View> students = new ArrayList<View>();

    public UpdateStudentsList(Context mContext, OnUpdateStudents mListener,List<User> users, LinearLayout linearLayout, ProgressBar progressBar) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.users = users;
        this.linearLayout = linearLayout;
        this.progressBar = progressBar;
    }

    @Override
    protected ArrayList<View> doInBackground(String... strings) {
        sorting();
        return students;
    }
    public void sorting() {
        final ArrayList<Integer> group = new ArrayList<Integer>();
        ArrayList<ArrayList<User>> users_group = new ArrayList<ArrayList<User>>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getGroup() != 0) {
                int j = 0;
                while (j < group.size()) {
                    if (group.get(j) == users.get(i).getGroup()) {
                        users_group.get(j).add(users.get(i));
                        break;
                    }
                    else if (j == group.size() - 1) {
                        group.add(users.get(i).getGroup());
                        ArrayList<User> users_ = new ArrayList<User>();
                        users_.add(users.get(i));
                        users_group.add(users_);
                        break;
                    }
                    j++;
                }
                if (group.size() == 0) {
                    group.add(users.get(i).getGroup());
                    ArrayList<User> users_ = new ArrayList<User>();
                    users_.add(users.get(i));
                    users_group.add(users_);
                }
            }
        }
        ArrayList<ArrayList<User>> users_group_ = new ArrayList<ArrayList<User>>();
        int u = 0;
        for (int i = 0; i < users_group.size(); i++) {
            int max = Collections.max(group);
            while (u < max){
                if (group.indexOf(u+1) > -1) {
                    users_group_.add(users_group.get(group.indexOf(u+1)));
                    u++;
                    break;
                }
                u++;
            }
            Collections.sort(users_group_.get(i), new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getSecondName().compareTo(o2.getSecondName());
                }
            });
        }
        Collections.sort(group);
        update(group, users_group_);
    }

    public void update(final ArrayList<Integer> group, ArrayList<ArrayList<User>> users_group) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < users_group.size(); i++) {
            final View v1 = inflater.inflate(R.layout.group_list, null);
            TextView t1 = (TextView) v1.findViewById(R.id.name_group);
            t1.setText("Группа " + group.get(i));

            final RecyclerView groupList = (RecyclerView) v1.findViewById(R.id.group_list);
            LinearLayoutManager llm = new LinearLayoutManager(mContext.getApplicationContext());
            groupList.setLayoutManager(llm);

            adapter = new RVAdapterUsers2(mContext, users_group.get(i));
            groupList.setAdapter(adapter);
            students.add(v1);

            final ImageView group_open = (ImageView)v1.findViewById(R.id.group_open);
            LinearLayout title_group = (LinearLayout)v1.findViewById(R.id.title_group);
            title_group.setOnClickListener(new View.OnClickListener() {
                boolean visible = false;
                @Override
                public void onClick(View v) {
                    if (visible) {
                        groupList.setVisibility(View.GONE);
                        group_open.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        visible = !visible;
                    } else {
                        groupList.setVisibility(View.VISIBLE);
                        group_open.setImageResource(R.drawable.ic_expand_less_black_24dp);
                        visible = !visible;
                    }
                }
            });

            TextView people_count_group = (TextView)v1.findViewById(R.id.people_count_group);
            people_count_group.setText(String.valueOf(adapter.getItemCount()) + " чел.");

            TextView time_group = (TextView)v1.findViewById(R.id.time_group);
            TextView day_week_group = (TextView)v1.findViewById(R.id.day_week_group);
            GetTimeGroup getTimeGroup = new GetTimeGroup(mContext, group.get(i));
            getTimeGroup.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            try {
                ArrayList<String> time = getTimeGroup.get();
                time_group.setText(time.get(0));
                day_week_group.setText(time.get(1));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.progressBar != null) {
            progressBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<View> students) {
        if (mListener != null) {
            mListener.onUpdateStudents(students);
        }
    }
}
