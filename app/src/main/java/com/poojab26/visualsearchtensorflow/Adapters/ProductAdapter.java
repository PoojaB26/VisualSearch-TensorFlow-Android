package com.poojab26.visualsearchtensorflow.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poojab26.visualsearchtensorflow.Model.Image;
import com.poojab26.visualsearchtensorflow.Model.Product;
import com.poojab26.visualsearchtensorflow.Model.ProductLabel;
import com.poojab26.visualsearchtensorflow.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by poojab26 on 08-Apr-18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public ProductAdapter(List<Image> images, int label_index) {
        imagesList = images;
        indexLabel = label_index;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final List<Image> imagesList;
    private final int indexLabel;


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
        return imagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductImage;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProductImage = itemView.findViewById(R.id.ivProductImage);

        }

        public void bind(final int position) {
            String imgPath = imagesList.get(position).getImageUrl();
            if (!TextUtils.isEmpty(imgPath)) {
                Picasso.with(itemView.getContext())
                        .load(imgPath)
                        .into(ivProductImage);

            }
        }
    }
}
