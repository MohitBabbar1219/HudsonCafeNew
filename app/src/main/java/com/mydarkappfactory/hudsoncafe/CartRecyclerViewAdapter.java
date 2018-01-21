package com.mydarkappfactory.hudsoncafe;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dragonslayer on 15/11/17.
 */

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    ArrayList<Dish> dishes;
    SQLiteDatabase db;
    String category;

    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public CartRecyclerViewAdapter(ArrayList<Dish> list, SQLiteDatabase db, String category) {
        this.dishes = list;
        this.db = db;
        this.category = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_dish_list_card, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        ImageView image = cardView.findViewById(R.id.dish_img);
        image.setImageResource(R.drawable.appetizers_cottage_cheeze);
        TextView name = cardView.findViewById(R.id.dish_name);
        TextView rating = cardView.findViewById(R.id.dishRating);
        TextView price = cardView.findViewById(R.id.dishPrice);
        price.setText("Total Price: " + Integer.toString(dishes.get(position).getPrice()) + "x" + Integer.toString(dishes.get(position).getQuantity()) + "=" + Integer.toString(dishes.get(position).getQuantity() * dishes.get(position).getPrice()));
        name.setText(dishes.get(position).getName());
        rating.setText(Float.toString(dishes.get(position).getRating()));
        Button deleteButt = cardView.findViewById(R.id.deleteDishIcon);
        deleteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("CART_DISHES", "NAME = ?", new String[]{dishes.get(position).getName()});
                dishes.remove(position);
                notifyDataSetChanged();
                //                    notifyDataSetChanged();
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }


}
