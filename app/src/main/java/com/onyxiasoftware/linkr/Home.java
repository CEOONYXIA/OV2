package com.onyxiasoftware.linkr;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onyxiasoftware.linkr.Interface.ItemClickListener;
import com.onyxiasoftware.linkr.Model.Category;
import com.onyxiasoftware.linkr.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    FirebaseDatabase database;
    public DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    private EditText mSearchField;
    private ImageButton mSearchButton;
    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;
    private DatabaseReference searchResult;
    private long customerNumber;
    private String resultURL;

    public static String preferedName;


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        preferedName = "Ramon Tree Lawn";

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Customers");
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customerNumber = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchButton = (ImageButton) findViewById(R.id.search_button);
        mResultList = (RecyclerView) findViewById(R.id.result_list);

        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mResultList.getWindowToken(), 0);

                mSearchField.setFocusableInTouchMode(false);
                mSearchField.setFocusable(false);

                firebaseUserSearch(searchText);
            }
        });

        // Load home
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        mSearchButton.setEnabled(false);

        mSearchField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSearchButton.setEnabled(true);
                } else {
                    mSearchButton.setEnabled(false);
                }
            }
        });

        // loadmenu(companyName)
        loadMenu();
    }

    private void loadMenu() {

        mSearchField.setFocusableInTouchMode(true);
        mSearchField.setFocusable(true);

        // Default company reference
        category = database.getReference("Companies/" + preferedName + "/Category");

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menu_item, MenuViewHolder.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        if(position == 0)
                        {
                            Intent jobList = new Intent(Home.this, JobList.class);

                            // Because CategoryId is key, we just get key of this item
                            jobList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(jobList);
                        }
                        if(position == 1)
                        {
                            Intent workList = new Intent(Home.this, WorkList.class);

                            workList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(workList);
                        }
                        if(position == 2)
                        {
                            Intent reviewList = new Intent(Home.this, ReviewList.class);

                            reviewList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(reviewList);
                        }
                        if(position == 3)
                        {
                            Intent saleList = new Intent(Home.this, SaleList.class);

                            saleList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(saleList);
                        }
                        if(position == 4)
                        {
                            Intent foodList = new Intent(Home.this, Appointment.class);

                            // Because CategoryId is key, we just get key of this item
                            foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(foodList);
                        }
                        if(position == 5)
                        {
                            Intent foodList = new Intent(Home.this, About.class);

                            // Because CategoryId is key, we just get key of this item
                            foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(foodList);
                        }
                        // Get the category ID and send to new activity
                        //Intent foodList = new Intent(Home.this, FoodList.class);

                        // Because CategoryId is key, we just get key of this item
                        //foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        //startActivity(foodList);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    public String getPreferedName(){
        return preferedName;
    }

    private void firebaseUserSearch(String searchText){

        Toast.makeText(this, "Started Search...", Toast.LENGTH_SHORT).show();

        final Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<Customers, Home.PublicCompanyViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Customers, Home.PublicCompanyViewHolder>(
                Customers.class,
                R.layout.list_layout,
                Home.PublicCompanyViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(final Home.PublicCompanyViewHolder viewHolder, final Customers model, int position) {

                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getDescription(), model.getImage());
                mResultList.setAlpha(1);
                final String[] customerNames = new String[(int)customerNumber];;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        int[] positions = new int[(int)customerNumber];
                        for(int i =0; i < positions.length; i++){
                            positions[i] = i;
                        }
                        customerNames[0] = model.getName();

                        for(int i = 0; i < positions.length; i++){
                            if(position == positions[i]){
                                Toast.makeText(Home.this, customerNames[0], Toast.LENGTH_SHORT).show();
                                searchResult = FirebaseDatabase.getInstance().getReference("Customers/0" + Integer.toString(i+1));
                                searchResult.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        preferedName = customerNames[0];

                                        mSearchField.getText().clear();
                                        mResultList.setAdapter(null);

                                        loadMenu();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                });
            }
        };

        if(firebaseRecyclerAdapter.getItemCount() == 0){
            mSearchField.setFocusableInTouchMode(true);
            mSearchField.setFocusable(true);
        }
        mResultList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    public DatabaseReference getCategory(){
        return category;
    }

    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    public static class PublicCompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View mView;
        private ItemClickListener itemClickListener;

        public PublicCompanyViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setDetails(Context ctx, String userName, String userStatus, String userImage){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_status = (TextView) mView.findViewById(R.id.description_text);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);

            user_name.setText(userName);
            user_status.setText(userStatus);

            Glide.with(ctx).load(userImage).into(user_image);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
