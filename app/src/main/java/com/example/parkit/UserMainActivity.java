package com.example.parkit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserMainActivity extends AppCompatActivity {
    Button refresh;
    RecyclerView dataList;
    List<String> titles;
    List<Integer> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        dataList = findViewById(R.id.dataList);
        refresh = findViewById(R.id.refresh);


        // These are the array list to get names and status of parking lots
        titles = new ArrayList<>();
        images = new ArrayList<>();


        // Updates will be shown if refresh is pressed
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserMainActivity.this,"Refreshing...",Toast.LENGTH_SHORT).show();
                // Firebase data reference
                DatabaseReference parkingSlot = FirebaseDatabase.getInstance().getReference().child("Database").child("Parking Slots");
                parkingSlot.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        titles.clear();
                        images.clear();
                        if ( dataSnapshot.exists() ) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                titles.add(snapshot.getKey());
                                int flag = Integer.parseInt(Objects.requireNonNull(snapshot.getValue()).toString());

                                if (flag == 0)
                                    images.add(R.drawable.red);
                                else
                                    images.add(R.drawable.green);
                            }

                            getAdapter();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
            }
        });



    }

    // This function is used to pass names and flag to adapter
    private void getAdapter() {
        Adapter adapter = new Adapter(this, titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }
}