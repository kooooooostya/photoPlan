package com.example.photoplan.ui.location

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
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : Fragment(), LocationView {

    @InjectPresenter
    lateinit var presenter: LocationPresenter

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
        et_location_name.addTextChangedListener(object : TextWatcher{
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
        location_rv.adapter = SectionRVAdapter(presenter.getLocations())
    }

}