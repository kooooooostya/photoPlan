package com.example.photoplan.ui.location

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.photoplan.R
import com.example.photoplan.ui.location.adapters.SectionRVAdapter
import com.example.photoplan.ui.location.presentor.LocationPresenter
import com.example.photoplan.ui.location.presentor.SectionRVPresenter
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : Fragment(), LocationView {

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

        presenter = LocationPresenter(requireContext())

        et_location_name.setText(presenter.getName())

        //TODO test
        et_location_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                presenter.updateNameOfSection(s.toString())
            }

        })
        et_location_name.isFocusable = false

        location_rv.layoutManager = LinearLayoutManager(requireContext())
        sectionRVAdapter = SectionRVAdapter(
            requireActivity(),
            requireActivity().activityResultRegistry,
            presenter.getLocations()
        )
        location_rv.adapter = sectionRVAdapter
    }
}