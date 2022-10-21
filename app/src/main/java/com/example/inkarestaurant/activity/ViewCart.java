package com.example.inkarestaurant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.inkarestaurant.Items;
import com.example.inkarestaurant.MainActivity;
import com.example.inkarestaurant.R;
import com.example.inkarestaurant.ViewCartCount;
import com.example.inkarestaurant.adapters.ItemListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewCart extends AppCompatActivity implements ViewCartCount {
    RecyclerView itemList;
    //    ArrayList<Items> items;
    ArrayList<Items> itemsCartList;
    Items items2;
    TextView totalCost, showall, back;
    Context mcontext;
    int totatItemInCart = 0;
    ViewCartCount viewCartCount = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart2);
        itemList = findViewById(R.id.itemlist);
        mcontext = this;
        totalCost = findViewById(R.id.totalcost);
        showall = findViewById(R.id.SHOW_ALL);
        back = findViewById(R.id.back);
        setItemlist();
        setBackBtn();
        calculateTotalAmount();
    }

    private void setBackBtn() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MainActivity.class);
                intent.putExtra("items", itemsCartList);
                startActivity(intent);
            }
        });
    }

    private void setItemlist() {

        itemList.setNestedScrollingEnabled(true);
        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(new LinearLayoutManager(mcontext));
        itemsCartList = (ArrayList<Items>) getIntent().getSerializableExtra("items");
        ItemListAdapter itemListAdapter;
        if (itemsCartList.size() <= 2) {
            itemListAdapter = new ItemListAdapter(itemsCartList, mcontext, this, 1, 2);
            itemList.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
            itemList.setAdapter(itemListAdapter);
            itemListAdapter.notifyDataSetChanged();
        } else if (itemsCartList.size() > 2) {
            itemListAdapter = new ItemListAdapter(itemsCartList, mcontext, this, 1, 2);
            itemList.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
            itemList.setAdapter(itemListAdapter);
            itemListAdapter.notifyDataSetChanged();
            showall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemListAdapter itemListAdapter = new ItemListAdapter(itemsCartList, mcontext, viewCartCount, 1);
                    itemList.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
                    itemList.setAdapter(itemListAdapter);
                    itemListAdapter.notifyDataSetChanged();
                    //show all btn
                    showall.setVisibility(View.GONE);
                }
            });
        }
        Log.d("TAG", "setItemlist: " + itemsCartList.size());

    }

    private void calculateTotalAmount() {
        float subtotal = 0f;
        for (Items i : itemsCartList) {
            subtotal += i.getPrice() * i.getTotalInCart();
        }
        totalCost.setText("$" + subtotal + "");
    }

    @Override
    public void onTOCart(Items items1) {
//        if (itemsCartList == null) {
//            itemsCartList = new ArrayList<>();
//        }
//        itemsCartList.add(items1);
//        totatItemInCart = 0;
//        for (Items i : itemsCartList) {
//            totatItemInCart = totatItemInCart + i.getTotalInCart();
//        }
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent=new Intent(mcontext,MainActivity.class);
//        intent.putExtra("items", itemsCartList);
//        intent.putExtra("key", 1);
//        startActivity(intent);
//    }


    @Override
    public void OnUpdateButton(Items items1) {
        if (itemsCartList.contains(items1)) {
            int index = itemsCartList.indexOf(items1);
            itemsCartList.remove(index);
            itemsCartList.add(index, items1);
            totatItemInCart = 0;
            float subtotal = 0f;

            for (Items i : itemsCartList) {
                totatItemInCart = totatItemInCart + i.getTotalInCart();
                subtotal += i.getPrice() * i.getTotalInCart();
                totalCost.setText("$" + subtotal + "");
            }
        }

    }

    @Override
    public void onRemoveButton(Items items1) {
        if (itemsCartList.contains(items1)) {

            itemsCartList.remove(items1);
            totatItemInCart = 0;
            float subtotal = 0f;

            for (Items i : itemsCartList) {
                totatItemInCart = totatItemInCart + i.getTotalInCart();
                subtotal += i.getPrice() * i.getTotalInCart();
                totalCost.setText("$" + subtotal + "");
            }
        }
    }
}
