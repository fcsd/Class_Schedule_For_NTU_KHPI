package com.khpi.classschedule.presentation.main.fragments.building.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.khpi.classschedule.R
import com.khpi.classschedule.presentation.base.BaseFragment

class BuildingMapFragment : BaseFragment(), BuildingMapView, OnMapReadyCallback {

    override var TAG = "BuildingMapFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: BuildingMapPresenter
    //@formatter:on


    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var fullName : String

    companion object {
        fun newInstance(latitude: Double, longitude: Double, fullName: String): BuildingMapFragment = BuildingMapFragment().apply {
            this.latitude = latitude
            this.longitude = longitude
            this.fullName = fullName
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_building_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val buildingCoordinates = LatLng(latitude, longitude)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(buildingCoordinates, 16f))
        googleMap.addMarker(MarkerOptions().position(buildingCoordinates).title(fullName))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(buildingCoordinates))
    }

}
