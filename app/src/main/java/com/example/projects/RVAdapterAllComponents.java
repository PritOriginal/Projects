package com.example.projects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RVAdapterAllComponents extends RecyclerView.Adapter<RVAdapterAllComponents.ViewHolder> {
    @NonNull
    List<Component> components;
    public ArrayList<Integer> count_use = new ArrayList<Integer>();
    private Context mContext;
    RVAdapterAllComponents (Context mContext, List<Component> components) {
        this.components = components;
        Collections.sort(this.components, new Comparator<Component>() {

            @Override
            public int compare(Component o1, Component o2) {
                return o1.getNameComponent().compareTo(o2.getNameComponent());
            }
        });
        this.mContext = mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.add_component_view, viewGroup, false);
        RVAdapterAllComponents.ViewHolder pvh = new RVAdapterAllComponents.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Component component = components.get(i);

        viewHolder.componentName.setText(component.getNameComponent());
        //viewHolder.componentName.setId(Integer.parseInt("2" + i));
        String s = String.valueOf(component.getNumber());
        viewHolder.lostNumber.setText(s);
        viewHolder.useNumber.setText(String.valueOf(component.getUseNumber()));
        if (components.get(i).getImage() != null) {
            int drawable = mContext.getResources().getIdentifier(components.get(i).getImage(), "drawable", mContext.getPackageName());
            viewHolder.image.setImageResource(drawable);
        }
        viewHolder.upNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(viewHolder.useNumber.getText().toString());
                viewHolder.useNumber.setText(String.valueOf(number + 1));
                components.get(i).setUseNumber(number + 1);
                component.setUseNumber(number + 1);
            }
        });
        viewHolder.downNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(viewHolder.useNumber.getText().toString());
                if (number > 0)
                    viewHolder.useNumber.setText(String.valueOf(number - 1));
                    components.get(i).setUseNumber(number - 1);
                    component.setUseNumber(number - 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return components.size();
    }


    public ArrayList<Integer> getCountUse() {
        for (int position = 0; position < components.size(); position++) {
            count_use.add(components.get(position).getUseNumber());
        }
        return count_use;
    }

    public ArrayList<Integer> getIdComponents() {
        ArrayList<Integer> id = new ArrayList<Integer>();
        for (int position = 0; position < components.size(); position++) {
            id.add(components.get(position).getId());
        }
        return id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView image;
        TextView componentName;
        TextView lostNumber;
        EditText useNumber;
        ImageButton upNumber;
        ImageButton downNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardViewAdd);
            image = (ImageView) itemView.findViewById(R.id.image_component);
            componentName = (TextView)itemView.findViewById(R.id.addComponent_name);
            lostNumber = (TextView)itemView.findViewById(R.id.lostNumber);
            useNumber = (EditText)itemView.findViewById(R.id.addComponent_number);
            upNumber = (ImageButton)itemView.findViewById(R.id.upNumberAdd);
            downNumber = (ImageButton)itemView.findViewById(R.id.downNumberAdd);
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
