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
import com.squareup.picasso.Picasso;

public class About extends AppCompatActivity {

    TextView about_name, about_description, about_phone;
    ImageView about_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    FirebaseDatabase database;
    DatabaseReference about;

    String aboutField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        database = FirebaseDatabase.getInstance();

        aboutField = Home.preferedName;
        about = database.getReference("Companies/" + aboutField + "/About");

        about_description = (TextView) findViewById(R.id.about_description);
        about_name = (TextView) findViewById(R.id.about_name);
        about_phone = (TextView) findViewById(R.id.about_phone);
        about_image = (ImageView) findViewById(R.id.img_about);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        about_name.setText(aboutField);

        about.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                about_phone.setText(dataSnapshot.child("Phone Number").getValue().toString());
                about_description.setText(dataSnapshot.child("Description").getValue().toString());

                Picasso.with(getBaseContext())
                        .load(dataSnapshot.child("Image").getValue().toString())
                        .into(about_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
