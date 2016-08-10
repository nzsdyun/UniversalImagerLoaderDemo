package com.example.chenkun.universalimagerloaderdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String mLoadImageUrl = "http://www.bz55.com/uploads1/allimg/120312/1_120312100435_8.jpg";
    private String mLoadImageDrawable = "drawable://" + R.drawable.default_wallpaper_copy;
    private String mLoadImageAssets = "assets://list_checkmark.png";
    private String mLoadImageContentProvider = "content://media/external/video/media/13";
    private String mLoadImageFile = "file:///mnt/sdcard/image.png"; // from sd card
    private ImageView mImageView;
    private ProgressBar mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
        mProgress = (ProgressBar) findViewById(R.id.progress);
    }

    public void Click(View v) {
        switch (v.getId()) {
            case R.id.load:
//                testLoadImage();
                testDisplayImage();
                break;
            default:
                break;
        }
    }

    public void testLoadImage() {
        DisplayImageOptions displayImageOptions =
                new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnFail(R.mipmap.ic_launcher)
                .build();
//        ImageLoader.getInstance().loadImage(mLoadImageUrl, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                Log.d(TAG, "onLoadingStarted");
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                Log.d(TAG, "onLoadingFailed");
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                Log.d(TAG, "onLoadingComplete");
//                mImageView.setImageBitmap(loadedImage);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                Log.d(TAG, "onLoadingCancelled");
//            }
//        });
        ImageSize imageSize = new ImageSize(500, 500);
        ImageLoader.getInstance().loadImage(mLoadImageUrl, imageSize, displayImageOptions, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgress.setVisibility(View.GONE);
                mImageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgress.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                int percent = current / total * 100;
                Log.d(TAG, "onProgressUpdate current progress:" + current
                        + ", total progress:" + total + ", percent:" + percent);
                mProgress.setProgress(percent);
            }
        });
    }

    public void testDisplayImage() {
        DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .delayBeforeLoading(100)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();
//        ImageLoader.getInstance().displayImage(mLoadImageUrl, mImageView, options);
        //TODO: android 6.0 higher load content provider and sd card image request runtime permission
        ImageLoader.getInstance().displayImage(/*mLoadImageDrawable*//*mLoadImageContentProvider*/
                mLoadImageAssets, mImageView, options
                , new SimpleImageLoadingListener(), new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                Log.d(TAG, "onProgressUpdate current progress:" + current + ", total progress:" + total);
            }
        });
    }
}
