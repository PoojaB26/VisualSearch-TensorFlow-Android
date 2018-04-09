package com.poojab26.visualsearchtensorflow.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poojab26.visualsearchtensorflow.Adapters.ProductAdapter;
import com.poojab26.visualsearchtensorflow.Interface.RetrofitInterface;
import com.poojab26.visualsearchtensorflow.Model.Product;
import com.poojab26.visualsearchtensorflow.Model.Products;
import com.poojab26.visualsearchtensorflow.R;
import com.poojab26.visualsearchtensorflow.Utils.APIClient;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListFragment extends Fragment {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView rvProducts;
    public RetrofitInterface retrofitInterface;
    TextView tvProductCategory;
    String topResult = null;
    Boolean mSimilarItems = false;
    FloatingActionButton fabButtonOpenCamera;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        rvProducts = rootView.findViewById(R.id.rvProducts);
        tvProductCategory = rootView.findViewById(R.id.tvProductCategory);
        fabButtonOpenCamera = rootView.findViewById(R.id.btnDetectObject);
        fabButtonOpenCamera.setVisibility(View.VISIBLE);

        fabButtonOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraFragment cameraFragment = new CameraFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, cameraFragment)
                        .commit();

                fabButtonOpenCamera.setVisibility(View.GONE);
            }
        });
       // loadProductImage("f");
        setupRecyclerView();
        return rootView;
    }

    private void setupRecyclerView() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        rvProducts.setLayoutManager(layoutManager);
    }

    private void loadProductImage(final String topResultArg) {

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);

        Call<Products> call = retrofitInterface.getProductList();
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                int indexLabel = 0;

                Products products = response.body();
                Log.d("LOL Frag", topResultArg);
                ArrayList<Product> customProducts;
                customProducts = new ArrayList();
                for(int i=0; i<products.getProducts().size(); i++){
                    if (topResultArg.equalsIgnoreCase("all")){
                        customProducts = products.getProducts();
                        Log.d("LOL", i+"");
                        break;
                    }
                    else if(products.getProducts().get(i).getProductLabel().equalsIgnoreCase(topResultArg))
                    {
                        customProducts.add(products.getProducts().get(i));
                    }
                }
                rvProducts.setAdapter(new ProductAdapter(customProducts, topResultArg));
               /* final Product products = response.body();
                List<ProductLabel> productLabel = products.getProductLabel();
              //  Log.d("TAG", topResult);
                    for (int i = 0; i < productLabel.size(); i++) {

                        if (productLabel.get(i).getLabel().equalsIgnoreCase(topResultArg)) {
                            indexLabel = i;
                            List<Image> imagesList = productLabel.get(indexLabel).getImages();
                            rvProducts.setAdapter(new ProductAdapter(imagesList, indexLabel));
                        }
                    }*/

            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void setTopResult(String result) {
        topResult = result;
        loadProductImage(topResult);
    }

    public void forSimilarItems(Boolean similarItems){
        mSimilarItems = similarItems;
    }
}
