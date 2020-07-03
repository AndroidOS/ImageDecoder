package com.manuelcarvalho.imagedecoder.view



import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manuelcarvalho.imagedecoder.R
import com.manuelcarvalho.imagedecoder.utils.formatString
import com.manuelcarvalho.imagedecoder.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.ByteArrayOutputStream


private const val TAG = "FirstFragment"

class FirstFragment : Fragment() {

    var emailString = "picture DB "
    private lateinit var viewModel: AppViewModel

    //private var screenWidth = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false)
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var screenWidth = imageView.measuredWidth
        //getBitmapFromVectorDrawable(view.context, 2)
        progressBar.isVisible = false

        progressBar.max = 200
        //progressBar.min = 0
        //progressBar.progress = 40

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        seekBar.progress = 50

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textview_first.text = "Progress : $progress"
                viewModel.seekBarProgress.value = progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })



        observeViewModel()

        view.setOnTouchListener { v, event ->
            val x = event.x
            val y = event.y
            Log.d(TAG, "Touched $x $screenWidth")
            true
        }


    }

    fun getBitmapFromVectorDrawable1(context: Context?, drawableId: Int) {
        //val d: Drawable = res.getDrawable(R.drawable.test)
        val d = context?.getDrawable(R.drawable.test)
        val bitmap = (d as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapdata: ByteArray = stream.toByteArray()

        //imageView.setImageBitmap(bitmap)

        val conf = Bitmap.Config.ARGB_8888 // see other conf types

        val bmp =
            Bitmap.createBitmap(bitmap.height, bitmap.width, conf) // this creates a MUTABLE bitmap

        //val canvas = Canvas(bmp)

        Log.d(TAG, "Drawable1 height = ${bitmap.height}")
        Log.d(TAG, "Drawable1 width = ${bitmap.width}")


        var hexNum = ""
        var lineNum = 0
        var pixelCount = 0
        for (x in 0..127) {
            for (y in 0..175) {
                val pix = bitmap.get(y, x)
                lineNum += 1
                pixelCount += 1

                if (pix == -5526613) {       //-1769386 writing, 0 , -5526613, -16777216
                    bmp.set(x, y, Color.RED)
                    hexNum = "4"
                }
                if (pix == -1769386) {       //-1769386 writing, 0 , -5526613, -16777216
                    bmp.set(x, y, Color.GREEN)
                    hexNum = "2"
                }
                if (pix == -16777216) {       //-1769386 writing, 0 , -5526613, -16777216
                    bmp.set(x, y, Color.BLUE)
                    hexNum = "1"
                }
                if (pix == 0) {       //-1769386 writing, 0 , -5526613, -16777216
                    bmp.set(x, y, Color.MAGENTA)
                    hexNum = "5"
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

        formatString = emailString
        //val pix = bitmap.get(0,0)
        Log.d(TAG, "${emailString}")
        Log.d(TAG, "${pixelCount}")
        imageView.setImageBitmap(bmp)

    }

    fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int) {
        //val d: Drawable = res.getDrawable(R.drawable.test)
        val d = context?.getDrawable(R.drawable.test2)
        val bitmap = (d as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapdata: ByteArray = stream.toByteArray()

        //imageView.setImageBitmap(bitmap)

        val conf = Bitmap.Config.ARGB_8888 // see other conf types

        val bmp =
            Bitmap.createBitmap(bitmap.height, bitmap.width, conf) // this creates a MUTABLE bitmap

        //val canvas = Canvas(bmp)

        Log.d(TAG, "Drawable1 height = ${bitmap.height}")
        Log.d(TAG, "Drawable1 width = ${bitmap.width}")


        var hexNum = ""
        var lineNum = 0
        var pixelCount = 0
        for (x in 0..146) {
            for (y in 0..141) {
                val pix = bitmap.get(y, x)
                lineNum += 1
                pixelCount += 1

                Log.d(TAG, "${pix}")

                if (pix == -16777216) {       //-1769386 writing, 0 , -5526613, -16777216
                    bmp.set(x, y, Color.BLACK)
                    hexNum = "0"
                } else {
                    bmp.set(x, y, Color.WHITE)
                    hexNum = "15"
                }
//                if (pix < -2040351 && pix > -12897226) {       //-1769386 writing, 0 , -5526613, -16777216
//                    bmp.set(x, y, Color.GRAY)
//                    hexNum = "21"
//                }
//                if (pix < -12897226 && pix > -16579837) {       //-1769386 writing, 0 , -5526613, -16777216
//                    bmp.set(x, y, Color.LTGRAY)
//                    hexNum = "23"
//                }
//                if (pix < -16777216) {       //-1769386 writing, 0 , -5526613, -16777216
//                    bmp.set(x, y, Color.WHITE)
//                    hexNum = "25"
//                }


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

        formatString = emailString
        //val pix = bitmap.get(0,0)
        Log.d(TAG, "${emailString}")
        Log.d(TAG, "${pixelCount}")
        imageView.setImageBitmap(bmp)

    }

    fun observeViewModel() {

        viewModel.displayProgress.observe(viewLifecycleOwner, Observer { display ->
            display?.let {
                progressBar.isVisible = display

            }
        })
    }


}