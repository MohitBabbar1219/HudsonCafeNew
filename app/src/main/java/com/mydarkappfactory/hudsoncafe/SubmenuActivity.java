package com.mydarkappfactory.hudsoncafe;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SubmenuActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        int index = getIntent().getIntExtra("ImageIndex", -1);

        toolbar = findViewById(R.id.toolbar_submenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Societies");

        viewPager = findViewById(R.id.viewpager_submenu);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppetizerFragment(), "Appetizers");
        adapter.addFragment(new ShakesFragment(), "Shakes");
        adapter.addFragment(new MaincourseFragment(), "Main Course");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);

        tabLayout = findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(viewPager);

        SQLiteOpenHelper dbHelper = new DBHelper(SubmenuActivity.this);
        db = dbHelper.getReadableDatabase();

    }



    //for menu of toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //for menu of toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.toolbar_item_1:
                //TODO
                break;
            case R.id.toolbar_item_2:
                //TODO
                break;
            case R.id.castle_button:
                //TODO
//                db.execSQL("delete from CART_DISHES");
//                for (Dish dish: AppetizerFragment.appetizers) {
//                    if (dish.getQuantity() > 0) {
//                        insertAddedDish(db, dish, "APPETIZER");
//
//                    }
//                }
//                for (Dish dish: ShakesFragment.appetizers) {
//                    if (dish.getQuantity() > 0) {
//                        insertAddedDish(db, dish, "SHAKE");
//
//                    }
//                }
//                for (Dish dish: MaincourseFragment.appetizers) {
//                    if (dish.getQuantity() > 0) {
//                        insertAddedDish(db, dish, "MAIN_COURSE");
//
//                    }
//                }

                Intent intent = new Intent(SubmenuActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
                break;
            //This is executed when the top left corner back is pressed
            case android.R.id.home:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void insertAddedDish(SQLiteDatabase db, Dish dish, String category) {
        ContentValues recordValues = new ContentValues();
        recordValues.put("QUANTITY", dish.getQuantity());
        recordValues.put("NAME", dish.getName());
        recordValues.put("CATEGORY", category);
        db.insert("CART_DISHES", null, recordValues);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
