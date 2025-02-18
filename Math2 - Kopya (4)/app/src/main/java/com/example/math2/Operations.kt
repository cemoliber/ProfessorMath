package com.example.math2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.ads.*

class Operations : AppCompatActivity() {

    private companion object{
        private const val TAG = "BANNER_AD_TAG"
    }
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operations)

        var jump2 = MediaPlayer.create(this@Operations, R.raw.jump2)

        val getSpinnerLevel: Spinner = findViewById(R.id.levelSpinner)

        val levels = arrayOf("Seviye 1", "Seviye 2", "Seviye 3")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, levels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        getSpinnerLevel.adapter = adapter

        getSpinnerLevel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

                val getSpinnerData = levels[position]

                val sumImg = findViewById<ImageView>(R.id.addition)

                sumImg.setOnClickListener {
                    jump2.start()
                    val intent = Intent(this@Operations, SumOperation::class.java)
                    intent.putExtra("setSpinnerData", getSpinnerData)
                    startActivity(intent)

                }

                val subImg = findViewById<ImageView>(R.id.subtraction)

                subImg.setOnClickListener{
                    jump2.start()
                    val intent = Intent(this@Operations,SubOperation::class.java)
                    intent.putExtra("setSpinnerData",getSpinnerData)
                    startActivity(intent)
                }

                val multiplyImg = findViewById<ImageView>(R.id.multiply)

                multiplyImg.setOnClickListener{
                    jump2.start()
                    val intent = Intent(this@Operations,MultiOperation::class.java)
                    intent.putExtra("setSpinnerData",getSpinnerData)
                    startActivity(intent)
                }

                val divisionImg = findViewById<ImageView>(R.id.divImg)

                divisionImg.setOnClickListener{
                    jump2.start()
                    val intent = Intent(this@Operations,DivisionOperation::class.java)
                    intent.putExtra("setSpinnerData",getSpinnerData)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //initialize
        MobileAds.initialize(this@Operations) {
            Log.d(TAG, "onInitializationCompleted: ")
        }

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(
                listOf("TEST_DEVICE1", "TEST_DEVICE2")
            ).build()
        )

        //init banner ad
        adView = findViewById(R.id.bannerAd3)
        //Ad Request
        val adRequest = AdRequest.Builder().build()
        //load ad
        adView?.loadAd(adRequest)

        //setup ad callbacklisteners
        adView?.adListener = object : AdListener() {

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d(TAG, "onAdClosed: ")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Log.e(TAG, "onAdFailedToLoad: ${adError.message}")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d(TAG, "onAdOpened: ")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(TAG, "onAdLoaded: ")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(TAG, "onAdClicked: ")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d(TAG, "onAdImpression: ")
            }

        }
    }
}