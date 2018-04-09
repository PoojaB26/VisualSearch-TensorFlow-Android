package com.poojab26.visualsearchtensorflow.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
    public ProductAdapter(ArrayList<Product> products, String result) {
        productList = products;
        topResult = result;
    }

    private final ArrayList<Product> productList;
    private final String topResult;


    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {

            holder.bind(position);

    }

    @Override
    public int getItemCount() {
        /*int size=0;
        for(int i=0; i<productList.size(); i++){
            if(productList.get(i).getProductLabel().equalsIgnoreCase(topResult))
                size++;
        }*/
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
        }

        public void bind(final int position) {
            //here it doesnt bind the image to the items that are not equal to topResult, but the items still display as blank empty boxes.
            // I dont want them to display at all.
          //  if(productList.get(position).getProductLabel().equals(topResult)) {
                String imgPath = productList.get(position).getProductUrl();
                if (!TextUtils.isEmpty(imgPath)) {
                    Picasso.with(itemView.getContext())
                            .load(imgPath)
                            .into(ivProductImage);

                }
         //   }
        }
    }
}
