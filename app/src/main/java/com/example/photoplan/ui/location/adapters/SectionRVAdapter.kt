package com.example.photoplan.ui.location.adapters

import android.app.Activity
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.photoplan.Location
import com.example.photoplan.R
import com.example.photoplan.ui.location.SectionRVView
import com.example.photoplan.ui.location.presentor.SectionRVPresenter
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.io.IOException
import java.io.InputStream


class SectionRVAdapter(private val activity: Activity, registry: ActivityResultRegistry, locationList: ArrayList<Location>) :
    RecyclerView.Adapter<SectionRVAdapter.SectionViewHolder>(),
    SectionRVView {

    @InjectPresenter
    lateinit var presenter: SectionRVPresenter

    private val getContent = registry.register(SectionRVPresenter.REQUEST_KEY, ActivityResultContracts.GetContent()) { uri: Uri? ->
        try {
            val inputStream: InputStream =
                activity.contentResolver.openInputStream(uri!!)!!
            presenter.insertImage(
                indexToInsertImage,
                Drawable.createFromStream(
                    inputStream,
                    uri.toString()
                )
            )
            notifyItemChanged(indexToInsertImage)
            indexToInsertImage = -1
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private var indexToInsertImage = -1

    init {
        presenter = SectionRVPresenter(locationList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val location = presenter.getLocation(position)

        with(holder) {
            edLocationName.setText(location.name)
            buttonAdd.setOnClickListener {
                indexToInsertImage = holder.adapterPosition
                getContent.launch("image/*")
            }
            recyclerView.layoutManager = GridLayoutManager(buttonAdd.context, 3)
            adapter = FolderRVAdapter(presenter.getLocation(position))
            recyclerView.adapter = adapter
        }
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }


    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val edLocationName: EditText = itemView.ed_location_name
        val buttonAdd: ImageButton = itemView.button_add_image
        val recyclerView: RecyclerView = itemView.rv_images

        lateinit var adapter: FolderRVAdapter
    }

}