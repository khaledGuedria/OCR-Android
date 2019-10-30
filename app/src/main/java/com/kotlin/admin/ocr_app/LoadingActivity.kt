package com.kotlin.admin.ocr_app

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions



class LoadingActivity : AppCompatActivity() {

    //widgets
    private lateinit var animatedImg: ImageView
    private lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        //init
        animatedImg = findViewById(R.id.loaderImg)
        spinner = findViewById(R.id.progressBar)
        spinner.visibility = View.VISIBLE



        val paintBorder = Paint()
        paintBorder.color = Color.WHITE
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK)
        animatedImg.setLayerType(View.LAYER_TYPE_SOFTWARE, paintBorder)


        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(100))
        Glide.with(this).load(R.drawable.cropped)
            .fitCenter()
            .apply(requestOptions)
            .into(animatedImg)


        //waiting delay : coroutine
        val handler = Handler()
        handler.postDelayed({
            // redirect after 10 seconds
            startActivity(Intent(this, ResultActivity::class.java))
        }, 10000)

    }
}
