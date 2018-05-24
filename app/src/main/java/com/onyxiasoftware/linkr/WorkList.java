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
import com.onyxiasoftware.linkr.Model.Work;
import com.onyxiasoftware.linkr.ViewHolder.WorkViewHolder;
import com.squareup.picasso.Picasso;

public class WorkList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference workList;

    String categoryId = "";
    String workField;

    FirebaseRecyclerAdapter<Work, WorkViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);

        // Firebase initialization
        database = FirebaseDatabase.getInstance();
        workField = Home.preferedName;
        workList = database.getReference("Companies/" + workField + "/Work");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_work);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        loadListWork(categoryId);
    }

    private void loadListWork(String categoryId){
        adapter = new FirebaseRecyclerAdapter<Work, WorkViewHolder>(Work.class, R.layout.menu_item, WorkViewHolder.class, workList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(WorkViewHolder viewHolder, Work model, int position) {
                viewHolder.work_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.work_image);

                final Work local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent workDetail = new Intent(WorkList.this, WorkDetail.class);
                        workDetail.putExtra("WorkId", adapter.getRef(position).getKey());
                        startActivity(workDetail);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
