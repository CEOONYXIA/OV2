package com.onyxiasoftware.linkr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onyxiasoftware.linkr.Common.Common;
import com.onyxiasoftware.linkr.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText editPhone, editName, editPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editPhone = (MaterialEditText) findViewById(R.id.editPhone);
        editName = (MaterialEditText) findViewById(R.id.editName);
        editPassword = (MaterialEditText) findViewById(R.id.editPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        // Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableUser = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Signing in...");
                mDialog.show();

                tableUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if there is already a user phone
                        if (dataSnapshot.child(editPhone.getText().toString()).exists()){

                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "That phone number already belongs to an existing account!", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            mDialog.dismiss();
                            User user = new User(editName.getText().toString(), editPassword.getText().toString());
                            tableUser.child(editPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUp.this, "Disregard Next Message!", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(SignUp.this, Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
