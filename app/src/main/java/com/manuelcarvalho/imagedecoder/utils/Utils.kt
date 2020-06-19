package com.manuelcarvalho.imagedecoder.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

private const val TAG = "utils"
fun sendEmail(context: Context, uri: Uri) {

    val to = "tom@gmail.com"
    val subject = "Turbo Assembler Image data}."
    //val message = string

    val intent = Intent(Intent.ACTION_SEND)
    val addressees = arrayOf(to)
    intent.putExtra(Intent.EXTRA_EMAIL, addressees)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    //intent.putExtra(Intent.EXTRA_TEXT, message)
    intent.putExtra(Intent.EXTRA_STREAM, uri)

    //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + textFile))

    intent.type = "message/rfc822"
    startActivity(context, Intent.createChooser(intent, "Select Email Sending App :"), null)

}