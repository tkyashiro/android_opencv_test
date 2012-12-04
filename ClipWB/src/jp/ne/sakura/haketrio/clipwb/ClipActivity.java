package jp.ne.sakura.haketrio.clipwb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;

public class ClipActivity extends Activity {

    //========================================
    // íËêî
    private static final int INTENT_GALARY_REQUEST_CODE = 1;
    private static final int INTENT_CAMERA_REQUEST_CODE = 2;

    //========================================
    // GUI
    private Button mCameraButton;
    private Button mGalaryButton;
    private Button mLeftButton;
    private Button mRightButton;

    // âÊëúï\é¶
    private ImageView mImageView;
    private Bitmap mBitmap = null;

    //========================================
    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_clip, menu);
        return true;
    }

    private void setup(){
        mImageView = (ImageView)findViewById(R.id.img_clip);

        mGalaryButton = (Button)findViewById(R.id.btn_galary);
        mGalaryButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, INTENT_GALARY_REQUEST_CODE);
            }
        });

        mCameraButton = (Button)findViewById(R.id.btn_camera);
        mCameraButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v){
                //TODO
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, INTENT_CAMERA_REQUEST_CODE);
            }
        });

        mLeftButton = (Button)findViewById(R.id.btn_left);
        mLeftButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v){
                //TODO
            }
        });

        mRightButton = (Button)findViewById(R.id.btn_right);
        mRightButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v){
                //TODO
            }
        });
    }

    protected void onGalaryImage(Intent data){
        if( mBitmap != null ){
            mBitmap.recycle();
        }
        InputStream in = null;
        try{
            Uri uri = data.getData();
            in = getContentResolver().openInputStream(uri);
            mBitmap = BitmapFactory.decodeStream(in);

            int w = mBitmap.getWidth();
            int h = mBitmap.getHeight();
            Bitmap disp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            WBDetect detector = new WBDetect(mBitmap);
            boolean b = detector.Detect(disp);

            mImageView.setImageBitmap(disp);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch( Exception e ){
            e.printStackTrace();
        }finally{
            try{
                if( in != null ){ in.close(); }
            }catch( IOException e ){
                e.printStackTrace();
            }
        }
    }

    protected void onCameraImage(Intent data) {
        Bundle bundle = data.getExtras();
    }

    @Override
    protected void onActivityResult( int requestCode,
            int resultCode, Intent data){
        if( requestCode == INTENT_GALARY_REQUEST_CODE ){
            if( resultCode == RESULT_OK ){
                onGalaryImage(data);
            }
        }else if( requestCode == INTENT_CAMERA_REQUEST_CODE ){
            if( resultCode == RESULT_OK ){
                onCameraImage(data);
            }
        }
    }

}

