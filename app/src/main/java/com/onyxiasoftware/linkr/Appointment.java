package com.onyxiasoftware.linkr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onyxiasoftware.linkr.Model.AppointmentObject;

public class Appointment extends AppCompatActivity {

    Button sendButton, clearButton;
    EditText appointmentName, appointmentAddress, appointmentPhone, appointmentInfo;

    // Declaring string variable in order to store firebase server url.
    public static final String Firebase_Server_URL = "https://linkr-2f5bf.firebaseio.com/";

    // Declaring string variables to hold stuff from edittext fields
    String nameHolder, addressHolder, phoneHolder, infoHolder;
    String appField;

    Firebase firebase;

    DatabaseReference databaseReference;

    // Root database name for firebase database
    public static final String Database_Path = "/Appointment_Details_Database/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Firebase.setAndroidContext(Appointment.this);
        firebase = new Firebase(Firebase_Server_URL);

        appField = Home.preferedName;
        databaseReference = FirebaseDatabase.getInstance().getReference("Companies/" + appField + Database_Path);

        sendButton = (Button) findViewById(R.id.btnAppointmentSend);
        clearButton = (Button) findViewById(R.id.btnAppointmentClear);

        appointmentName = (EditText) findViewById(R.id.appointment_name);
        appointmentAddress =(EditText) findViewById(R.id.appointment_address);
        appointmentPhone = (EditText) findViewById(R.id.appointment_phone);
        appointmentInfo = (EditText) findViewById(R.id.appointment_info);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentObject details = new AppointmentObject();

                getDataFromEditText();

                details.setAppointmentName(nameHolder);
                details.setAppointmentAddress(addressHolder);
                details.setAppointmentPhone(phoneHolder);
                details.setAppointmentInfo(infoHolder);

                String appointmentId = databaseReference.push().getKey();
                databaseReference.child(appointmentId).setValue(details);

                Toast.makeText(Appointment.this, "Your appointment has been successfuly reached!", Toast.LENGTH_LONG).show();

                clearEditText();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEditText();
            }
        });
    }

    public void getDataFromEditText(){
        nameHolder = appointmentName.getText().toString().trim();
        addressHolder = appointmentAddress.getText().toString().trim();
        phoneHolder = appointmentPhone.getText().toString().trim();
        infoHolder = appointmentInfo.getText().toString().trim();
    }

    public void clearEditText(){
        appointmentName.getText().clear();
        appointmentAddress.getText().clear();
        appointmentPhone.getText().clear();
        appointmentInfo.getText().clear();
    }
}
