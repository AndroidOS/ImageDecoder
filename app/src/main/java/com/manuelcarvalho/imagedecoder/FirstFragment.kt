package com.manuelcarvalho.imagedecoder


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


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

    fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int): Bitmap? {
        //Log.d(TAG, "Drawable1 width = ${R.drawable.test}")
        var drawable =
            context?.let { ContextCompat.getDrawable(it, R.drawable.test) }

        val drawable2 =
            context!!.resources.getDrawable(R.drawable.test)

        val bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.test)

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            drawable = DrawableCompat.wrap(drawable!!).mutate()
//        }
        val bitmap = Bitmap.createBitmap(
            drawable?.intrinsicWidth!!,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )

        Log.d(TAG, "bitmap width = ${bitmap.width}")
        Log.d(TAG, "bitmap height = ${bitmap.height}")

        Log.d(TAG, "Drawable1 width = ${drawable2.minimumWidth}")
        //Log.d(TAG, "Drawable1 width = ${R.drawable.test}")
        val canvas = Canvas(bitmap)
        // drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        //drawable.draw(canvas)
        return bitmap
    }

}