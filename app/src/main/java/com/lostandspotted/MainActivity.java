package com.lostandspotted;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.onecode.s3.model.S3BucketData;
import com.onecode.s3.model.S3Credentials;
import com.onecode.s3.service.S3UploadService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "lost_and_spotted";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_take_image).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_take_image) {
            startImageCaptureActivity();
        }
    }

    private void startImageCaptureActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void sendToS3Bucket(Bitmap bitmap) {
        try {
            File file = new File(this.getCacheDir(), "image");
            file.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapData = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();

            S3Credentials s3Credentials = new S3Credentials(
                    getString(R.string.aws_s3_key),
                    getString(R.string.aws_s3_secret),
                    sessionToken);

            S3BucketData s3BucketData = new S3BucketData.Builder()
                    .setCredentials(s3Credentials)
                    .setBucket(getString(R.string.aws_s3_bucket))
                    .setKey(file.getName())
                    .setRegion(getString(R.string.aws_s3_region))
                    .build();

            S3UploadService.upload(this.getApplicationContext(), s3BucketData, file, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
