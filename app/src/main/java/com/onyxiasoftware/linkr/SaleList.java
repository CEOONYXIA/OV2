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
import com.onyxiasoftware.linkr.Model.Sale;
import com.onyxiasoftware.linkr.ViewHolder.SaleViewHolder;
import com.squareup.picasso.Picasso;

public class SaleList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference treeList;

    String categoryId = "";
    String saleField;

    FirebaseRecyclerAdapter<Sale, SaleViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list);

        // Firebase initialization
        database = FirebaseDatabase.getInstance();
        saleField = Home.preferedName;
        treeList = database.getReference("Companies/" + saleField + "/Sale");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_sale);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        loadListSale(categoryId);
    }

    private void loadListSale(String categoryId){

        adapter = new FirebaseRecyclerAdapter<Sale, SaleViewHolder>(Sale.class, R.layout.menu_item, SaleViewHolder.class, treeList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(SaleViewHolder viewHolder, Sale model, int position) {
                viewHolder.sale_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.sale_image);

                final Sale local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent jobDetail = new Intent(SaleList.this, SaleDetail.class);
                        jobDetail.putExtra("SaleId", adapter.getRef(position).getKey());
                        startActivity(jobDetail);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
