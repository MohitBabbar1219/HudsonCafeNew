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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

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

    public RecyclerViewAdapter(ArrayList<Dish> list, SQLiteDatabase db, String category) {
        this.dishes = list;
        this.db = db;
        this.category = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_list_card, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        ImageView image = cardView.findViewById(R.id.dish_img);
        image.setImageResource(R.drawable.appetizers_cottage_cheeze);
        TextView name = cardView.findViewById(R.id.dish_name);
        RelativeLayout layout = cardView.findViewById(R.id.cardColor);
        TextView rating = cardView.findViewById(R.id.dishRating);
        Button minusButt = cardView.findViewById(R.id.minusButt);
        Button plusButt = cardView.findViewById(R.id.plusButt);
        TextView price = cardView.findViewById(R.id.dishPrice);
        price.setText("Price: " + Integer.toString(dishes.get(position).getPrice()));
        final TextView qty = cardView.findViewById(R.id.dishQty);
        qty.setText(Integer.toString(dishes.get(position).getQuantity()));
        name.setText(dishes.get(position).getName());
        rating.setText(Float.toString(dishes.get(position).getRating()));
        if (dishes.get(position).getQuantity() > 0) {
            layout.setBackgroundColor(Color.parseColor("#52744c"));
        } else {
            layout.setBackgroundColor(Color.parseColor("#4c5f6b"));
        }
        minusButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtyInt = dishes.get(position).getQuantity();
                if (qtyInt != 0) {
//                    qty.setText(Integer.toString(qtyInt - 1));
                    dishes.get(position).setQuantity(qtyInt - 1);
                    if (qtyInt - 1 == 0) {
                        db.delete("CART_DISHES", "NAME = ?", new String[]{dishes.get(position).getName()});
                    } else {
                        updateAddedDish(db, dishes.get(position), dishes.get(position).getName());
                    }
                    notifyItemChanged(position);
//                    notifyDataSetChanged();
                }
            }
        });

        plusButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtyInt = dishes.get(position).getQuantity();
//                qty.setText(Integer.toString(qtyInt + 1));
                dishes.get(position).setQuantity(qtyInt + 1);
                Log.d("Bpit", dishes.get(position).getQuantity() + "");
                if (qtyInt == 0) {
                    insertAddedDish(db, dishes.get(position), category);
                } else {
                    updateAddedDish(db, dishes.get(position), dishes.get(position).getName());
                }
                notifyItemChanged(position);
//                notifyDataSetChanged();
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

    private void insertAddedDish(SQLiteDatabase db, Dish dish, String category) {
        ContentValues recordValues = new ContentValues();
        recordValues.put("QUANTITY", dish.getQuantity());
        recordValues.put("PRICE", dish.getPrice());
        recordValues.put("NAME", dish.getName());
        recordValues.put("CATEGORY", category);
        db.insert("CART_DISHES", null, recordValues);
    }

    private void updateAddedDish(SQLiteDatabase db, Dish dish, String name) {
        ContentValues recordValues = new ContentValues();
        recordValues.put("QUANTITY", dish.getQuantity());
        db.update("CART_DISHES", recordValues, "NAME = ?", new String[]{name});
    }

}
