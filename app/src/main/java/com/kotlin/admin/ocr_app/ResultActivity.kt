package com.kotlin.admin.ocr_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class ResultActivity : AppCompatActivity() {

    //widgets
    private lateinit var animatedImg: ImageView
    private lateinit var doneButton:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //init
        animatedImg = findViewById(R.id.doneImageView)
        doneButton = findViewById(R.id.donebutton)


        //gif loader init
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(100))
        Glide.with(this).load(R.drawable.donedone)
            .fitCenter()
            .apply(requestOptions)
            .into(animatedImg)


        //done Button Event
        doneButton.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))

        }

    }
}
