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
import com.onyxiasoftware.linkr.Model.Sale;
import com.squareup.picasso.Picasso;

public class SaleDetail extends AppCompatActivity {

    TextView sale_name, sale_description;
    ImageView sale_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String saleId = "";
    String saleField;

    FirebaseDatabase database;
    DatabaseReference sales;

    Sale currentSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        database = FirebaseDatabase.getInstance();
        saleField = Home.preferedName;
        sales = database.getReference("Companies/" + saleField + "/Sale");

        sale_description = (TextView) findViewById(R.id.sale_description);
        sale_name = (TextView) findViewById(R.id.sale_name);
        sale_image = (ImageView) findViewById(R.id.img_sale);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent() != null){
            saleId = getIntent().getStringExtra("SaleId");
        }

        //if (!treeId.isEmpty()){
        getDetailSale(saleId);
        //}
    }

    private void getDetailSale(String saleId){

        sales.child(saleId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentSale = dataSnapshot.getValue(Sale.class);

                Picasso.with(getBaseContext())
                        .load(currentSale.getImage())
                        .into(sale_image);

                collapsingToolbarLayout.setTitle(currentSale.getName());

                sale_name.setText(currentSale.getName());

                sale_description.setText(currentSale.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
