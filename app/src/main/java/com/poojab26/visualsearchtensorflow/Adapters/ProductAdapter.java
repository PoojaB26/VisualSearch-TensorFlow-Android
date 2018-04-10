package com.poojab26.visualsearchtensorflow.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.poojab26.visualsearchtensorflow.Model.Product;
import com.poojab26.visualsearchtensorflow.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poojab26 on 08-Apr-18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public ProductAdapter(ArrayList<Product> products, Boolean result) {
        productList = products;
        secondResult = result;
    }

    private final ArrayList<Product> productList;
    private final Boolean secondResult;


    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(!secondResult){
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_list, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.second_product_item_list, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {

            holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
        }

        public void bind(final int position) {
                String imgPath = productList.get(position).getProductUrl();
                if (!TextUtils.isEmpty(imgPath)) {
                    Picasso.with(itemView.getContext())
                            .load(imgPath)
                            .into(ivProductImage);

                }
        }
    }
}
