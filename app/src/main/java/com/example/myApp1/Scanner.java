package com.example.myApp1;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;





public class Scanner extends Fragment {


    SurfaceView surfaceView;
    TextView textView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.scanner,container,false);




        getPermissonsCamera();

        surfaceView=(SurfaceView)view.findViewById(R.id.surfaceView);
        textView=(TextView)view.findViewById(R.id.textView);

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource=new CameraSource.Builder(getActivity(),barcodeDetector)
                .setRequestedPreviewSize(300,300).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
                    @Override
                  public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                      if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                        !=PackageManager.PERMISSION_GRANTED)
                           return;
                        try{
                             cameraSource.start(surfaceHolder);
                        }catch (IOException e){
                           e.printStackTrace(); }
                        }

                    @Override
                      public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                         }

                    @Override
                     public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                      cameraSource.stop();
                   }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });


            return view;
        }




    public void getPermissonsCamera() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);

        }

    }
}