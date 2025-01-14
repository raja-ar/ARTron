/*
 *    Copyright 2015 Azmeer Raja
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package com.RaceAr;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

public class DevActivity extends AppCompatActivity implements ObservableScrollViewCallbacks, View.OnClickListener {

    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));


        //Set and Define Imageviews id
        ImageView knowme = (ImageView) findViewById(R.id.knowme);
        ImageView bwar = (ImageView) findViewById(R.id.bwar);
        ImageView maari = (ImageView) findViewById(R.id.maari);
        ImageView kingcompass = (ImageView) findViewById(R.id.kingcompass);
        ImageView racear = (ImageView) findViewById(R.id.racear);
        ImageView fotoar =(ImageView) findViewById(R.id.fotoar);



 /*                 Multiple Screen Size Condition             */

        // Small Size

        if ((getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)
        {
            //Toast.makeText(getApplication(), "small", Toast.LENGTH_SHORT).show();

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=5;
            Bitmap bm_knowme=BitmapFactory.decodeResource(getResources(),R.drawable.knowme,options);
            knowme.setImageBitmap(bm_knowme);
            Bitmap bm_bwar = BitmapFactory.decodeResource(getResources(), R.drawable.bwar, options);
            bwar.setImageBitmap(bm_bwar);
            Bitmap bm_maari=BitmapFactory.decodeResource(getResources(),R.drawable.maari,options);
            maari.setImageBitmap(bm_maari);
            Bitmap bm_compass=BitmapFactory.decodeResource(getResources(),R.drawable.kingcompass,options);
            kingcompass.setImageBitmap(bm_compass);
            Bitmap bm_race=BitmapFactory.decodeResource(getResources(),R.drawable.racear,options);
            racear.setImageBitmap(bm_race);
            Bitmap bm_fotoar=BitmapFactory.decodeResource(getResources(),R.drawable.fotoar,options);
            fotoar.setImageBitmap(bm_fotoar);
        }

        //Normal Size

        else if ((getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
            // Toast.makeText(getApplication(),"normal" , Toast.LENGTH_SHORT).show();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=3;
            Bitmap bm_knowme=BitmapFactory.decodeResource(getResources(),R.drawable.knowme,options);
            knowme.setImageBitmap(bm_knowme);
            Bitmap bm_bwar = BitmapFactory.decodeResource(getResources(), R.drawable.bwar, options);
            bwar.setImageBitmap(bm_bwar);
            Bitmap bm_maari=BitmapFactory.decodeResource(getResources(),R.drawable.maari,options);
            maari.setImageBitmap(bm_maari);
            Bitmap bm_compass=BitmapFactory.decodeResource(getResources(),R.drawable.kingcompass,options);
            kingcompass.setImageBitmap(bm_compass);
            Bitmap bm_race=BitmapFactory.decodeResource(getResources(),R.drawable.racear,options);
            racear.setImageBitmap(bm_race);
            Bitmap bm_fotoar=BitmapFactory.decodeResource(getResources(),R.drawable.fotoar,options);
            fotoar.setImageBitmap(bm_fotoar);
        }

        // Large Size

        else if ((getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            // Toast.makeText(getApplication(),"large" , Toast.LENGTH_SHORT).show();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=2;

            Bitmap bm_knowme=BitmapFactory.decodeResource(getResources(),R.drawable.knowme,options);
            knowme.setImageBitmap(bm_knowme);
            Bitmap bm_bwar = BitmapFactory.decodeResource(getResources(), R.drawable.bwar, options);
            bwar.setImageBitmap(bm_bwar);
            Bitmap bm_maari=BitmapFactory.decodeResource(getResources(),R.drawable.maari,options);
            maari.setImageBitmap(bm_maari);
            Bitmap bm_compass=BitmapFactory.decodeResource(getResources(),R.drawable.kingcompass,options);
            kingcompass.setImageBitmap(bm_compass);
            Bitmap bm_race=BitmapFactory.decodeResource(getResources(),R.drawable.racear,options);
            racear.setImageBitmap(bm_race);
            Bitmap bm_fotoar=BitmapFactory.decodeResource(getResources(),R.drawable.fotoar,options);
            fotoar.setImageBitmap(bm_fotoar);

        }

        //X-large Size

        else if ((getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
            // Toast.makeText(getApplication(),"xlarge" , Toast.LENGTH_SHORT).show();

            // Set What To Show in The Imageview
            knowme.setImageResource(R.drawable.knowme);
            bwar.setImageResource(R.drawable.bwar);
            maari.setImageResource(R.drawable.maari);
            kingcompass.setImageResource(R.drawable.kingcompass);
            racear.setImageResource(R.drawable.racear);
            fotoar.setImageResource(R.drawable.fotoar);

        }

        //Undefined Size

        else
        {
            // Toast.makeText(getApplication(), "undefined", Toast.LENGTH_SHORT).show();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=4;

            Bitmap bm_knowme=BitmapFactory.decodeResource(getResources(),R.drawable.knowme,options);
            knowme.setImageBitmap(bm_knowme);
            Bitmap bm_bwar = BitmapFactory.decodeResource(getResources(), R.drawable.bwar, options);
            bwar.setImageBitmap(bm_bwar);
            Bitmap bm_maari=BitmapFactory.decodeResource(getResources(),R.drawable.maari,options);
            maari.setImageBitmap(bm_maari);
            Bitmap bm_compass=BitmapFactory.decodeResource(getResources(),R.drawable.kingcompass,options);
            kingcompass.setImageBitmap(bm_compass);
            Bitmap bm_race=BitmapFactory.decodeResource(getResources(),R.drawable.racear,options);
            racear.setImageBitmap(bm_race);
            Bitmap bm_fotoar=BitmapFactory.decodeResource(getResources(),R.drawable.fotoar,options);
            fotoar.setImageBitmap(bm_fotoar);

        }




        //Set On CLick Listeners
        knowme.setOnClickListener(this);
        bwar.setOnClickListener(this);
        maari.setOnClickListener(this);
        kingcompass.setOnClickListener(this);
        racear.setOnClickListener(this);
        fotoar.setOnClickListener(this);





        mImageView = findViewById(R.id.image);
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);


    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
        ViewHelper.setTranslationY(mImageView, scrollY / 2);

    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.knowme: {


                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.raja.knowme")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.raja.knowme")));
                }
            }
            break;
            case R.id.bwar: {


                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.raja.bwar")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.raja.bwar")));
                }
            }
            break;

            case R.id.maari: {


                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.raja.android.maari")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.raja.android.maari")));
                }
            }
            break;

            case R.id.kingcompass: {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.raja.compass")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.raja.compass")));
                }
            }
            break;
            case R.id.racear:{

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.raja.RaceAr")));
                } catch (android.content.ActivityNotFoundException anfe){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.raja.RaceAr")));
                }

            }
            break;
            case R.id.fotoar:{

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.raja.fotoar")));
                } catch (android.content.ActivityNotFoundException anfe){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.raja.fotoar")));
                }

            }

        }
    }

}