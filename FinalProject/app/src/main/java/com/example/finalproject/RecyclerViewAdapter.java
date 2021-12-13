package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder>{

    private LayoutInflater minflator;
    private MainActivity activity;
    private List<Charity> mData;
    Context context;

    public RecyclerViewAdapter(Context context, List<Charity> c,MainActivity activity) {
        this.context=context;
        this.mData=c;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = minflator.from(parent.getContext()).inflate(R.layout.child_view,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Charity c = mData.get(position);
        holder.text1.setText(String.valueOf(c.getTitle()));
        holder.text2.setText(String.valueOf(c.getAbout()));
        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startCharityEditActivity(c.getCharity_id());
            }
        });
        holder.donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startDonateActivity(c.getCharity_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        TextView text1;
        TextView text2;
        FloatingActionButton editbutton,donateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("viewholder constructor","on viewholder constructor");
            text1 = itemView.findViewById(R.id.textView1);
            text2 = itemView.findViewById(R.id.textView2);
            editbutton = itemView.findViewById(R.id.fab1);
            donateButton = itemView.findViewById(R.id.donateFab);
            if(activity.currentUser_id==-1)
            {
                editbutton.setVisibility(View.VISIBLE);
            }

        }

    }
}
