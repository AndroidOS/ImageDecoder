package com.manuelcarvalho.imagedecoder.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manuelcarvalho.imagedecoder.BuildConfig
import com.manuelcarvalho.imagedecoder.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        checkPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE
        )

        checkPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE
        )


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            createFile()
        }


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
            R.id.action_settings -> true
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

    private fun createFile() {

        //this.getExternalFilesDir()
        val file = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/" + File.separator + "test.txt"
        )
        file.createNewFile()
        val data1 = byteArrayOf(1, 1, 0, 0)

        if (file.exists()) {
            val fo: OutputStream = FileOutputStream(file)
            fo.write(data1)
            fo.close()
            println("file created: $file")
        }


        //file.delete()
        println("file deleted")
    }

    fun getAbsoluteDir(ctx: Context, optionalPath: String?): File? {
        var rootPath: String
        rootPath = if (optionalPath != null && optionalPath != "") {
            ctx.getExternalFilesDir(optionalPath)!!.absolutePath
        } else {
            ctx.getExternalFilesDir(null)!!.absolutePath
        }
        // extraPortion is extra part of file path
        val extraPortion = ("Android/data/" + BuildConfig.APPLICATION_ID
                + File.separator + "files" + File.separator)
        // Remove extraPortion
        rootPath = rootPath.replace(extraPortion, "")
        return File(rootPath)
    }


}