package com.poojab26.visualsearchtensorflow.Interface;

import com.poojab26.visualsearchtensorflow.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by poojab26 on 07-Apr-18.
 */


public interface RetrofitInterface {
    @GET("product.json")
    Call<Product> getProductList();

}
