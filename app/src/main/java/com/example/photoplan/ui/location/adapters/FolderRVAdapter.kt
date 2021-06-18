package com.example.photoplan.ui.location.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoplan.ui.data.Location
import com.example.photoplan.R
import com.example.photoplan.ui.imageDialog.ImageExpandDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_image_rv_item.view.*

class FolderRVAdapter(private val location: Location) :
    RecyclerView.Adapter<FolderRVAdapter.FolderViewHolder>() {

    private var imageExpandDialog = ImageExpandDialog()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_image_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {

        if (location.getImage(position).drawable != null){
            holder.imageView.setImageDrawable(location.getImage(position).drawable)
        }else{
            Picasso.get()
                .load(location.getImage(position).uri)
                .placeholder(R.mipmap.user_placeholder)
                .error(R.mipmap.user_placeholder_error)
                .into(holder.imageView)
        }

        holder.imageView.setOnClickListener {
            imageExpandDialog.showDialog(it.context, holder.imageView.drawable)
        }
    }

    override fun getItemCount(): Int {
        return location.getImageCount()
    }


    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_grid_rv
    }

}