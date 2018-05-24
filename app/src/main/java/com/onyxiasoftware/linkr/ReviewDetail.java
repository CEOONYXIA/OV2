package com.onyxiasoftware.linkr;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onyxiasoftware.linkr.Model.Review;
import com.squareup.picasso.Picasso;

public class ReviewDetail extends AppCompatActivity {

    TextView review_name, review_description;
    ImageView review_image;
    RatingBar ratingBar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String reviewId = "";
    String reviewField;

    FirebaseDatabase database;
    DatabaseReference reviews;

    Review currentReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        database = FirebaseDatabase.getInstance();
        reviewField = Home.preferedName;
        reviews = database.getReference("Companies/" + reviewField + "/Reviews");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        review_description = (TextView) findViewById(R.id.review_description);
        review_name = (TextView) findViewById(R.id.review_name);
        review_image = (ImageView) findViewById(R.id.img_review);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent() != null){
            reviewId = getIntent().getStringExtra("ReviewId");
        }

        //if (!treeId.isEmpty()){
        getDetailReview(reviewId);
        //}
    }

    private void getDetailReview(String reviewId){

        reviews.child(reviewId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentReview = dataSnapshot.getValue(Review.class);

                Picasso.with(getBaseContext())
                        .load(currentReview.getImage())
                        .into(review_image);

                collapsingToolbarLayout.setTitle(currentReview.getName());

                review_name.setText(currentReview.getName());

                review_description.setText(currentReview.getDescription());

                ratingBar.setNumStars(Integer.parseInt(currentReview.getRating()));
                ratingBar.setIsIndicator(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
