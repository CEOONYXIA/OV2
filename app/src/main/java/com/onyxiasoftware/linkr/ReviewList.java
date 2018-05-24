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
import com.onyxiasoftware.linkr.Model.Review;
import com.onyxiasoftware.linkr.ViewHolder.ReviewViewHolder;
import com.squareup.picasso.Picasso;

public class ReviewList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference treeList;

    String categoryId = "";
    String reviewField;

    FirebaseRecyclerAdapter<Review, ReviewViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        // Firebase initialization
        database = FirebaseDatabase.getInstance();
        reviewField = Home.preferedName;
        treeList = database.getReference("Companies/" + reviewField + "/Reviews");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_review);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        loadListReview(categoryId);
    }

    private void loadListReview(String categoryId){

        adapter = new FirebaseRecyclerAdapter<Review, ReviewViewHolder>(Review.class, R.layout.menu_item, ReviewViewHolder.class, treeList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(ReviewViewHolder viewHolder, Review model, int position) {
                viewHolder.review_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.review_image);

                final Review local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent jobDetail = new Intent(ReviewList.this, ReviewDetail.class);
                        jobDetail.putExtra("ReviewId", adapter.getRef(position).getKey());
                        startActivity(jobDetail);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
