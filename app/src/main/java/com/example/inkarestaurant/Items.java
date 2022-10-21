package com.example.inkarestaurant;

import java.io.Serializable;

public class Items implements Serializable {
    public Items(String tittle, String subtittle, int price,int quantity,int totalInCart) {
        Tittle = tittle;
        this.subtittle = subtittle;
        this.price = price;
        this.quantity=quantity;
        this.totalInCart=totalInCart;
    }

    String Tittle,subtittle;

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public String getSubtittle() {
        return subtittle;
    }

    public void setSubtittle(String subtittle) {
        this.subtittle = subtittle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    int price;

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    int totalInCart;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    int quantity;
}
