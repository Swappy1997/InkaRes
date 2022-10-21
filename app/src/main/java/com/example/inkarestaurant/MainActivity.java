package com.example.inkarestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inkarestaurant.activity.ViewCart;
import com.example.inkarestaurant.adapters.ItemListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewCartCount {
    RecyclerView itemList;
    TextView menuBtn;
    ArrayList<Items> items;
    ArrayList<Items> itemsCartList;
    Context mcontext;
    TextView tittle, itemcount;
    int totatItemInCart = 0;
    int key = 0;
    JSONArray jsonArray;
    Items items1;
//    public static ArrayList<String> myVal = new ArrayList<String>() ;
//    public static ArrayList<Integer> count = new ArrayList<Integer>() ;


    int startercount, maincoursecount, dessertcount, drinkscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        itemList = findViewById(R.id.itemlist);
        menuBtn = findViewById(R.id.menu);
        tittle = findViewById(R.id.maintittle);
        itemcount = findViewById(R.id.itemcount);
        mcontext = this;
        //updatelist();
        setItemList("Starters");

        setMenuBtn();

        viewCart();
    }

    private void setMenuBtn() {
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.menulistwithitemcount, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
                wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                wmlp.x = 100;   //x position
                wmlp.y = 100;
                alertDialog.show();
                TextView starter = alertDialog.findViewById(R.id.starter);
                TextView startercount1 = alertDialog.findViewById(R.id.count1);
                TextView maincourse = alertDialog.findViewById(R.id.maincourse);
                TextView maincoursecount1 = alertDialog.findViewById(R.id.count2);
                TextView dessert = alertDialog.findViewById(R.id.dessert);
                TextView dessertcount1 = alertDialog.findViewById(R.id.count3);
                TextView drinks = alertDialog.findViewById(R.id.drinks);
                TextView drinkscount1 = alertDialog.findViewById(R.id.count4);


                startercount1.setText(String.valueOf(startercount));
                maincoursecount1.setText(String.valueOf(maincoursecount));
                dessertcount1.setText(String.valueOf(dessertcount));
                drinkscount1.setText(String.valueOf(drinkscount));
                String name = tittle.getText().toString();
                if (name.contains(getResources().getString(R.string.main_course))) {
                    maincourse.setTextColor(getResources().getColor(R.color.navyvlue));
                    maincourse.setTypeface(null, Typeface.BOLD);
                } else if (name.contains(getResources().getString(R.string.starter))) {
                    starter.setTextColor(getResources().getColor(R.color.navyvlue));
                    starter.setTypeface(null, Typeface.BOLD);
                } else if (name.contains(getResources().getString(R.string.dessert))) {
                    dessert.setTextColor(getResources().getColor(R.color.navyvlue));
                    dessert.setTypeface(null, Typeface.BOLD);
                } else if (name.contains(getResources().getString(R.string.drinks))) {
                    drinks.setTextColor(getResources().getColor(R.color.navyvlue));
                    drinks.setTypeface(null, Typeface.BOLD);
                }
                Log.d("", "onClickk: " + name);
                starter.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        tittle.setText(R.string.starter);
                        setItemList("Starters");
                        alertDialog.dismiss();
                    }
                });
                maincourse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tittle.setText(R.string.main_course);
                        setItemList("Main Course");
                        alertDialog.dismiss();
                    }
                });
                dessert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tittle.setText(R.string.dessert);
                        setItemList("Dessert");
                        alertDialog.dismiss();
                    }
                });
                drinks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tittle.setText(R.string.drinks);
                        setItemList("Drinks");
                        alertDialog.dismiss();
                    }
                });
            }
        });

    }

    private void setItemList(String menuname) {
        itemList.setNestedScrollingEnabled(true);
        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(new LinearLayoutManager(mcontext));
        items = new ArrayList<>();

        try {
            InputStream is = mcontext.getAssets().open("items.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String json = new String(buffer, "UTF-8");
            JSONObject jsonitemlist = new JSONObject(json);
            JSONArray menuList = jsonitemlist.optJSONArray("menu");
            JSONArray menuitemsname = null;
            Log.d("", "setItemList: " + menuList.length());


            for (int i = 0; i < menuList.length(); i++) {
                try {
                    JSONObject object = menuList.optJSONObject(i);
                    menuitemsname = object.getJSONArray(menuname);
                    Log.d("", "setItemList: " + menuitemsname);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < menuitemsname.length(); i++) {

                JSONObject object = menuitemsname.optJSONObject(i);

                items.add(new Items(object.optString("name"), object.optString("description"),
                        object.optInt("price"), object.optInt("quantity"), 0));

                Log.d("", "count: " + startercount + "drik" + dessertcount);
            }
            //for no of item count

            for (int i = 0; i < menuList.length(); i++) {
                try {
                    JSONObject object = menuList.optJSONObject(i);
                    dessertcount = object.getJSONArray("Dessert").length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < menuList.length(); i++) {
                try {
                    JSONObject object = menuList.optJSONObject(i);
                    startercount = object.getJSONArray("Starters").length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < menuList.length(); i++) {
                try {
                    JSONObject object = menuList.optJSONObject(i);
                    maincoursecount = object.getJSONArray("Main Course").length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < menuList.length(); i++) {
                try {
                    JSONObject object = menuList.optJSONObject(i);
                    drinkscount = object.getJSONArray("Drinks").length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if (key == 1) {
            itemsCartList = (ArrayList<Items>) getIntent().getSerializableExtra("items");
            items = (ArrayList<Items>) itemsCartList.clone();
        }
        ItemListAdapter itemListAdapter = new ItemListAdapter(items, mcontext, this, 0);

        itemList.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
        itemList.setAdapter(itemListAdapter);
        itemListAdapter.notifyDataSetChanged();
    }


    private void viewCart() throws NullPointerException {
        itemsCartList = new ArrayList<>();
        itemcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemsCartList != null && itemsCartList.size() <= 0) {
                    Toast.makeText(mcontext, "Please add some items", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(mcontext, ViewCart.class);
                    intent.putExtra("items", itemsCartList);
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public void onTOCart(Items items1) {
        if (itemsCartList == null) {
            itemsCartList = new ArrayList<>();
        }
        itemsCartList.add(items1);
        totatItemInCart = 0;
        for (Items i : itemsCartList) {
            totatItemInCart = totatItemInCart + i.getTotalInCart();
        }
        itemcount.setText("View Cart" + "(" + totatItemInCart + " items" + ")");
    }

    @Override
    public void OnUpdateButton(Items items1) {
        if (itemsCartList.contains(items1)) {
            int index = itemsCartList.indexOf(items1);
            itemsCartList.remove(index);
            itemsCartList.add(index, items1);
            totatItemInCart = 0;
            for (Items i : itemsCartList) {
                totatItemInCart = totatItemInCart + i.getTotalInCart();
            }
            itemcount.setText("View Cart" + "(" + totatItemInCart + " items" + ")");
        }
    }

    @Override
    public void onRemoveButton(Items items1) {
        if (itemsCartList.contains(items1)) {

            itemsCartList.remove(items1);
            totatItemInCart = 0;
            for (Items i : itemsCartList) {
                totatItemInCart = totatItemInCart + i.getTotalInCart();
            }
            itemcount.setText("View Cart" + "(" + totatItemInCart + " items" + ")");
        }
    }
}