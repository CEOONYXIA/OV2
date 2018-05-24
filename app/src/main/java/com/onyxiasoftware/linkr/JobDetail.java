package com.onyxiasoftware.linkr;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onyxiasoftware.linkr.Model.Job;
import com.squareup.picasso.Picasso;

public class JobDetail extends AppCompatActivity {

    TextView job_name, job_description;
    ImageView job_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String jobId = "";

    FirebaseDatabase database;
    DatabaseReference jobs;

    String jobsField;
    Job currentJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        database = FirebaseDatabase.getInstance();

        jobsField = Home.preferedName;
        jobs = database.getReference("Companies/" + jobsField + "/Jobs");

        job_description = (TextView) findViewById(R.id.job_description);
        job_name = (TextView) findViewById(R.id.job_name);
        job_image = (ImageView) findViewById(R.id.img_job);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent() != null){
            jobId = getIntent().getStringExtra("JobId");
        }

        //if (!treeId.isEmpty()){
        getDetailJob(jobId);
        //}
    }

    private void getDetailJob(String foodId) {

        jobs.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentJob = dataSnapshot.getValue(Job.class);

                Picasso.with(getBaseContext())
                        .load(currentJob.getImage())
                        .into(job_image);

                collapsingToolbarLayout.setTitle(currentJob.getName());

                job_name.setText(currentJob.getName());

                job_description.setText(currentJob.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
