package com.example.parkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    Integer count_green=0;
    Integer count_red=0;
    TextView green;
    TextView red;
    TextView total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        dataList = findViewById(R.id.dataList);
        refresh = findViewById(R.id.refresh);
        green = findViewById(R.id.number_of_green);
        red = findViewById(R.id.number_of_red);
        total = findViewById(R.id.total_count);


        // These are the array list to get names and status of parking lots
        titles = new ArrayList<>();
        images = new ArrayList<>();
        refresh.setPressed(true);
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

                                if (flag == 0){
                                    images.add(R.drawable.cardbgred);
                                    count_red=count_red+1;

                                }

                                else{

                                    images.add(R.drawable.cardbdgreen);
                                    count_green=count_green+1;
                                }

                            }

                            getAdapter();
                        }
                        green.setText(count_green.toString());
                        red.setText(count_red.toString());
                        Integer t = count_green+count_red;
                        total.setText(t.toString());
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }
}