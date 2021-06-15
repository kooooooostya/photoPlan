package com.example.photoplan.ui.location.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoplan.Location
import com.example.photoplan.R
import com.example.photoplan.ui.imageDialog.ImageExpandDialog
import kotlinx.android.synthetic.main.grid_image_rv_item.view.*

class FolderRVAdapter(private val location: Location) :
    RecyclerView.Adapter<FolderRVAdapter.FolderViewHolder>(){

    private var imageExpandDialog = ImageExpandDialog()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_image_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.imageView.setImageDrawable(location.getImage(position))

        holder.imageView.setOnClickListener {
            imageExpandDialog.showDialog(it.context, location.getImage(position))
        }
    }

    override fun getItemCount(): Int {
        return location.getImageCount()
    }


    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_grid_rv
    }

}