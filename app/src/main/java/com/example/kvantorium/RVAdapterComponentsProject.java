package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kvantorium.server.DeleteComponentProject;
import com.example.kvantorium.server.DeleteProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RVAdapterComponentsProject extends RecyclerView.Adapter<RVAdapterComponentsProject.ViewHolder> {
    @NonNull
    List<Component> components;
    ArrayList<Integer> image = new ArrayList<>();
    DBHelper dbHelper;
    SQLiteDatabase database;
    private Context mContext;
    OnComponentsListener mListener;

    //int idProject;
    RVAdapterComponentsProject(Context mContext, List<Component> components, OnComponentsListener mListener) {
        this.components = components;
        Collections.sort(this.components, new Comparator<Component>() {

            @Override
            public int compare(Component o1, Component o2) {
                return o1.getNameComponent().compareTo(o2.getNameComponent());
            }
        });
        //this.idProject = idProject;
        this.mContext = mContext;
        this.mListener = mListener;

        //<<<<<=================== Типо картинки
        /*
        image = new ArrayList<>();
        image.add(R.drawable.arduino_uno);
        image.add(R.drawable.led_diode_5mm);
        image.add(R.drawable.intel_edison);
        image.add(R.drawable.joystick_overview);
        image.add(R.drawable.ph_sensor);
        image.add(R.drawable.raspberry_pi_3_model_b_plus);
        image.add(R.drawable.sealed_temperature_sensor_ds18b20_0);
        */
    }

    @Override
    public RVAdapterComponentsProject.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_view, viewGroup, false);
        View v = LayoutInflater.from(mContext).inflate(R.layout.component_project_view, viewGroup, false);
        RVAdapterComponentsProject.ViewHolder pvh = new RVAdapterComponentsProject.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAdapterComponentsProject.ViewHolder viewHolder, final int i) {
        viewHolder.componentName.setText(components.get(i).getNameComponent());
        String s = String.valueOf(components.get(i).getNumber());
        viewHolder.componentNumber.setText(s);
        if (components.get(i).getImage() != null) {
            int drawable = mContext.getResources().getIdentifier(components.get(i).getImage(), "drawable", mContext.getPackageName());
            viewHolder.image.setImageResource(drawable);
        }
        viewHolder.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.component_menu, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_edit_component:
                                DialogFragment dialogFragment = new ChangeNumberComponentProjectFragment(mContext , mListener, i, components.get(i));
                                FragmentManager fragmentManager =  ((FragmentActivity) mContext).getSupportFragmentManager();
                                dialogFragment.show(fragmentManager, TAG);
                                return true;
                            case R.id.action_delete_component:
                                DeleteComponentProject deleteComponentProject = new DeleteComponentProject(components.get(i).getId());
                                deleteComponentProject.execute();
                                components.remove(i);
                                notifyDataSetChanged();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ComponentsInfo.class);
                intent.putExtra("id", components.get(i).getId());
                intent.putExtra("project", true);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView image;
        TextView componentName;
        TextView componentNumber;
        ImageView setting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            image = (ImageView) itemView.findViewById(R.id.image_component);
            componentName = (TextView) itemView.findViewById(R.id.component_name_project);
            componentNumber = (TextView) itemView.findViewById(R.id.component_number_project);
            setting = (ImageView) itemView.findViewById(R.id.component_setting);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setComponents(List<Component> components) {
        this.components = components;
        notifyDataSetChanged();
    }
}
