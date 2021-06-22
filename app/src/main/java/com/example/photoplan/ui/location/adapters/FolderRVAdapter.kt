package com.example.photoplan.ui.location.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoplan.R
import com.example.photoplan.ui.data.Image
import com.example.photoplan.ui.data.Location
import com.example.photoplan.ui.imageDialog.ImageExpandDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_image_rv_item.view.*

class FolderRVAdapter(private val location: Location, private val deleteButtonManager: DeleteButtonManager,
                      var isSelectMode: Boolean = false) :
    RecyclerView.Adapter<FolderRVAdapter.FolderViewHolder>() {

    private var imageExpandDialog = ImageExpandDialog()

    val selectedList = ArrayList<Image>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_image_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {

        with(holder){
            if (location.getImage(position).drawable != null){
                imageView.setImageDrawable(location.getImage(position).drawable)
            }else{
                Picasso.get()
                    .load(location.getImage(position).uri)
                    .placeholder(R.mipmap.user_placeholder)
                    .error(R.mipmap.user_placeholder_error)
                    .into(imageView)
            }

            imageView.setOnClickListener {
                imageExpandDialog.showDialog(it.context, holder.imageView.drawable)
            }

            imageView.setOnLongClickListener{
                startDeleteMode()
                return@setOnLongClickListener true
            }

            if (isSelectMode) checkBox.visibility = View.VISIBLE

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    selectedList.add(location.getImage(position))
                }else selectedList.remove(location.getImage(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return location.getImageCount()
    }

    private fun startDeleteMode(){
        deleteButtonManager.openButton()
    }

    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_grid_rv
        val checkBox: CheckBox = itemView.image_checkbox
    }
}