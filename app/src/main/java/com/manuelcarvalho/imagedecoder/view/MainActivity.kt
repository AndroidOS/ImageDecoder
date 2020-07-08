package com.manuelcarvalho.imagedecoder.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manuelcarvalho.imagedecoder.R
import com.manuelcarvalho.imagedecoder.utils.formatString
import com.manuelcarvalho.imagedecoder.utils.getResizedBitmap
import com.manuelcarvalho.imagedecoder.utils.sendEmail
import com.manuelcarvalho.imagedecoder.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.*


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel

    private val STORAGE_PERMISSION_CODE = 101
    private val CAMERA_PERMISSION_CODE = 105
    private val PHOTO_PERMISSION_CODE = 106

    private val filepath = "MyFileStorage"
    internal var myExternalFile: File? = null

    val fileName = "image.asm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]
        viewModel.seekBarProgress.value = 50


        checkPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE
        )

        checkPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE
        )


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            //readFile()
            createFile()
            createUri()

            sendEmail(this, createUri()!!)
        }


        observeViewModel()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.c64bitmap, R.id.cocobitmap, R.id.vzbitmap -> {
                //item.isChecked = !item.isChecked
                item.isChecked = !item.isChecked
                if (item.itemId == R.id.c64bitmap) {
                    decode64Image()
                }
                if (item.itemId == R.id.vzbitmap) {
                    decodeVZImage()
                }
                if (item.itemId == R.id.cocobitmap) {
                    decodeCoCoImage()
                }
                Toast.makeText(this, "$item ", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_camera -> {
                capturePhoto()
                return true
            }
            R.id.action_image -> {
                captureImage()   //decodeVZImage()

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission)
            == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf(permission),
                requestCode
            )
        } else {
            Toast.makeText(
                this@MainActivity,
                "Permission already granted",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    this,
                    "Storage Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    this,
                    "Storage Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    //https://www.javatpoint.com/kotlin-android-read-and-write-external-storage
    private fun createFile() {
        myExternalFile = File(getExternalFilesDir(filepath), fileName)
        try {
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(formatString.toByteArray())
            fileOutPutStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()
    }


    private fun readFile() {
        //myExternalFile = File(getExternalFilesDir(filepath), fileName)
        //val filename = fileName.text.toString()
        myExternalFile = File(getExternalFilesDir(filepath), fileName)

        if (fileName.toString() != null && fileName.toString().trim() != "") {
            var fileInputStream = FileInputStream(myExternalFile)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            fileInputStream.close()
            //Displaying data on EditText
            Toast.makeText(applicationContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUri(): Uri? {
        val requestFile = File(getExternalFilesDir(filepath), fileName)
        /*
         * Most file-related method calls need to be in
         * try-catch blocks.
         */
        // Use the FileProvider to get a content URI
        val fileUri: Uri? = try {
            FileProvider.getUriForFile(
                this@MainActivity,
                "com.manuelcarvalho.imagedecoder.fileprovider",
                requestFile
            )
        } catch (e: IllegalArgumentException) {
            Log.e(
                "File Selector",
                "The selected file can't be shared: $requestFile"
            )
            null
        }
        //Log.e(TAG,"Uri ${fileUri}")
        return fileUri
    }

    private fun captureImage() {
        //val intent = Intent(Intent.ACTION_GET_CONTENT)
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        )
        //val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        //MediaStore.Images.Media.INTERNAL_CONTENT_URI
        //intent.type = "image/*"
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PHOTO_PERMISSION_CODE)
    }

    private fun capturePhoto() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_PERMISSION_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PERMISSION_CODE && data != null) {
            val newPhoto = (data.extras?.get("data") as Bitmap)
            val newImage = getResizedBitmap(newPhoto, 320, 200)
            //imageView.setImageBitmap(newImage)
            Log.d(TAG, "NewImage   ---  H = ${newImage?.height}  W = ${newImage?.width}")
            if (newImage != null) {
                //decodeBitmap(newImage)
                progressBar.isVisible = true
                viewModel.decodeBitmap(newImage)
            }

        }

        if (resultCode == Activity.RESULT_OK && requestCode == PHOTO_PERMISSION_CODE && data != null) {

            //val newPhoto = (data.extras?.get("dat") as Bitmap?)
            Log.d(TAG, "Result Ok ${data}")


            val imageUri = data.data
            val bitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            imageView.setImageBitmap(bitmap)
            progressBar.isVisible = true
            viewModel.decodeBitmap(bitmap)
        }
    }

    private fun observeViewModel() {
        Log.d(TAG, "ObserveViewModel started")
        viewModel.newImage.observe(this, Observer { image ->
            image?.let {
                imageView.setImageBitmap(image)
                Log.d(TAG, "observeViewModel() fired")
            }
        })

        viewModel.progress.observe(this, Observer { progress ->
            progress?.let {
                progressBar.progress = progress
            }
        })


    }

    private fun decodeVZImage() {

        val icon = BitmapFactory.decodeResource(
            this.resources,
            R.drawable.bart
        )
        progressBar.isVisible = true
        val newImage = getResizedBitmap(icon, 128, 64)
        imageView.setImageBitmap(newImage)
        if (newImage != null) {
            viewModel.decodeBitmapVZ(newImage)
        }
    }

    private fun decode64Image() {

        val icon = BitmapFactory.decodeResource(
            this.resources,
            R.drawable.bart
        )
        progressBar.isVisible = true
        val newImage = getResizedBitmap(icon, 320, 200)
        imageView.setImageBitmap(newImage)
        if (newImage != null) {
            viewModel.decodeBitmapVZ(newImage)
        }
    }

    private fun decodeCoCoImage() {

        val icon = BitmapFactory.decodeResource(
            this.resources,
            R.drawable.bart
        )
        progressBar.isVisible = true
        val newImage = getResizedBitmap(icon, 256, 192)
        imageView.setImageBitmap(newImage)
        if (newImage != null) {
            viewModel.decodeBitmapVZ(newImage)
        }
    }


}