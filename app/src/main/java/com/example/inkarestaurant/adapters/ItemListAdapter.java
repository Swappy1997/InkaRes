package com.example.inkarestaurant.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inkarestaurant.Items;
import com.example.inkarestaurant.MainActivity;
import com.example.inkarestaurant.R;
import com.example.inkarestaurant.ViewCartCount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    ArrayList<Items> items;
    private Context mContext;
    int lastcountervalue, number;
    private ViewCartCount viewCartCount;
    int key = 1;
    private int limit = 2;


    public ItemListAdapter(ArrayList<Items> items, Context mcontext, ViewCartCount viewCartCount, int key) {
        this.mContext = mcontext;
        this.items = items;
        this.viewCartCount = viewCartCount;
        this.key = key;
        this.limit=items.size();
    }
    public ItemListAdapter(ArrayList<Items> items, Context mcontext, ViewCartCount viewCartCount, int key,int limit) {
        this.mContext = mcontext;
        this.items = items;
        this.viewCartCount = viewCartCount;
        this.key = key;
        this.limit=limit;
    }


    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ItemListViewHolder itemListViewHolder = new ItemListViewHolder(view);
        return itemListViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ItemListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (key == 0) {
            Items display_items = items.get(position);
            holder.main_tittle.setText(display_items.getTittle());
            holder.sub_tittle.setText(display_items.getSubtittle());
            Log.d("", "onBindViewHolder: " + position);

            holder.price.setText("$ "+display_items.getPrice());

            holder.counting.setText(String.valueOf(display_items.getQuantity()));


            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Items display_items = items.get(position);
                    display_items.setTotalInCart(1);
                    viewCartCount.onTOCart(display_items);
                    holder.countingLayout.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.GONE);
                    holder.counting.setText(display_items.getTotalInCart() + "");
                }
            });


            holder.addition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Items display_items = items.get(position);
                    int total = display_items.getTotalInCart();
                    total++;
                    if (total <= 20) {
                        display_items.setTotalInCart(total);
                        viewCartCount.OnUpdateButton(display_items);
                        holder.counting.setText(total + "");
                    }
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Items display_items = items.get(position);
                    int total = display_items.getTotalInCart();
                    total--;
                    if (total > 0) {
                        display_items.setTotalInCart(total);
                        viewCartCount.OnUpdateButton(display_items);
                        holder.counting.setText(total + "");
                    } else {
                        holder.countingLayout.setVisibility(View.GONE);
                        holder.add.setVisibility(View.VISIBLE);
                        display_items.setTotalInCart(total);
                        viewCartCount.onRemoveButton(display_items);
                    }

                }
            });
        } else if (key == 1) {

            Items display_items = items.get(position);
            holder.main_tittle.setText(display_items.getTittle());
            holder.sub_tittle.setText(display_items.getSubtittle());
            Log.d("", "onBindViewHolder: " + position);
            holder.price.setText("$ "+display_items.getPrice());
            holder.counting.setText(String.valueOf(display_items.getTotalInCart()));
            holder.countingLayout.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
            holder.addition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Items display_items = items.get(position);
                    int total = display_items.getTotalInCart();
                    total++;
                    if (total <= 20) {
                        display_items.setTotalInCart(total);
                        viewCartCount.OnUpdateButton(display_items);
                        holder.counting.setText(total + "");
                    }
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Items display_items = items.get(position);
                    int total = display_items.getTotalInCart();
                    total--;
                    if (total > 0) {
                        display_items.setTotalInCart(total);
                        viewCartCount.OnUpdateButton(display_items);
                        holder.counting.setText(total + "");
                    } else {
                        holder.countingLayout.setVisibility(View.GONE);
                        holder.add.setVisibility(View.VISIBLE);
                        display_items.setTotalInCart(total);
                        viewCartCount.onRemoveButton(display_items);
                    }

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return limit;
    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder {
        TextView main_tittle, sub_tittle, price, add, minus, addition, counting;
        LinearLayout countingLayout;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            main_tittle = itemView.findViewById(R.id.maintittle);
            sub_tittle = itemView.findViewById(R.id.subtittle);
            price = itemView.findViewById(R.id.price);
            add = itemView.findViewById(R.id.addbtn);
            minus = itemView.findViewById(R.id.minus);
            addition = itemView.findViewById(R.id.addition);
            counting = itemView.findViewById(R.id.counting);
            countingLayout = itemView.findViewById(R.id.countlayot);

        }
    }
}
