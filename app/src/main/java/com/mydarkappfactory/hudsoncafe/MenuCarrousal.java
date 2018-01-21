package com.mydarkappfactory.hudsoncafe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.relex.circleindicator.CircleIndicator;

public class MenuCarrousal extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ViewPager viewPager;
    SlideshowAdapter adapter;
    CircleIndicator indicator;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        toolbar = findViewById(R.id.toolbar_menu_carrousal);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        //TODO
                        break;
                    case R.id.confirmedOrders:
                        //TODO
                        break;
                    case R.id.finalBill:
                        //TODO
                        break;
                    case R.id.signOut:
                        //TODO
                        break;
                }
                return true;
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager_menu);
        adapter = new SlideshowAdapter(MenuCarrousal.this);
        viewPager.setAdapter(adapter);

        indicator = findViewById(R.id.circle_indicator);
        indicator.setViewPager(viewPager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MenuCarrousal.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }


    //for menu of toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                Toast.makeText(this, "Castle", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuCarrousal.this, CartActivity.class);
                startActivity(intent);
                break;
            //This is executed when the top left corner back is pressed
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
