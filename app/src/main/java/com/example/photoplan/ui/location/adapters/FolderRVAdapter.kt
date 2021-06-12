package com.example.photoplan.ui.location.adapters

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.photoplan.Location
import com.example.photoplan.R
import com.example.photoplan.ui.location.presentor.FolderPresenter
import com.example.photoplan.ui.location.FolderView
import kotlinx.android.synthetic.main.grid_image_rv_item.view.*

class FolderRVAdapter(location: Location): RecyclerView.Adapter<FolderRVAdapter.FolderViewHolder>(),
    FolderView {

    @InjectPresenter
    lateinit var presenter: FolderPresenter

    init {
        presenter = FolderPresenter(location)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_image_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.imageView.setImageDrawable(presenter.getImage(position))

        holder.imageView.setOnClickListener{
            //TODO expand image
        }
    }

    override fun getItemCount(): Int {
        return presenter.getImagesCount()
    }


    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_grid_rv
    }

}