package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class RVAdapterComponents extends RecyclerView.Adapter<RVAdapterComponents.ViewHolder> {
    @NonNull
    List<Component> components;
    DBHelper dbHelper;
    SQLiteDatabase database;
    ArrayList<Integer> image = new ArrayList<>();
    private Context mContext;
    boolean project;
    int idProject;
    RVAdapterComponents(Context mContext, List<Component> components, boolean project) {
        this.components = components;
        Collections.sort(this.components, new Comparator<Component>() {

            @Override
            public int compare(Component o1, Component o2) {
                return o1.getNameComponent().compareTo(o2.getNameComponent());
            }
        });
        this.mContext = mContext;
        this.project = project;

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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_view, viewGroup, false);
        View v = LayoutInflater.from(mContext).inflate(R.layout.component_view, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAdapterComponents.ViewHolder viewHolder, final int i) {
        viewHolder.componentName.setText(components.get(i).getNameComponent());
        String s = String.valueOf(components.get(i).getNumber());
        viewHolder.componentNumber.setText(s);
        if (components.get(i).getImage() != null) {
            int drawable = mContext.getResources().getIdentifier(components.get(i).getImage(), "drawable", mContext.getPackageName());
            viewHolder.image.setImageResource(drawable);
        }
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ComponentsInfo.class);
                intent.putExtra("id", components.get(i).getId());
                intent.putExtra("project", project);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            image = (ImageView)itemView.findViewById(R.id.image_component2);
            componentName = (TextView)itemView.findViewById(R.id.component_name);
            componentNumber = (TextView) itemView.findViewById(R.id.component_number);
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
