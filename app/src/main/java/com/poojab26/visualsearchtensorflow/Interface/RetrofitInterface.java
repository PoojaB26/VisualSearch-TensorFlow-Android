package com.poojab26.visualsearchtensorflow.Interface;

import com.poojab26.visualsearchtensorflow.Model.Product;
import com.poojab26.visualsearchtensorflow.Model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by poojab26 on 07-Apr-18.
 */


public interface RetrofitInterface {
    @GET("raw/1d86056892fca7bb3d00c0ab366e3415a8e71d89/product.json")
    Call<Products> getProductList();

}
