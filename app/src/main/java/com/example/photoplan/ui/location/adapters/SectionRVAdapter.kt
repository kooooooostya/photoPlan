package com.example.photoplan.ui.location.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoplan.R
import com.example.photoplan.ui.data.Location
import com.example.photoplan.ui.location.LocationModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.recycler_item.view.*

class SectionRVAdapter(
    var locationList: ArrayList<Location>,
    private val activityResultLauncher: ActivityResultLauncher<String>
) :
    RecyclerView.Adapter<SectionRVAdapter.SectionViewHolder>() {

    companion object {
        const val REQUEST_KEY = "request_key"
    }

    private var lastLocationList = ArrayList<Location>()
    private var isSelectMode = false

    var indexToInsertImage = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false)
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val location = locationList[position]

        with(holder) {
            holder.location = location

            edLocationName.setText(location.name)

            buttonAdd.setOnClickListener {
                indexToInsertImage = holder.adapterPosition
                activityResultLauncher.launch("image/*")
            }

            edLocationName.isFocusable = false

            edLocationName.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    v.isFocusableInTouchMode = true
                }
                v.performClick()
            }
            edLocationName

            cardView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN && edLocationName.isFocusable) {
                    val imm: InputMethodManager =
                        cardView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    edLocationName.isFocusable = false

                    locationList[holder.adapterPosition].name = edLocationName.text.toString()
                }
                v.performClick()
            }

            recyclerView.layoutManager = GridLayoutManager(buttonAdd.context, 3)
            adapter = FolderRVAdapter(locationList[position], this, isSelectMode)
            recyclerView.adapter = adapter

            removeFab.setOnClickListener {
                locationList.last().removeSelectedImages(adapter.selectedList)

                locationList.addAll(lastLocationList)

                val model = LocationModel.instance
                model.updateDataInDb()

                adapter.selectedList

                isSelectMode = false

                notifyDataSetChanged()

                hideButton()
            }
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        DeleteButtonManager {
        val edLocationName: EditText = itemView.ed_location_name
        val buttonAdd: ImageButton = itemView.button_add_image
        val recyclerView: RecyclerView = itemView.rv_images
        val cardView: CardView = itemView.recycler_item_container
        val removeFab: ExtendedFloatingActionButton = itemView.fab_delete

        lateinit var location: Location

        lateinit var adapter: FolderRVAdapter

        override fun hideButton() {
            removeFab.isVisible = false
        }

        override fun openButton() {
            lastLocationList = locationList.clone() as ArrayList<Location>
            lastLocationList.remove(location)
            locationList.clear()
            locationList.add(location)

            adapter.isSelectMode = true

            isSelectMode = true

            notifyDataSetChanged()

            removeFab.isVisible = true
        }
    }
}