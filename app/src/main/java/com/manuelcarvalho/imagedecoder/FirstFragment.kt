package com.manuelcarvalho.imagedecoder



import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.ByteArrayOutputStream


private const val TAG = "FirstFragment"

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBitmapFromVectorDrawable(view.context, 2)

//        val photo =
//            BitmapFactory.decodeResource(resources, R.drawable.test) //this returns null
//
//        if (photo != null)
//        val bytes: ByteArray = getBitmapAsByteArray(photo)

    }

    fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int) {
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

        for (x in 0..127) {
            for (y in 0..175) {
                val pix = bitmap.get(y, x)
                if (pix == -5526613) {       //-1769386 writing, 0 , -5526613, -16777216
                    bmp.set(x, y, Color.RED)
                }
                Log.d(TAG, "Pixel = ${pix}")
            }
        }
        //val pix = bitmap.get(0,0)
        //Log.d(TAG, "Pixel = ${pix}")
        imageView.setImageBitmap(bmp)

    }

}