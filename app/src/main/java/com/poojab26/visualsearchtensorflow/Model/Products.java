package com.poojab26.visualsearchtensorflow.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poojab26 on 09-Apr-18.
 */

public class Products {

    @SerializedName("products")
    @Expose
    private ArrayList<Product> products = null;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}