package parkettklub.smartcheckroom.fragments;


import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;

import parkettklub.smartcheckroom.ItemHandlingActivity;
import parkettklub.smartcheckroom.R;
import parkettklub.smartcheckroom.barcode.CameraSourcePreview;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {

    private SurfaceView cameraView;
    private TextView barcodeInfo;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private CameraSourcePreview preview;

    private String lastBarcode;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View v = this.getView();

        barcodeInfo = (TextView)v.findViewById(R.id.code_info);
        preview = (CameraSourcePreview) v.findViewById(R.id.cameraSourcePreview);

        setupBarcodeDetector();
        setupCameraSource();
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                preview.start(cameraSource);
            } catch (IOException e) {
                cameraSource.release();
                cameraSource = null;
            }
        }
    }


    private void setupBarcodeDetector() {
        barcodeDetector = new BarcodeDetector.Builder(this.getContext())
                //.setBarcodeFormats(Barcode.QR_CODE)
                .build();

        lastBarcode = new String("");

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }


            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {

                    /* TODO: to be continued
                    Intent i = new Intent(getContext(), AboutActivity.class);
                    startActivity(i);o.n
                    */

                    lastBarcode = barcodes.valueAt(0).displayValue;

                    /*
                    ManageDB.getInstance().createNewDayItem(barCode);
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();
                    */
                    /*
                    CheckroomItemCreateFragment createFragment =
                            CheckroomItemCreateFragment.newInstance(barcodes.valueAt(0).displayValue, "meeh");
                    android.support.v4.app.FragmentManager fm = getFragmentManager();
                    createFragment.show(fm, CheckroomItemCreateFragment.TAG);
                    */

                    if(getUserVisibleHint()) {
                        Intent i = new Intent();
                        //i.setClass(getActivity(), ItemCreateActivity.class);
                        i.setClass(getActivity(), ItemHandlingActivity.class);
                        i.putExtra("KEY_BARCODE_NUMBER", barcodes.valueAt(0).displayValue);
                        startActivity(i);
                    }

                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {

                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                            Toast.makeText(getContext(), barcodes.valueAt(0).displayValue,Toast.LENGTH_LONG).show();
                        }
                    });

                    //Toast.makeText(getContext(),barcodes.valueAt(0).displayValue,Toast.LENGTH_LONG).show();

                    //playBeep();
                }
            }
        });

        if (!barcodeDetector.isOperational()) {
            Log.w("TAG_QR", "Detector dependencies are not yet available.");
        }

    }

    private void setupCameraSource() {
        cameraSource = new CameraSource.Builder(this.getContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(15.0f)
                .setRequestedPreviewSize(640, 640)
                .build();
    }

    private void playBeep() {
        ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }

    @Override
    public void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }
}
