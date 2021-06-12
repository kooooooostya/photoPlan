package com.example.photoplan.ui.location.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.photoplan.Location
import com.example.photoplan.R
import com.example.photoplan.ui.location.SectionRVView
import com.example.photoplan.ui.location.presentor.SectionRVPresenter
import kotlinx.android.synthetic.main.recycler_item.view.*


class SectionRVAdapter(locationList: ArrayList<Location>) :
    RecyclerView.Adapter<SectionRVAdapter.SectionViewHolder>(),
    SectionRVView {

    @InjectPresenter
    lateinit var presenter: SectionRVPresenter

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
                //TODO
            }
            recyclerView.layoutManager = GridLayoutManager(buttonAdd.context, 3)
            recyclerView.adapter = FolderRVAdapter(presenter.getLocation(position))
        }
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val edLocationName: EditText = itemView.ed_location_name
        val buttonAdd: ImageButton = itemView.button_add_image
        val recyclerView: RecyclerView = itemView.rv_images
    }
}