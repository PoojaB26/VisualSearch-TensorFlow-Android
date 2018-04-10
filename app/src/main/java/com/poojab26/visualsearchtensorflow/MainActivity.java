package com.poojab26.visualsearchtensorflow;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.poojab26.visualsearchtensorflow.Fragments.ProductListFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());

        ProductListFragment productListFragment = new ProductListFragment();
        productListFragment.setTopResult("all");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, productListFragment)
                .commit();

    }


}
