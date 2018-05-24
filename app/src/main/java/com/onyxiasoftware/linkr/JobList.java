package com.onyxiasoftware.linkr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onyxiasoftware.linkr.Interface.ItemClickListener;
import com.onyxiasoftware.linkr.Model.Job;
import com.onyxiasoftware.linkr.ViewHolder.JobViewHolder;
import com.squareup.picasso.Picasso;

public class JobList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference treeList;

    String categoryId = "";
    String jobField;

    FirebaseRecyclerAdapter<Job, JobViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        // Firebase initialization
        database = FirebaseDatabase.getInstance();
        jobField = Home.preferedName;
        treeList = database.getReference("Companies/" + jobField + "/Jobs");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_job);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        loadListJob(categoryId);
    }

    private void loadListJob(String categoryId){

        adapter = new FirebaseRecyclerAdapter<Job, JobViewHolder>(Job.class, R.layout.menu_item, JobViewHolder.class, treeList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(JobViewHolder viewHolder, Job model, int position) {
                viewHolder.job_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.job_image);

                final Job local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent jobDetail = new Intent(JobList.this, JobDetail.class);
                        jobDetail.putExtra("JobId", adapter.getRef(position).getKey());
                        startActivity(jobDetail);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
