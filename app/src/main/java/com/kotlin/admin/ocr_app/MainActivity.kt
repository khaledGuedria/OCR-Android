package com.kotlin.admin.ocr_app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File
import com.android.volley.toolbox.*
import com.googlecode.tesseract.android.TessBaseAPI
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import android.content.res.AssetManager
import android.icu.text.CaseMap
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.android.volley.*
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    //var
    val ADD_SERVER = "http://192.168.1.17:3015"

    //widgets
    private lateinit var importBtn: Button
    private lateinit var imgSelected: ImageView
    private lateinit var loadingView: ConstraintLayout
    private lateinit var imageView: ImageView
    private lateinit var file:File
    private lateinit var filePath:String
    private lateinit var result:JSONObject

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        importBtn =findViewById(R.id.button)
        loadingView = findViewById(R.id.loadingView)
        loadingView.isVisible = false
        imgSelected = findViewById(R.id.SelectedImageView)
        imageView = findViewById(R.id.imageView)
        importBtn.setOnClickListener {

            //val intent = Intent()
               // .setType("*/*")
                //.setAction(Intent.ACTION_GET_CONTENT)

           // startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start  { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data
                        imgSelected.setImageURI(fileUri)

                        //You can get File object from intent
                        file = ImagePicker.getFile(data)!!

                        //You can also get File Path from intent
                        filePath = ImagePicker.getFilePath(data)!!

                        //testing ..
                        println("******************************")
                        println(file.absoluteFile)
                        println(file.name)
                        println(file.extension)
                        println(this.encoder(this.filePath))
                        println("******************************")

                        //Edit picture with OpenCV



                        //sendImage to Tesseract API
                        this.sendImage()


                        //Move to loading UI
                        //startActivity(Intent(this, LoadingActivity::class.java))
                        //loadingView.isVisible = true

                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }


        }

    }

    //..

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
        }*/
    }


    //..




    //..

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendImage(){

        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        //url init
        val url = "https://api.ocr.space/parse/image"
        // Post parameters
        // Form fields and values
        val params = HashMap<String,Any>()
        params["apikey"] = "4e4a1ff85588957" //Key provide by OCR.SPACE
        params["language"] = "eng"
        //params["base64image"] = this.encoder(this.filePath)
        params["file"] = this.file.absoluteFile
        params["filetype"] = (this.file.extension).toUpperCase()
        params["isOverlayRequired"] = "false"
        val jsonObject = JSONObject(params as Map<*, *>)

        // Formulate the request and handle the response.
        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener<JSONObject> { response ->
                // Do something with the response
                //this.result = response
                println(response.toString())
            },
            Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
            })


        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the request to the RequestQueue.
        //requestQueue.add(request)

        // Access the RequestQueue through your singleton class.
        VolleyService.getInstance(this).addToRequestQueue(request)



    }

    //..
    @RequiresApi(Build.VERSION_CODES.O)
    fun encoder(filePath: String): String{
        val bytes = File(filePath).readBytes()
        val base64 = Base64.getEncoder().encodeToString(bytes)
        return base64
    }

    }







