package com.country.countries;




import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ahmadrosid.svgloader.SvgLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Countries> dataModelArrayList;
    private OnNoteList onNoteList;
    private Activity activity;

    public Adapter(Context ctx, Activity act, ArrayList<Countries> dataModelArrayList, OnNoteList onNoteList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.onNoteList = onNoteList;
        this.activity = act;
    }



    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view,onNoteList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {

      //  Picasso.with(context).load(dataModelArrayList.get(position).getFlag()).into(holder.iv_flag);

        SvgLoader.pluck()
                .with(activity)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(dataModelArrayList.get(position).getFlag(), holder.iv_flag);

        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.capital.setText("Capital : "+dataModelArrayList.get(position).getCapital());
        holder.region.setText("Region : " + dataModelArrayList.get(position).getRegion());
       // System.out.println("dataModelArrayList.get(position).getTitle() = " + dataModelArrayList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, capital, region;
        ImageView iv_flag;
        OnNoteList onNoteList;
        public MyViewHolder(@NonNull View itemView, OnNoteList onNoteList) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            capital = itemView.findViewById(R.id.tv_capital);
            region = itemView.findViewById(R.id.tv_region);
            iv_flag = itemView.findViewById(R.id.iv_flag);



            this.onNoteList = onNoteList;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onNoteList.OnnoteClick(dataModelArrayList.get(getAdapterPosition()));

        }
    }
    public interface OnNoteList {
        void OnnoteClick(Countries userClass);


    }
    public void filteredlist(ArrayList<Countries> filterlist){
        dataModelArrayList = filterlist;
        notifyDataSetChanged();
    }
}
