package com.example.firebasedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<HomeroomClass> homeroomClasses;
    private DatabaseReference mDatabase;
    HomeroomClassAdapter homeroomClassAdapter;
    RecyclerView rvHomeroomClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeroomClasses = new ArrayList<HomeroomClass>();
        loadHomeRoomClasses();
        setContentView(R.layout.activity_main);
        homeroomClassAdapter = new HomeroomClassAdapter(homeroomClasses);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rvHomeroomClass = (RecyclerView) findViewById(R.id.rvHomeroomClass);

        // Initialize contacts
        // Create adapter passing in the sample user data
        HomeroomClassAdapter adapter = new HomeroomClassAdapter(homeroomClasses);
        // Attach the adapter to the recyclerview to populate items
        rvHomeroomClass.setAdapter(adapter);
        // Set layout manager to position the items
        rvHomeroomClass.setLayoutManager(new LinearLayoutManager(this));
        // That's all!


        final TextView className = (TextView) findViewById(R.id.tvClassNameEntry);
        final TextView classTeacherName = (TextView) findViewById(R.id.tvTeacherNameEntry);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        Button button = (Button) findViewById(R.id.btnAddContent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = className.getText().toString().trim();
                String teacher = classTeacherName.getText().toString().trim();

                //Creating Person object
                final HomeroomClass homeroomClass = new HomeroomClass(className.getText().toString(), classTeacherName.getText().toString());
                mDatabase.child("classes").child(name).setValue(homeroomClass)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "woohoo", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });



            }
        });
    }

    void insertClass(String className, String classTeacherName) {
        for (int i = 0; i < homeroomClasses.size(); i++) {
            if (homeroomClasses.get(i).getClassName() == className) {
                return;
            }
        }
        HomeroomClass homeroomClass = new HomeroomClass(className, classTeacherName);
        homeroomClasses.add(homeroomClass);
    }

    void loadHomeRoomClasses() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("classes");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        String className = (String) messageSnapshot.child("className").getValue().toString();
                        String classTeacherName = (String) messageSnapshot.child("classTeacherName").getValue().toString();
                        insertClass(className, classTeacherName);
                    }
                homeroomClassAdapter.notifyDataSetChanged();
                rvHomeroomClass.swapAdapter(homeroomClassAdapter, true);
                rvHomeroomClass.scrollBy(0,0);
            }






            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
