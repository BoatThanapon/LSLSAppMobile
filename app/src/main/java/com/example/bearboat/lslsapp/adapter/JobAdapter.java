package com.example.bearboat.lslsapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.activity.MainActivity;
import com.example.bearboat.lslsapp.fragment.ShippingFragment;
import com.example.bearboat.lslsapp.model.Job;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private Context context;
    private List<Job> jobDaos;
    private ShippingFragment mFragment;
    private Bundle mBundle;

    private static final String TAG = "JobAdapter";

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

        viewHolder.tvTitle.setText(job.getStartingPointJob() + " -> " + job.getDestinationJob());
        viewHolder.tvSubTitle.setText("Shipping ID: " + job.getShippingId());
        viewHolder.tvTime.setText(job.getJobAssignmentDate());

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
        private TextView tvTitle, tvSubTitle, tvTime;

        public ViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tvTitle);
            tvSubTitle = view.findViewById(R.id.tvSubTitle);
            tvTime = view.findViewById(R.id.tvTime);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: " + getAdapterPosition());

            fragmentJump(getAdapterPosition());

//            LatLng origin = new LatLng(jobDaos.get(getAdapterPosition()).getLatitudeStartJob(),
//                    jobDaos.get(getAdapterPosition()).getLatitudeStartJob());
//
//            LatLng destination = new LatLng(jobDaos.get(getAdapterPosition()).getLatitudeDesJob(),
//                    jobDaos.get(getAdapterPosition()).getLongitudeDesJob());
//
//            GoogleDirection.withServerKey("AIzaSyBJX8xmIoID7XSDstbHieRweoU9ArTxvUo")
//                    .from(origin)
//                    .to(destination)
//                    .execute(new DirectionCallback() {
//                        @Override
//                        public void onDirectionSuccess(Direction direction, String rawBody) {
//
//                        }
//
//                        @Override
//                        public void onDirectionFailure(Throwable t) {
//
//                        }
//                    });
        }

        private void fragmentJump(int adapterPosition) {
            mFragment = new ShippingFragment();

            mBundle = new Bundle();
            mBundle.putSerializable("JOB_ASSIGNMENT", jobDaos.get(adapterPosition));
            mFragment.setArguments(mBundle);

            FragmentManager fragmentManager = ((MainActivity) (context)).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.contentContainer2, mFragment);

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}