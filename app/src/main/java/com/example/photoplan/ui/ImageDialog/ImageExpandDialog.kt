package com.example.photoplan.ui.ImageDialog


import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.Window
import com.example.photoplan.R
import kotlinx.android.synthetic.main.image_dialog.view.*


class ImageExpandDialog {

    private lateinit var dialog: AlertDialog

    fun showDialog(context: Context, drawable: Drawable) {
        val view = LayoutInflater.from(context).inflate(R.layout.image_dialog, null)

        view.imageView.setImageDrawable(drawable)

        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setCancelable(true)

        dialog = builder.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.show()
    }

    private fun hideDialog() {
        dialog.dismiss()
    }
}