package jp.ne.sakura.haketrio.clipwb;

import java.util.ArrayList;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class WBDetect {
    private Mat mRgba;

    WBDetect( Bitmap bmp ){
        mRgba = Utils.bitmapToMat(bmp.copy(Config.ARGB_8888,true));
    }

    public boolean Detect(Bitmap dstBmp){
        //Mat dst = mRgba.clone();
        //Imgproc.GaussianBlur( mRgba, mRgba, new Size(21,21), 1.0 );

        ArrayList<Mat> contours = new ArrayList<Mat>();
        Mat hierarchy = new Mat();

        boolean b = Utils.matToBitmap(mRgba, dstBmp);
        return b;
    }
}
