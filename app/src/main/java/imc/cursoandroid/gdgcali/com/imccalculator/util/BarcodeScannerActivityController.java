package imc.cursoandroid.gdgcali.com.imccalculator.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * Created by joseberna on 13/08/16.
 */
public class BarcodeScannerActivityController extends CaptureActivity {
    private static final String TAG = "BarcodeScanner";
    private static BarcodeScannerListener barcodeScannerListener;

    public interface BarcodeScannerListener {
        void onResult(IntentResult scanResult);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        try {
            if(BarcodeScannerActivityController.barcodeScannerListener != null) {
                new IntentIntegrator(this).initiateScan(); // `this` is the current Activity
            }
            //IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            //Intent intent = intentIntegrator.createScanIntent();
            //Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            //intent.putExtra("SCAN_FORMATS", "QR_CODE_MODE");
            //startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
        } catch (Exception e) {
//            Utilities.Log(TAG, e);
//            Utilities.Toast(this, ExceptionManager.parseExceptionMessage(e, this.getTitle().toString(), this));
        }
    }

    /**
     * Called when the barcode scanner intent completes.
     *
     * @param requestCode The request code originally supplied to startActivityForResult(),
     *                       allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param intent      An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            // Results
            IntentResult scanResult = IntentIntegrator.parseActivityResult( requestCode, resultCode, intent );
            if(BarcodeScannerActivityController.barcodeScannerListener != null) {
                BarcodeScannerActivityController.barcodeScannerListener.onResult(scanResult);
                finish();
            }

            // Make sure something was scanned
            /*Esto debe ser implementado en el metodo BarcodeScanner.BarcodeScannerListener.onResult
            if( scanResult != null ) {
                // Get the UPC from the scan results
                resultContents = scanResult.getContents();

                if( resultContents == null ) {
                    Log.i(TAG, "Scanning cancelled.");
                } else {
                    Log.i(TAG, resultContents );
                }
            }*/
        } catch (Exception e) {
//            Utilities.Log(TAG, e);
//            Utilities.Toast(this, ExceptionManager.parseExceptionMessage(e, this.getTitle().toString(), this));
        }
    }

    public static void startScanner(Activity activity, BarcodeScannerListener barcodeScannerListener) throws Exception {
        BarcodeScannerActivityController.barcodeScannerListener = null;

        if(barcodeScannerListener == null) {
            throw new IllegalArgumentException("The param barcodeScannerListener can't be null");
        }

        if(!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            throw new UnsupportedOperationException("The camera is not available");
        }

        BarcodeScannerActivityController.barcodeScannerListener = barcodeScannerListener;
        Intent intent = new Intent(activity, BarcodeScannerActivityController.class);
        activity.startActivity(intent);
    }

    public static void startScanner(Activity activity) throws Exception {
        BarcodeScannerActivityController.barcodeScannerListener = null;

        if(!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            throw new UnsupportedOperationException("The camera is not available");
        }

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setCaptureActivity(BarcodeScannerActivityController.class);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    public static void startScanner(Fragment fragment) throws Exception {
        BarcodeScannerActivityController.barcodeScannerListener = null;

        if(!fragment.getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            throw new UnsupportedOperationException("The camera is not available");
        }

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(fragment);
        integrator.setCaptureActivity(BarcodeScannerActivityController.class);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }
}
