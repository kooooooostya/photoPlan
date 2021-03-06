package com.example.photoplan.ui.location

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoplan.R
import com.example.photoplan.ui.location.adapters.SectionRVAdapter
import com.example.photoplan.ui.location.presentor.LocationPresenter
import kotlinx.android.synthetic.main.fragment_location.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import java.io.IOException
import java.io.InputStream


class LocationFragment : MvpAppCompatFragment(), LocationView {


    @InjectPresenter
    lateinit var presenter: LocationPresenter

    lateinit var sectionRVAdapter: SectionRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_location.setOnClickListener {
            presenter.addFolder()
        }

        location_container.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN && et_location_name.isFocusable) {
                val imm: InputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                et_location_name.isFocusable = false
                presenter.updateNameOfSection(et_location_name.text.toString())
            }
            v.performClick()
        }


        if(presenter.getName().isNotEmpty()) et_location_name.setText(presenter.getName())

        et_location_name.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.isFocusableInTouchMode = true
            }
            v.performClick()
        }

        et_location_name.isFocusable = false
        et_location_name.isFocusableInTouchMode = false

        location_rv.layoutManager = LinearLayoutManager(requireContext())

        sectionRVAdapter = SectionRVAdapter(presenter.getLocations(),
            requireActivity().activityResultRegistry
                .register(
                    SectionRVAdapter.REQUEST_KEY,
                    ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    try {
                        if (uri != null) {
                            val inputStream: InputStream =
                                requireActivity().contentResolver.openInputStream(uri)!!

                            presenter.addImage(
                                sectionRVAdapter.indexToInsertImage,
                                Drawable.createFromStream(
                                    inputStream,
                                    uri.toString()
                                ),
                                uri
                            )
                            sectionRVAdapter.notifyItemChanged(sectionRVAdapter.indexToInsertImage)
                            sectionRVAdapter.indexToInsertImage = -1
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
        )
        location_rv.adapter = sectionRVAdapter
    }

    override fun updateUi() {
        et_location_name.setText(presenter.getName())
        sectionRVAdapter.notifyDataSetChanged()
    }

    override fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun notifyItemInserted() {
        sectionRVAdapter.notifyItemInserted(sectionRVAdapter.itemCount + 1)
    }
}