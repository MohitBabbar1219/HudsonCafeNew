package com.mydarkappfactory.hudsoncafe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dragonslayer on 24/12/17.
 */

public class AppetizerFragment extends Fragment {

    View view;
    DatabaseReference firebaseDb;
    RecyclerViewAdapter adapter;
    static ArrayList<Dish> appetizers;
    SQLiteDatabase db;

    public AppetizerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_frag_appetizers, container, false);

        firebaseDb = FirebaseDatabase.getInstance().getReference();
        appetizers = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_appetizers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        SQLiteOpenHelper dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        firebaseDb.child("Menu").child("Appetizers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<ArrayList<Dish>> t = new GenericTypeIndicator<ArrayList<Dish>>() {};
//                ArrayList<Dish> dishes = dataSnapshot.getValue(t);
                Cursor cursor = db.query("CART_DISHES", new String[]{"_id", "NAME", "QUANTITY", "PRICE"}, "CATEGORY = ?", new String[]{"APPETIZER"}, null, null, null);
                ArrayList<FirebaseDish> dishesA = new ArrayList<>();
                for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    // The Cursor is now set to the right position
                    dishesA.add(new FirebaseDish(cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                }
                cursor.close();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    boolean added = false;
                    for (FirebaseDish dish: dishesA) {
                        if (snapshot.getValue(Dish.class).getName().equals(dish.getName())) {
                            Dish dish1 = snapshot.getValue(Dish.class);
                            dish1.setQuantity(dish.getQuantity());
                            appetizers.add(dish1);
                            added = true;
                        }
                    }
                    if (!added) {
                        appetizers.add(snapshot.getValue(Dish.class));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new RecyclerViewAdapter(appetizers, db, "APPETIZER");
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }
}

