package com.poojab26.visualsearchtensorflow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.poojab26.visualsearchtensorflow.Interface.RetrofitInterface;
import com.poojab26.visualsearchtensorflow.Model.Product;
import com.poojab26.visualsearchtensorflow.Model.ProductLabel;
import com.poojab26.visualsearchtensorflow.Utils.APIClient;
import com.squareup.picasso.Picasso;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // json = https://gist.githubusercontent.com/PoojaB26/07cd672876c885f21396313d07270db9/raw/2cdc14ed43641f7f9e67333b25f30e55058fbdcf/product.json

    public RetrofitInterface retrofitInterface;
    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";

    private static final String MODEL_FILE = "file:///android_asset/graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/labels.txt";

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    private FloatingActionButton btnDetectObject;
    private ImageView imageViewResult;
    private CameraView cameraView;

    public String topResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);
        imageViewResult = findViewById(R.id.imageViewResult);
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());

        btnDetectObject = findViewById(R.id.btnDetectObject);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                loadProductImage();
                Bitmap bitmap = cameraKitImage.getBitmap();

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
                topResult = results.get(0).getTitle();
                textViewResult.setText(topResult);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });


        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });

        initTensorFlowAndLoadModel();
    }

    private void loadProductImage() {

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);

        Call<Product> call = retrofitInterface.getProductList();
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                int indexLabel = 0;
                final Product products = response.body();
                Log.d("MAIN", products.toString());

                List<ProductLabel> productLabel = products.getProductLabel();
                for(int i=0; i<productLabel.size(); i++){
                    if(productLabel.get(i).getLabel().equalsIgnoreCase("white_shirts")){
                        indexLabel = i;
                    }
                }

                String URL = products.getProductLabel().get(indexLabel).getImages().get(1).getImageUrl();

                Picasso.
                        with(getApplicationContext())
                        .load(URL)
                        .resize(160, 200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageViewResult);


            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("Error", t.getMessage());


            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    classifier =
                            TensorFlowImageClassifier.create(
                                    getAssets(),
                                    MODEL_FILE,
                                    LABEL_FILE,
                                    INPUT_SIZE,
                                    IMAGE_MEAN,
                                    IMAGE_STD,
                                    INPUT_NAME,
                                    OUTPUT_NAME);


                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }
}
