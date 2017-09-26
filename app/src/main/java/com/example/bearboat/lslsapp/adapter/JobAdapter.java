package com.example.bearboat.lslsapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.model.Job;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private Context context;
    private List<Job> jobDaos;

    private static final String TAG = "VillageAdapter";

    public JobAdapter(List<Job> jobDaos, Context context) {
        this.jobDaos = jobDaos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Job job = jobDaos.get(position);

//        viewHolder.tvName.setText(job.getVillageName());
//        viewHolder.tvAddress.setText(job.getAddress());

    }

    @Override
    public int getItemCount() {
        return jobDaos == null ? 0
                : jobDaos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgVillage;
        private TextView tvAddress, tvName;

        public ViewHolder(View view) {
            super(view);

//            tvName = (TextView) view.findViewById(R.id.tvName);
//            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
//            imgVillage = (ImageView) view.findViewById(R.id.imgVillage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Log.i(TAG, "onClick: " + getAdapterPosition());
//            fragmentJump(getAdapterPosition());
        }

//        private void fragmentJump(int adapterPosition) {
//            mFragment = new ProfileFragment();
//
//            mBundle = new Bundle();
//            mBundle.putSerializable("VILLAGE", villageDaos.get(adapterPosition));
//            mFragment.setArguments(mBundle);
//
//            FragmentManager fragmentManager = ((MainActivity) (context)).getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.contentContainer, mFragment);
//
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }
    }
}