package com.poojab26.visualsearchtensorflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.poojab26.visualsearchtensorflow.Adapters.ProductAdapter;
import com.poojab26.visualsearchtensorflow.Model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;

/*    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rvProducts) RecyclerView rvProducts;*/

 RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        rvProducts = findViewById(R.id.rvProducts);
        //setSupportActionBar(toolbar);
//        toolbar.setTitle("Hello");
        setupRecyclerView();
}

    private void setupRecyclerView() {
        layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
        setupProductList();

    }

    private void setupProductList() {
        List<Product> products;
        rvProducts.setAdapter(new ProductAdapter());
    }

}
