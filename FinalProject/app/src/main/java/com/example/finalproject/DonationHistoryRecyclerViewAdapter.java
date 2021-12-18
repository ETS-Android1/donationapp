package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DonationHistoryRecyclerViewAdapter extends RecyclerView.Adapter <DonationHistoryRecyclerViewAdapter.ViewHolder>{

    private LayoutInflater minflator;
    private DonationHistoryActivity activity;
    private List<DonationWithUserAndCharity> mData;
    Context context;

    public DonationHistoryRecyclerViewAdapter(Context context, List<DonationWithUserAndCharity> d, DonationHistoryActivity activity) {
        this.context=context;
        this.mData=d;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = minflator.from(parent.getContext()).inflate(R.layout.child_view2,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonationWithUserAndCharity d = mData.get(position);
        holder.charityTitle.setText( activity.db.getCharityDao().findById(d.donation.getCharity_id()).getTitle() );
        Donation temp = activity.db.getDonationDao().findById(d.donation.getDonation_id());
        holder.donationAmount.setText( temp.getAmount() );
        holder.donationDate.setText(temp.getDate());
        holder.donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startDonateActivity(d.donation.getDonation_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        TextView charityTitle,donationAmount,donationDate;
        FloatingActionButton donateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("viewholder constructor","on viewholder constructor");
            charityTitle = itemView.findViewById(R.id.charitynameTextView);
            donationAmount = itemView.findViewById(R.id.donationAmountTextView);
            donationDate = itemView.findViewById(R.id.dateTextView);
            donateButton = itemView.findViewById(R.id.donateFab2);
            if(activity.currentUser_id==-1)
            {
                donateButton.setVisibility(View.INVISIBLE);
            }

        }

    }
}
