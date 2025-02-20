package com.example.math2

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import com.google.android.gms.ads.*

class MainActivity : AppCompatActivity() {
    private companion object{
        private const val TAG = "BANNER_AD_TAG"
    }
    private var adView:AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize
        MobileAds.initialize(this@MainActivity) {
            Log.d(TAG,"onInitializationCompleted: ")
        }


        MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(
            listOf("TEST_DEVICE1","TEST_DEVICE2")
        ).build())

        //init banner ad
        adView = findViewById(R.id.bannerAd)
        //Ad Request
        val adRequest = AdRequest.Builder().build()
        //load ad
        adView?.loadAd(adRequest)

        //setup ad callbacklisteners
        adView?.adListener = object : AdListener(){

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d(TAG,"onAdClosed: ")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Log.e(TAG,"onAdFailedToLoad: ${adError.message}")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d(TAG,"onAdOpened: ")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(TAG,"onAdLoaded: ")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(TAG,"onAdClicked: ")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d(TAG,"onAdImpression: ")
            }

        }

        selectLevel()

    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
        Log.d(TAG,"onPause: ")
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
        Log.d(TAG,"onResume: ")
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
        Log.d(TAG,"onDestroy: ")
    }

    private fun selectLevel() {
        val startButton = findViewById<Button>(R.id.start)

        startButton.setOnClickListener {
            var jump2 = MediaPlayer.create(this@MainActivity, R.raw.jump2)
            jump2.start()
            // Hedef aktiviteyi başlat
            val intent = Intent(this, Operations::class.java)
            startActivity(intent)
        }
    }
}