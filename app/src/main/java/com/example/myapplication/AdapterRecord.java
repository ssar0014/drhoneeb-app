package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord> {

    private Context context;
    private ArrayList<ModelRecord> recordsList;

    public AdapterRecord(Context context, ArrayList<ModelRecord> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_record, parent, false);

        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {
        //get data, set data, handel view clics in this method


        //get data
        ModelRecord model = recordsList.get(position);
        final String id = model.getId();
        String species = model.getSpecies();
        String health_condition = model.getHealth_condition();
        String description = model.getDescription();
        String image = model.getImage();
        String addedTime = model.getAddedTime();
        String updatedTime = model.getUpdatedTime();

        //set data to views
        holder.speciesTv.setText(species);
        holder.health_condition.setText(health_condition);
        holder.decription.setText(description);

        if (image.equals("null")) {
            //no image in record
            holder.beeIv.setImageResource(R.drawable.bee);
        }
        else {
            //have image in record
            holder.beeIv.setImageURI(Uri.parse(image));
        }


        //handle item clicks (go to detail record activity)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass record id to next activity to show details of that record
                Intent intent = new Intent(context, RecordDetailActivity.class);
                intent.putExtra("RECORD_ID", id);
                context.startActivity(intent);
            }
        });
        //handle more button click listener (show options like edit, delete et)
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will implement later
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder{

        ImageView beeIv;
        TextView speciesTv, health_condition, decription;
        ImageButton moreBtn;


        public HolderRecord(@NonNull View itemView) {
            super(itemView);

            beeIv = itemView.findViewById(R.id.bee_image);
            speciesTv = itemView.findViewById(R.id.speciesTv);
            health_condition = itemView.findViewById(R.id.healthConditionTv);
            decription = itemView.findViewById(R.id.descriptionTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
        }
    }
}





