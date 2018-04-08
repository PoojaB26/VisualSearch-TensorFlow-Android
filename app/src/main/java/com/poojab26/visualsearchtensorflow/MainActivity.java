package com.poojab26.visualsearchtensorflow;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.poojab26.visualsearchtensorflow.Fragments.ProductListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProductListFragment productListFragment = new ProductListFragment();
        productListFragment.setTopResult("white_shirts");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, productListFragment)
                .commit();

    }


}
