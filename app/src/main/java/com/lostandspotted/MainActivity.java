package com.lostandspotted;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_take_image).setOnClickListener(this);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:f8f9c2c2-eacd-4e2f-b00d-91ab80c23c0a", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            String url = sendToS3Bucket(bitmap);

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

    private String sendToS3Bucket(Bitmap bitmap) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
            String key = "pets/images/" + dateFormatter.format(new Date()) + ".jpg";

            File file = new File(this.getCacheDir(), "image");
            file.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapData = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();

            transferUtility.upload(
                    getString(R.string.aws_s3_bucket),
                    key,
                    file
            );

            return getString(R.string.aws_s3_base_url) + key;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
