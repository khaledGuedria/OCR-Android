package com.kotlin.admin.ocr_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File
import java.net.URI

class MainActivity : AppCompatActivity() {

    //widgets
    private lateinit var importBtn: Button
    private lateinit var imgSelected: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        importBtn =findViewById(R.id.button)
        imgSelected = findViewById(R.id.SelectedImageView)
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
                        val file:File = ImagePicker.getFile(data)!!

                        //You can also get File Path from intent
                        val filePath:String = ImagePicker.getFilePath(data)!!

                        //Sending the picture to the server
                        /*




                        here ..




                         */

                        //Move to loading UI
                        startActivity(Intent(this, LoadingActivity::class.java))


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



}
