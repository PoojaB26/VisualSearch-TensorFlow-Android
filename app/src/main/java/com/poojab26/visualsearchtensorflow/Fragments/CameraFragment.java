package com.poojab26.visualsearchtensorflow.Fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poojab26.visualsearchtensorflow.Classifier;
import com.poojab26.visualsearchtensorflow.R;
import com.poojab26.visualsearchtensorflow.TensorFlowImageClassifier;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class CameraFragment extends Fragment {

    private String topResult, secondResult = "all";
    private Float topResultConfidence, secondResultConfidence;

    private CameraView cameraView;
    private FloatingActionButton fabCamera;

    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";

    private static final String MODEL_FILE = "file:///android_asset/graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/labels.txt";

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();


    public CameraFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);
        cameraView = rootView.findViewById(R.id.cameraView);
        fabCamera = rootView.findViewById(R.id.fabClick);
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

                topResult = results.get(0).getTitle();
                topResultConfidence = results.get(0).getConfidence();
                Log.d("LOL first", topResult + topResultConfidence);

                int size = results.size()-1;
                if(size>=1) {
                    secondResult = results.get(1).getTitle();
                    secondResultConfidence = results.get(1).getConfidence();
                    Log.d("LOL second", secondResult + secondResultConfidence);

                    if(secondResultConfidence<0.5) {
                        secondResult = "all";
                    }
                }

                if(topResultConfidence<0.7) {
                    topResult = "none";
                }



                getActivity().getSupportFragmentManager().beginTransaction().remove(CameraFragment.this).commit();

                ProductListFragment productListFragment = new ProductListFragment();
                productListFragment.setTopResult(topResult);
                productListFragment.setSecondResult(secondResult);
                if(topResult.equalsIgnoreCase("none")) {
                    productListFragment.setSimilarItems(false);
                }
                else
                    productListFragment.setSimilarItems(true);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, productListFragment, null)
                        .commit();


            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });

        initTensorFlowAndLoadModel(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel(final View rootview) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    classifier =
                            TensorFlowImageClassifier.create(
                                    rootview.getContext().getAssets(),
                                    MODEL_FILE,
                                    LABEL_FILE,
                                    INPUT_SIZE,
                                    IMAGE_MEAN,
                                    IMAGE_STD,
                                    INPUT_NAME,
                                    OUTPUT_NAME);


                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }



}
