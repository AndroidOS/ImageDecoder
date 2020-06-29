package com.manuelcarvalho.imagedecoder.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    val newImage = MutableLiveData<Bitmap>()
    val progress = MutableLiveData<Int>()
    val displayProgress = MutableLiveData<Boolean>()

    fun decodeBitmap(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {

            val conf = Bitmap.Config.ARGB_8888
            val bmp =
                Bitmap.createBitmap(bitmap.width, bitmap.height, conf)
            var minimumVal = 0      //      -15768818
            var maximumVal = -15768818  //  -1382691
            Log.d(TAG, "NewImage   ---  H = ${bitmap.height}  W = ${bitmap.width}")
            var emailString = "picture DB "
            var hexNum = ""
            var lineNum = 0
            var pixelCount = 0

            for (y in 0..bitmap.height - 1) {
                //progress.value = y
                viewModelScope.launch(Dispatchers.Main) { progress.value = y }
                Log.d(TAG, "${y}")
                for (x in 0..bitmap.width - 1) {
                    val pix = bitmap.get(x, y)
                    lineNum += 1
                    pixelCount += 1
                    if (minimumVal > pix) {
                        minimumVal = pix
                    }
                    if (maximumVal < pix) {
                        maximumVal = pix
                    }
                    // Log.d(TAG, "${pix}")

                    if (pix < -7193063) {       //-1769386 writing, 0 , -5526613, -16777216
                        bmp.set(x, y, Color.BLACK)
                        hexNum = "0"
                    } else {
                        bmp.set(x, y, Color.WHITE)
                        hexNum = "15"
                    }

                    //Log.d(TAG, "Pixel = ${pix}")
                    if (lineNum > 20) {
                        lineNum = 0
                        emailString += "\n    DB " + hexNum + ","
                    } else if (lineNum > 19) {
                        emailString += hexNum
                        //lineNum = 0
                    } else {
                        emailString += hexNum + ","
                    }
                }
            }
            viewModelScope.launch(Dispatchers.Main) {
                newImage.value = bmp
                displayProgress.value = false
            }
        }


    }

    fun decodeBitmapVZ(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {

            val conf = Bitmap.Config.ARGB_8888
            val bmp =
                Bitmap.createBitmap(bitmap.width, bitmap.height, conf)
            var minimumVal = -15768818      //      -15768818
            var maximumVal = -15768818  //  -1382691
            Log.d(TAG, "NewImage   ---  H = ${bitmap.height}  W = ${bitmap.width}")
            var emailString = "picture DB "
            var hexNum = ""
            var lineNum = 0
            var pixelCount = 0

            for (y in 0..bitmap.height - 1) {
                //progress.value = y
                viewModelScope.launch(Dispatchers.Main) { progress.value = y }
                Log.d(TAG, "${y}")
                for (x in 0..bitmap.width - 1) {
                    val pix = bitmap.get(x, y)
                    if (minimumVal > pix) {
                        maximumVal = pix
                    }
                    if (maximumVal < pix) {
                        maximumVal = pix
                    }
                }
            }

            Log.d(TAG, "${minimumVal}  ${maximumVal}")
            for (y in 0..bitmap.height - 1) {
                //progress.value = y
                viewModelScope.launch(Dispatchers.Main) { progress.value = y }
                Log.d(TAG, "${y}")
                for (x in 0..bitmap.width - 1) {
                    val pix = bitmap.get(x, y)
                    lineNum += 1
                    pixelCount += 1
                    if (minimumVal > pix) {
                        minimumVal = pix
                    }
                    if (maximumVal < pix) {
                        maximumVal = pix
                    }
                    // Log.d(TAG, "${pix}")

                    if (pix < -9768818) {       //-1769386 writing, 0 , -5526613, -16777216
                        bmp.set(x, y, Color.BLACK)
                        hexNum = "0"
                    } else {
                        bmp.set(x, y, Color.WHITE)
                        hexNum = "15"
                    }

                    //Log.d(TAG, "Pixel = ${pix}")
                    if (lineNum > 20) {
                        lineNum = 0
                        emailString += "\n    DB " + hexNum + ","
                    } else if (lineNum > 19) {
                        emailString += hexNum
                        //lineNum = 0
                    } else {
                        emailString += hexNum + ","
                    }
                }
            }
            viewModelScope.launch(Dispatchers.Main) {
                newImage.value = bmp
                displayProgress.value = false
            }
        }


    }


}