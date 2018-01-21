package com.mydarkappfactory.hudsoncafe;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    android.support.v7.widget.Toolbar toolbar;
    DatabaseReference firebaseDb;
    CartRecyclerViewAdapter adapter;
    ArrayList<Dish> appetizers;
    SQLiteDatabase db;
    String email;
    ArrayList<FirebaseDish> dishesA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Societies");

        firebaseDb = FirebaseDatabase.getInstance().getReference();
        appetizers = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_cart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        SQLiteOpenHelper dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();


        firebaseDb.child("Menu").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<ArrayList<Dish>> t = new GenericTypeIndicator<ArrayList<Dish>>() {};
//                ArrayList<Dish> dishes = dataSnapshot.getValue(t);
                Cursor cursor = db.query("CART_DISHES", new String[]{"_id", "NAME", "QUANTITY", "PRICE"}, null, null, null, null, null);
                dishesA = new ArrayList<>();
                for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    // The Cursor is now set to the right position
                    dishesA.add(new FirebaseDish(cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                }
                cursor.close();
                for (DataSnapshot snapshot: dataSnapshot.child("Appetizers").getChildren()) {
                    boolean added = false;
                    for (FirebaseDish dish: dishesA) {
                        if (snapshot.getValue(Dish.class).getName().equals(dish.getName())) {
                            Dish dish1 = snapshot.getValue(Dish.class);
                            dish1.setQuantity(dish.getQuantity());
                            appetizers.add(dish1);
                            added = true;
                        }
                    }
                }
                for (DataSnapshot snapshot: dataSnapshot.child("Shakes").getChildren()) {
                    boolean added = false;
                    for (FirebaseDish dish: dishesA) {
                        if (snapshot.getValue(Dish.class).getName().equals(dish.getName())) {
                            Dish dish1 = snapshot.getValue(Dish.class);
                            dish1.setQuantity(dish.getQuantity());
                            appetizers.add(dish1);
                            added = true;
                        }
                    }
                }
                for (DataSnapshot snapshot: dataSnapshot.child("MainCourse").getChildren()) {
                    boolean added = false;
                    for (FirebaseDish dish: dishesA) {
                        if (snapshot.getValue(Dish.class).getName().equals(dish.getName())) {
                            Dish dish1 = snapshot.getValue(Dish.class);
                            dish1.setQuantity(dish.getQuantity());
                            appetizers.add(dish1);
                            added = true;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        adapter = new CartRecyclerViewAdapter(appetizers, db, "APPETIZER");
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        Cursor cursor = db.query("EMAIL_PASSWORD", new String[]{"EMAIL", "PASSWORD"},
                "_id = 1", null, null, null, null);
        cursor.moveToFirst();

        email = cursor.getString(0);


        cursor.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmOrder(View view) {

        firebaseDb.child("USERS").child(email.substring(0, email.indexOf('.'))).child("assignedTable").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int tableNumber = dataSnapshot.getValue(Integer.class);
                if (tableNumber == -1) {
                    Toast.makeText(CartActivity.this, "Please contact management for otp.", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseDb.child("tables").child(tableNumber + "").child("Dishes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<ArrayList<FirebaseDish>> t = new GenericTypeIndicator<ArrayList<FirebaseDish>>() {};
                            ArrayList<FirebaseDish> previousOrders = dataSnapshot.getValue(t);

                            for (FirebaseDish dish: dishesA) {
                                previousOrders.add(dish);
                            }
                            firebaseDb.child("tables").child(tableNumber + "").child("Dishes").setValue(previousOrders);

                            db.execSQL("delete from CART_DISHES");
                            startActivity(new Intent(CartActivity.this, SubmenuActivity.class));
                            finish();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
