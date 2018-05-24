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
import com.onyxiasoftware.linkr.Model.Work;
import com.squareup.picasso.Picasso;

public class WorkDetail extends AppCompatActivity {

    TextView work_name, work_description;
    ImageView work_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String workId = "";
    String workField;

    FirebaseDatabase database;
    DatabaseReference work;

    Work currentWork;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);

        database = FirebaseDatabase.getInstance();
        workField = Home.preferedName;
        work = database.getReference("Companies/" + workField + "/Work");

        work_description = (TextView) findViewById(R.id.work_description);
        work_name = (TextView) findViewById(R.id.work_name);
        work_image = (ImageView) findViewById(R.id.img_work);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if(getIntent() != null){
            workId = getIntent().getStringExtra("WorkId");
        }

        getDetailWork(workId);
    }

    private void getDetailWork(String workId){

        work.child(workId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentWork = dataSnapshot.getValue(Work.class);

                Picasso.with(getBaseContext())
                        .load(currentWork.getImage())
                        .into(work_image);

                collapsingToolbarLayout.setTitle(currentWork.getName());

                work_name.setText(currentWork.getName());

                work_description.setText(currentWork.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
