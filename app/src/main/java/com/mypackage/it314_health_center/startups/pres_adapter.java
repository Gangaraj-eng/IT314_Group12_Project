package com.mypackage.it314_health_center.startups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mypackage.it314_health_center.R;

import java.util.List;

public class pres_adapter extends RecyclerView.Adapter<pres_adapter.ViewHolder> {

    private List<prescription_class> pres_list;

    public pres_adapter (List<prescription_class>pres_list){this.pres_list=pres_list;}

    @NonNull
    @Override
    public pres_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new pres_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pres_adapter.ViewHolder holder, int position) {
        int resource = pres_list.get(position).getPres_id1();
        String date = pres_list.get(position).getPres_text_id1();
        String name = pres_list.get(position).getPres_text_id2();
        String line = pres_list.get(position).getDivider();

        holder.setData(resource, date, name, line);
    }

    @Override
    public int getItemCount() {
        return pres_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private TextView textView2;
        private TextView divider;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.pres_id1);
            textView = itemView.findViewById(R.id.pres_text_id1);
            textView = itemView.findViewById(R.id.pres_text_id2);
            textView = itemView.findViewById(R.id.divider);
        }

        public void setData(int resource, String date, String name, String line) {
            imageView.setImageResource(resource);
            textView.setText(date);
            textView2.setText(name);
            divider.setText(line);
        }
    }
}
